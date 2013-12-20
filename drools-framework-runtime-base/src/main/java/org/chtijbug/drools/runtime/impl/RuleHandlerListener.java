/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chtijbug.drools.runtime.impl;

import org.chtijbug.drools.entity.DroolsFactObject;
import org.chtijbug.drools.entity.DroolsRuleFlowGroupObject;
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
public class RuleHandlerListener extends DefaultAgendaEventListener {
    /**
     * Class logger
     */
    private static Logger logger = LoggerFactory.getLogger(RuleHandlerListener.class);
    /**
     * The Knowledge sessions sending events
     */
    private final RuleBaseStatefulSession ruleBaseSession;
    /**
     * The rule fired count
     */
    private int nbRuleFired = 0;
    /**
     * the RuleFLowGroup count
     */
    private int nbRuleFlowGroupUsed = 0;
    /**
     * The rule fire limit
     */
    private int maxNumberRuleToExecute;

    /**
     * IfMaxNumberRulewasReached
     */
    private boolean maxNumerExecutedRulesReached = false;

    public boolean isMaxNumerExecutedRulesReached() {
        return maxNumerExecutedRulesReached;
    }


    public RuleHandlerListener(RuleBaseStatefulSession ruleBaseSession) {
        this.ruleBaseSession = ruleBaseSession;
        this.maxNumberRuleToExecute = ruleBaseSession.getMaxNumberRuleToExecute();
    }

    @Override
    public void beforeActivationFired(BeforeActivationFiredEvent event) {
        logger.debug(">>beforeActivationFired", event);
        try {
            Activation activation = event.getActivation();
            List<? extends FactHandle> listFact = activation.getFactHandles();
            //____ Getting the Rule object summary from the session
            DroolsRuleObject droolsRuleObject = ruleBaseSession.getDroolsRuleObject(activation.getRule());
            //____ Creating the specific History event for history managment
            BeforeRuleFiredHistoryEvent newBeforeRuleEvent = new BeforeRuleFiredHistoryEvent(this.ruleBaseSession.getNextEventCounter(), this.nbRuleFired + 1, droolsRuleObject);
            //____ Adding all objects info contained in the Activation object into the history Events
            for (FactHandle h : listFact) {
                DroolsFactObject sourceFactObject = ruleBaseSession.getLastFactObjectVersionFromFactHandle(h);
                newBeforeRuleEvent.getWhenObjects().add(sourceFactObject);
            }
            //_____ Add Event into the History Container
            ruleBaseSession.addHistoryElement(newBeforeRuleEvent);
        } finally {
            logger.debug("<<beforeActivationFired");
        }
    }

    @Override
    public void afterActivationFired(AfterActivationFiredEvent event) {
        logger.debug(">>afterActivationFired", event);
        try {
            //____ Increment the global rule fired count
            nbRuleFired++;
            Activation activation = event.getActivation();
            //____ Getting the Rule Object Summary from the session
            DroolsRuleObject droolsRuleObject = ruleBaseSession.getDroolsRuleObject(activation.getRule());
            //____ Creating the specific "After Rule Fired" History Event
            AfterRuleFiredHistoryEvent newAfterRuleEvent = new AfterRuleFiredHistoryEvent(this.ruleBaseSession.getNextEventCounter(), this.nbRuleFired, droolsRuleObject);
            ruleBaseSession.addHistoryElement(newAfterRuleEvent);
            //____ If the max number rule able to be executed threshold is raised, stop the session execution
            if (nbRuleFired >= maxNumberRuleToExecute) {
                logger.warn(String.format("%d rules have been fired. This is the limit.", maxNumberRuleToExecute));
                logger.warn("The session execution will be stop");
                KnowledgeRuntime runTime = event.getKnowledgeRuntime();
                this.maxNumerExecutedRulesReached = true;
                runTime.halt();
            }
            logger.debug("nbre RDG Fired ==> ", nbRuleFired);
        } finally {
            logger.debug("<<afterActivationFired");
        }
    }

    @Override
    public void afterRuleFlowGroupActivated(RuleFlowGroupActivatedEvent ruleFlowGroupActivatedEvent) {
        logger.debug(">>afterRuleFlowGroupActivated", ruleFlowGroupActivatedEvent);
        try {
            String ruleFlowGroupName = null;
            //____ Filling the event with the rule flow group name activated
            if (ruleFlowGroupActivatedEvent.getRuleFlowGroup() != null && ruleFlowGroupActivatedEvent.getRuleFlowGroup().getName() != null) {
                ruleFlowGroupName = ruleFlowGroupActivatedEvent.getRuleFlowGroup().getName();
            }
            DroolsRuleFlowGroupObject droolsRuleFlowGroupObject = new DroolsRuleFlowGroupObject(this.nbRuleFlowGroupUsed + 1, ruleFlowGroupName);
            //____ Creating history event
            AfterRuleFlowActivatedHistoryEvent afterRuleFlowActivatedHistoryEvent = new AfterRuleFlowActivatedHistoryEvent(this.ruleBaseSession.getNextEventCounter(), droolsRuleFlowGroupObject);
            //____ Adding into the History container
            ruleBaseSession.addHistoryElement(afterRuleFlowActivatedHistoryEvent);
        } finally {
            logger.debug("<<afterRuleFlowGroupActivated");
        }
    }

    @Override
    public void afterRuleFlowGroupDeactivated(RuleFlowGroupDeactivatedEvent ruleFlowGroupDeactivatedEvent) {
        logger.debug(">>afterRuleFlowGroupDeactivated", ruleFlowGroupDeactivatedEvent);
        try {
            String ruleFlowGroupName = null;
            if (ruleFlowGroupDeactivatedEvent.getRuleFlowGroup() != null && ruleFlowGroupDeactivatedEvent.getRuleFlowGroup().getName() != null) {
                ruleFlowGroupName = ruleFlowGroupDeactivatedEvent.getRuleFlowGroup().getName();
            }
            this.nbRuleFlowGroupUsed++;
            DroolsRuleFlowGroupObject droolsRuleFlowGroupObject = new DroolsRuleFlowGroupObject(this.nbRuleFlowGroupUsed, ruleFlowGroupName);
            //____ Creating history event
            AfterRuleFlowDeactivatedHistoryEvent afterRuleFlowGroupDeactivated = new AfterRuleFlowDeactivatedHistoryEvent(this.ruleBaseSession.getNextEventCounter(), droolsRuleFlowGroupObject);
            //_____ Adding the event to the History container
            ruleBaseSession.addHistoryElement(afterRuleFlowGroupDeactivated);
        } finally {
            logger.debug("<<afterRuleFlowGroupDeactivated");
        }
    }

    public int getNbRuleFired() {
        return nbRuleFired;
    }

}
