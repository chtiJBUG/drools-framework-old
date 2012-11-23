/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chtijbug.drools.runtime.impl;

import org.chtijbug.drools.common.log.Logger;
import org.chtijbug.drools.common.log.LoggerFactory;
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

import java.util.List;

/**
 * @author nheron
 */
public class RuleHandlerListener extends DefaultAgendaEventListener {
    /** Class logger */
    private static Logger logger = LoggerFactory.getLogger(RuleHandlerListener.class);
    /** The Knowledge sessions sending events */
    private final RuleBaseStatefulSession ruleBaseSession;
    /** The rule fired count */
    private int nbRuleFired = 0;
    /** The rule fire limit */
    private int maxNumberRuleToExecute;

   /** IfMaxNumberRulewasReached */
   private boolean maxNumerExecutedRulesReached=false;

    public boolean isMaxNumerExecutedRulesReached() {
        return maxNumerExecutedRulesReached;
    }


    public RuleHandlerListener(RuleBaseStatefulSession ruleBaseSession) {
        this.ruleBaseSession = ruleBaseSession;
        this.maxNumberRuleToExecute = ruleBaseSession.getMaxNumberRuleToExecute();
    }

    @Override
    public void beforeActivationFired(BeforeActivationFiredEvent event) {
        logger.entry("beforeActivationFired", event);
        try {
            Activation activation = event.getActivation();
            List<? extends FactHandle> listFact = activation.getFactHandles();
            //____ Getting the Rule object summary from the session
            DroolsRuleObject droolsRuleObject = ruleBaseSession.getDroolsRuleObject(activation.getRule());
            //____ Creating the specific History event for history managment
            BeforeRuleFiredHistoryEvent newBeforeRuleEvent = new BeforeRuleFiredHistoryEvent(this.ruleBaseSession.getNextEventCounter(),droolsRuleObject);
            //____ Adding all objects info contained in the Activation object into the history Events
            for (FactHandle h : listFact) {
                DroolsFactObject sourceFactObject = ruleBaseSession.getLastFactObjectVersionFromFactHandle(h);
                newBeforeRuleEvent.getWhenObjects().add(sourceFactObject);
            }
            //_____ Add Event into the History Container
            ruleBaseSession.getHistoryContainer().addHistoryElement(newBeforeRuleEvent);
        } finally {
            logger.exit("beforeActivationFired");
        }
    }

    @Override
    public void afterActivationFired(AfterActivationFiredEvent event) {
        logger.entry("afterActivationFired", event);
        try {
            Activation activation = event.getActivation();
            //____ Getting the Rule Object Summary from the session
            DroolsRuleObject droolsRuleObject = ruleBaseSession.getDroolsRuleObject(activation.getRule());
            //____ Creating the specific "After Rule Fired" History Event
            AfterRuleFiredHistoryEvent newAfterRuleEvent = new AfterRuleFiredHistoryEvent(this.ruleBaseSession.getNextEventCounter(),droolsRuleObject);
            ruleBaseSession.getHistoryContainer().addHistoryElement(newAfterRuleEvent);
            //____ Increment the global rule fired count
            nbRuleFired++;
            //____ If the max number rule able to be executed threshold is raised, stop the session execution
            if (nbRuleFired > maxNumberRuleToExecute) {
                logger.warn(String.format("%d rules have been fired. This is the limit.", maxNumberRuleToExecute));
                logger.warn("The session execution will be stop");
                KnowledgeRuntime runTime = event.getKnowledgeRuntime();
                this.maxNumerExecutedRulesReached=true;
                runTime.halt();
            }
            logger.debug("nbre RDG Fired ==> ", nbRuleFired);
        } finally {
            logger.exit("afterActivationFired");
        }
    }

    @Override
    public void afterRuleFlowGroupActivated(RuleFlowGroupActivatedEvent ruleFlowGroupActivatedEvent) {
        logger.entry("afterRuleFlowGroupActivated", ruleFlowGroupActivatedEvent);
        try {
            //____ Creating history event
            AfterRuleFlowActivatedHistoryEvent afterRuleFlowActivatedHistoryEvent = new AfterRuleFlowActivatedHistoryEvent();
            //____ Filling the event with the rule flow group name activated
            if (ruleFlowGroupActivatedEvent.getRuleFlowGroup() != null && ruleFlowGroupActivatedEvent.getRuleFlowGroup().getName() != null) {
                afterRuleFlowActivatedHistoryEvent.setRuleFlowGroupName(ruleFlowGroupActivatedEvent.getRuleFlowGroup().getName());
            }
            //____ Adding into the History container
            ruleBaseSession.getHistoryContainer().addHistoryElement(afterRuleFlowActivatedHistoryEvent);
        } finally {
            logger.exit("afterRuleFlowGroupActivated");
        }
    }

    @Override
    public void afterRuleFlowGroupDeactivated(RuleFlowGroupDeactivatedEvent ruleFlowGroupDeactivatedEvent) {
        logger.entry("afterRuleFlowGroupDeactivated", ruleFlowGroupDeactivatedEvent);
        try {
            //____ Creating history event
            AfterRuleFlowDeactivatedHistoryEvent afterRuleFlowGroupDeactivated = new AfterRuleFlowDeactivatedHistoryEvent();
            //____ Filling with the deactivated rule flow group name
            if (ruleFlowGroupDeactivatedEvent.getRuleFlowGroup() != null && ruleFlowGroupDeactivatedEvent.getRuleFlowGroup().getName() != null) {
                afterRuleFlowGroupDeactivated.setRuleFlowGroupName(ruleFlowGroupDeactivatedEvent.getRuleFlowGroup().getName());
            }
            //_____ Adding the event to the History container
            ruleBaseSession.getHistoryContainer().addHistoryElement(afterRuleFlowGroupDeactivated);
        } finally {
            logger.exit("afterRuleFlowGroupDeactivated");
        }
    }

    public int getNbRuleFired() {
        return nbRuleFired;
    }

}
