/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chtijbug.drools.runtime.impl;

import org.chtijbug.drools.entity.DroolsFactObject;
import org.chtijbug.drools.entity.DroolsRuleObject;
import org.chtijbug.drools.entity.history.rule.AfterRuleFiredHistoryEvent;
import org.chtijbug.drools.entity.history.rule.AfterRuleFlowActivatedHistoryEvent;
import org.chtijbug.drools.entity.history.rule.AfterRuleFlowDeactivatedHistoryEvent;
import org.chtijbug.drools.entity.history.rule.BeforeRuleFiredHistoryEvent;
import org.drools.event.rule.*;
import org.drools.runtime.KnowledgeRuntime;
import org.drools.runtime.rule.Activation;
import org.drools.runtime.rule.FactHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author nheron
 */
public class RuleHandlerListener implements AgendaEventListener {

    private final RuleBaseStatefulSession ruleBaseSession;
    static final Logger LOGGER = LoggerFactory.getLogger(RuleHandlerListener.class);
    private int nbRuleFired = 0;
    private int maxNumberRuleToExecute;

    public RuleHandlerListener(RuleBaseStatefulSession ruleBaseSession) {
        this.ruleBaseSession = ruleBaseSession;
        this.maxNumberRuleToExecute = ruleBaseSession.getMaxNumberRuleToExecute();
    }

    @Override
    public void activationCreated(ActivationCreatedEvent ace) {
    }

    @Override
    public void activationCancelled(ActivationCancelledEvent ace) {
    }

    @Override
    public void beforeActivationFired(BeforeActivationFiredEvent event) {
        Activation activation = event.getActivation();
        List<? extends FactHandle> listFact = activation.getFactHandles();

        DroolsRuleObject droolsRuleObject = ruleBaseSession.getDroolsRuleObject(activation.getRule());

        BeforeRuleFiredHistoryEvent newBeforeRuleEvent = new BeforeRuleFiredHistoryEvent(droolsRuleObject);

        for (FactHandle h : listFact) {
            DroolsFactObject sourceFactObject = ruleBaseSession.getLastFactObjectVersionFromFactHandle(h);
            newBeforeRuleEvent.getWhenObjects().add(sourceFactObject);
        }

        LOGGER.debug("BeforeActivationFiredEvent. Rule name: {} ", droolsRuleObject.getRuleName());
        ruleBaseSession.getHistoryContainer().addHistoryElement(newBeforeRuleEvent);
    }

    @Override
    public void afterActivationFired(AfterActivationFiredEvent event) {
        Activation activation = event.getActivation();
        List<? extends FactHandle> listFact = activation.getFactHandles();

        DroolsRuleObject droolsRuleObject = ruleBaseSession.getDroolsRuleObject(activation.getRule());

        AfterRuleFiredHistoryEvent newAfterRuleEvent = new AfterRuleFiredHistoryEvent(droolsRuleObject);

        LOGGER.debug("AfterActivationFiredEvent. Rule name: {} ", droolsRuleObject.getRuleName());
        ruleBaseSession.getHistoryContainer().addHistoryElement(newAfterRuleEvent);

        nbRuleFired++;

        if (nbRuleFired > maxNumberRuleToExecute) {
            KnowledgeRuntime runTime = event.getKnowledgeRuntime();
            runTime.halt();
        }
        LOGGER.debug("nbre RDG Fired ", nbRuleFired);

    }

    @Override
    public void agendaGroupPopped(AgendaGroupPoppedEvent agpe) {
    }

    @Override
    public void agendaGroupPushed(AgendaGroupPushedEvent agpe) {
    }

    @Override
    public void beforeRuleFlowGroupActivated(RuleFlowGroupActivatedEvent ruleFlowGroupActivatedEvent) {
    }

    @Override
    public void afterRuleFlowGroupActivated(RuleFlowGroupActivatedEvent ruleFlowGroupActivatedEvent) {

        LOGGER.debug("afterRuleFlowGroupActivated. Rule name: {} ", "");
        AfterRuleFlowActivatedHistoryEvent afterRuleFlowActivatedHistoryEvent = new AfterRuleFlowActivatedHistoryEvent();
        if (ruleFlowGroupActivatedEvent.getRuleFlowGroup() != null && ruleFlowGroupActivatedEvent.getRuleFlowGroup().getName() != null) {
            afterRuleFlowActivatedHistoryEvent.setRuleFlowGroupName(ruleFlowGroupActivatedEvent.getRuleFlowGroup().getName());
        }

        ruleBaseSession.getHistoryContainer().addHistoryElement(afterRuleFlowActivatedHistoryEvent);
    }

    @Override
    public void beforeRuleFlowGroupDeactivated(RuleFlowGroupDeactivatedEvent ruleFlowGroupDeactivatedEvent) {

    }

    @Override
    public void afterRuleFlowGroupDeactivated(RuleFlowGroupDeactivatedEvent ruleFlowGroupDeactivatedEvent) {
        LOGGER.debug("afterRuleFlowGroupDeactivated. Rule name: {} ", "");
        AfterRuleFlowDeactivatedHistoryEvent afterRuleFlowGroupDeactivated = new AfterRuleFlowDeactivatedHistoryEvent();
        if (ruleFlowGroupDeactivatedEvent.getRuleFlowGroup() != null && ruleFlowGroupDeactivatedEvent.getRuleFlowGroup().getName() != null) {
            afterRuleFlowGroupDeactivated.setRuleFlowGroupName(ruleFlowGroupDeactivatedEvent.getRuleFlowGroup().getName());
        }
        ruleBaseSession.getHistoryContainer().addHistoryElement(afterRuleFlowGroupDeactivated);
    }

    public int getNbRuleFired() {
        return nbRuleFired;
    }

}
