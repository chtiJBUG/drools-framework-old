/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chtijbug.drools.runtime.impl;

import org.chtijbug.drools.entity.DroolsJbpmVariableObject;
import org.chtijbug.drools.entity.DroolsNodeInstanceObject;
import org.chtijbug.drools.entity.DroolsProcessInstanceObject;
import org.chtijbug.drools.entity.DroolsProcessObject;
import org.chtijbug.drools.entity.history.process.*;
import org.drools.event.process.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author nheron
 */
public class ProcessHandlerListener extends DefaultProcessEventListener {
    /**
     * Class logger
     */
    private static Logger logger = LoggerFactory.getLogger(ProcessHandlerListener.class);
    /**
     * The knowledge session sending event
     */
    private final RuleBaseStatefulSession ruleBaseSession;

    public ProcessHandlerListener(RuleBaseStatefulSession ruleBaseSession) {
        this.ruleBaseSession = ruleBaseSession;
    }

    @Override
    public void beforeProcessStarted(ProcessStartedEvent event) {
        logger.debug(">>beforeProcessStarted", event);
        try {
            DroolsProcessInstanceObject droolsProcessInstanceObject = ruleBaseSession.getDroolsProcessInstanceObject(event.getProcessInstance());
            BeforeProcessStartHistoryEvent beforeProcessStart = new BeforeProcessStartHistoryEvent(this.ruleBaseSession.getNextEventCounter(), droolsProcessInstanceObject, this.ruleBaseSession.getRuleBaseID(), this.ruleBaseSession.getSessionId());
            ruleBaseSession.addHistoryElement(beforeProcessStart);
        } finally {
            logger.debug("<<beforeProcessStarted");
        }
    }


    @Override
    public void afterProcessStarted(ProcessStartedEvent event) {
        logger.debug(">>afterProcessStarted", event);
        try {

            DroolsProcessInstanceObject droolsProcessInstanceObject = ruleBaseSession.getDroolsProcessInstanceObject(event.getProcessInstance());
            AfterProcessStartHistoryEvent afterProcessStart = new AfterProcessStartHistoryEvent(this.ruleBaseSession.getNextEventCounter(), droolsProcessInstanceObject, this.ruleBaseSession.getRuleBaseID(), this.ruleBaseSession.getSessionId());
            ruleBaseSession.addHistoryElement(afterProcessStart);
        } finally {
            logger.debug("<<afterProcessStarted");
        }
    }

    @Override
    public void beforeProcessCompleted(ProcessCompletedEvent event) {
        logger.debug(">>beforeProcessCompleted", event);
        try {
            DroolsProcessInstanceObject droolsProcessInstanceObject = ruleBaseSession.getDroolsProcessInstanceObject(event.getProcessInstance());
            BeforeProcessEndHistoryEvent beforeProcessEndHistoryEvent = new BeforeProcessEndHistoryEvent(this.ruleBaseSession.getNextEventCounter(), droolsProcessInstanceObject, this.ruleBaseSession.getRuleBaseID(), this.ruleBaseSession.getSessionId());
            ruleBaseSession.addHistoryElement(beforeProcessEndHistoryEvent);
        } finally {
            logger.debug("<<beforeProcessCompleted");
        }
    }

    @Override
    public void afterProcessCompleted(ProcessCompletedEvent event) {
        logger.debug(">>afterProcessCompleted", event);
        try {
            DroolsProcessInstanceObject droolsProcessInstanceObject = ruleBaseSession.getDroolsProcessInstanceObject(event.getProcessInstance());
            AfterProcessEndHistoryEvent AfterProcessStart = new AfterProcessEndHistoryEvent(this.ruleBaseSession.getNextEventCounter(), droolsProcessInstanceObject, this.ruleBaseSession.getRuleBaseID(), this.ruleBaseSession.getSessionId());
            ruleBaseSession.addHistoryElement(AfterProcessStart);
        } finally {
            logger.debug("<<afterProcessCompleted");
        }
    }

    @Override
    public void beforeNodeTriggered(ProcessNodeTriggeredEvent event) {
        logger.debug(">>beforeNodeTriggered", event);
        try {
            DroolsNodeInstanceObject droolsNodeInstanceObject = ruleBaseSession.getDroolsNodeInstanceObject(event.getNodeInstance());
            BeforeNodeInstanceTriggeredHistoryEvent beforeNodeInstanceTriggeredHistoryEvent = new BeforeNodeInstanceTriggeredHistoryEvent(this.ruleBaseSession.getNextEventCounter(), droolsNodeInstanceObject, this.ruleBaseSession.getRuleBaseID(), this.ruleBaseSession.getSessionId());
            ruleBaseSession.addHistoryElement(beforeNodeInstanceTriggeredHistoryEvent);
        } finally {
            logger.debug("<<beforeNodeTriggered");
        }
    }

    @Override
    public void afterNodeTriggered(ProcessNodeTriggeredEvent event) {
        logger.debug(">>afterNodeTriggered", event);
        try {
            DroolsNodeInstanceObject droolsNodeInstanceObject = ruleBaseSession.getDroolsNodeInstanceObject(event.getNodeInstance());
            AfterNodeInstanceTriggeredHistoryEvent afterNodeInstanceTriggeredHistoryEvent = new AfterNodeInstanceTriggeredHistoryEvent(this.ruleBaseSession.getNextEventCounter(), droolsNodeInstanceObject, this.ruleBaseSession.getRuleBaseID(), this.ruleBaseSession.getSessionId());
            ruleBaseSession.addHistoryElement(afterNodeInstanceTriggeredHistoryEvent);
        } finally {
            logger.debug("<<afterNodeTriggered");
        }
    }

    @Override
    public void beforeNodeLeft(ProcessNodeLeftEvent event) {
        logger.debug(">>beforeNodeLeft", event);
        try {

            DroolsNodeInstanceObject droolsNodeInstanceObject = ruleBaseSession.getDroolsNodeInstanceObject(event.getNodeInstance());
            BeforeNodeLeftHistoryEvent afterHistoryEvent = new BeforeNodeLeftHistoryEvent(this.ruleBaseSession.getNextEventCounter(), droolsNodeInstanceObject, this.ruleBaseSession.getRuleBaseID(), this.ruleBaseSession.getSessionId());
            ruleBaseSession.addHistoryElement(afterHistoryEvent);
        } finally {
            logger.debug("<<beforeNodeLeft");
        }
    }

    @Override
    public void afterNodeLeft(ProcessNodeLeftEvent event) {
        logger.debug(">>afterNodeLeft", event);
        try {

            DroolsNodeInstanceObject droolsNodeInstanceObject = ruleBaseSession.getDroolsNodeInstanceObject(event.getNodeInstance());
            AfterNodeLeftHistoryEvent afterHistoryEvent = new AfterNodeLeftHistoryEvent(this.ruleBaseSession.getNextEventCounter(), droolsNodeInstanceObject, this.ruleBaseSession.getRuleBaseID(), this.ruleBaseSession.getSessionId());
            ruleBaseSession.addHistoryElement(afterHistoryEvent);
        } finally {
            logger.debug("<<afterNodeLeft");
        }
    }

    @Override
    public void beforeVariableChanged(ProcessVariableChangedEvent event) {
        logger.debug(">>beforeVariableChanged", event);
        try {

            DroolsJbpmVariableObject oldValue = new DroolsJbpmVariableObject(event.getVariableId(), event.getVariableInstanceId(), event.getOldValue());
            DroolsProcessObject droolsProcessObject = new DroolsProcessObject(String.valueOf(event.getProcessInstance().getId()), event.getProcessInstance().getProcessName(), event.getProcessInstance().getProcess().getPackageName(), event.getProcessInstance().getProcess().getType(), event.getProcessInstance().getProcess().getVersion());
            BeforeVariableChangeChangedHistoryEvent beforeVariableChangeChangedHistoryEvent = new BeforeVariableChangeChangedHistoryEvent(this.ruleBaseSession.getNextEventCounter(), droolsProcessObject, this.ruleBaseSession.getRuleBaseID(), this.ruleBaseSession.getSessionId(), oldValue);
            ruleBaseSession.addHistoryElement(beforeVariableChangeChangedHistoryEvent);
        } finally {
            logger.debug("<<beforeVariableChanged");
        }
    }

    @Override
    public void afterVariableChanged(ProcessVariableChangedEvent event) {
        logger.debug(">>afterVariableChanged", event);
        try {

            DroolsJbpmVariableObject oldValue = new DroolsJbpmVariableObject(event.getVariableId(), event.getVariableInstanceId(), event.getOldValue());
            DroolsJbpmVariableObject newValue = new DroolsJbpmVariableObject(event.getVariableId(), event.getVariableInstanceId(), event.getNewValue());
            DroolsProcessObject droolsProcessObject = new DroolsProcessObject(String.valueOf(event.getProcessInstance().getId()), event.getProcessInstance().getProcessName(), event.getProcessInstance().getProcess().getPackageName(), event.getProcessInstance().getProcess().getType(), event.getProcessInstance().getProcess().getVersion());
            AfterVariableChangeChangedHistoryEvent afterVariableChangeChangedHistoryEvent = new AfterVariableChangeChangedHistoryEvent(this.ruleBaseSession.getNextEventCounter(), droolsProcessObject, this.ruleBaseSession.getRuleBaseID(), this.ruleBaseSession.getSessionId(), oldValue, newValue);
            ruleBaseSession.addHistoryElement(afterVariableChangeChangedHistoryEvent);
        } finally {
            logger.debug("<<afterVariableChanged");
        }
    }


}
