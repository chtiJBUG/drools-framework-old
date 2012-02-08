/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chtijbug.drools.runtime.impl;

import org.chtijbug.drools.entity.DroolsFactObject;
import org.chtijbug.drools.entity.DroolsRuleObject;
import org.chtijbug.drools.entity.history.rule.RuleFiredHistoryEvent;
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

    private final RuleBaseStatefullSession ruleBaseSession;
    static final Logger LOGGER = LoggerFactory.getLogger(RuleHandlerListener.class);
    private int nbRuleFired = 0;
    private int maxNumberRuleToExecute;

    public RuleHandlerListener(RuleBaseStatefullSession ruleBaseSession) {
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
    public void beforeActivationFired(BeforeActivationFiredEvent bafe) {
    }

    @Override
    public void afterActivationFired(AfterActivationFiredEvent event) {
        Activation activation = event.getActivation();
        List<? extends FactHandle> listFact = activation.getFactHandles();

        DroolsRuleObject droolsRuleObject = ruleBaseSession.getDroolsRuleObject(activation.getRule());

        RuleFiredHistoryEvent newRuleEvent = new RuleFiredHistoryEvent(droolsRuleObject);

        for (FactHandle h : listFact) {
            DroolsFactObject sourceFactObject = ruleBaseSession.getLastFactObjectVersionFromFactHandle(h);
            newRuleEvent.getWhenObjects().add(sourceFactObject);
        }

        LOGGER.debug("AfterActivationFiredEvent. Rule name: {} ", droolsRuleObject.getRuleName());
        ruleBaseSession.getHistoryContainer().addHistoryElement(newRuleEvent);

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
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void afterRuleFlowGroupActivated(RuleFlowGroupActivatedEvent ruleFlowGroupActivatedEvent) {

        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void beforeRuleFlowGroupDeactivated(RuleFlowGroupDeactivatedEvent ruleFlowGroupDeactivatedEvent) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void afterRuleFlowGroupDeactivated(RuleFlowGroupDeactivatedEvent ruleFlowGroupDeactivatedEvent) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public int getNbRuleFired() {
        return nbRuleFired;
    }

}
