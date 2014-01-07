/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chtijbug.drools.runtime.impl;

import org.chtijbug.drools.entity.DroolsNodeInstanceObject;
import org.chtijbug.drools.entity.DroolsProcessInstanceObject;
import org.chtijbug.drools.entity.history.process.NodeInstanceAfterHistoryEvent;
import org.chtijbug.drools.entity.history.process.ProcessEndHistoryEvent;
import org.chtijbug.drools.entity.history.process.ProcessStartHistoryEvent;
import org.drools.event.process.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author nheron
 */
public class ProcessHandlerListener extends DefaultProcessEventListener {
    /** Class logger */
    private static Logger logger = LoggerFactory.getLogger(ProcessHandlerListener.class);
    /** The knowledge session sending event */
    private final RuleBaseStatefulSession ruleBaseSession;

    public ProcessHandlerListener(RuleBaseStatefulSession ruleBaseSession) {
        this.ruleBaseSession = ruleBaseSession;
    }

    @Override
    public void afterNodeLeft(ProcessNodeLeftEvent event) {
        logger.debug(">>afterNodeLeft", event);
        try {
            DroolsNodeInstanceObject droolsNodeInstanceObject = ruleBaseSession.getDroolsNodeInstanceObject(event.getNodeInstance());
            NodeInstanceAfterHistoryEvent afterHistoryEvent = new NodeInstanceAfterHistoryEvent(this.ruleBaseSession.getNextEventCounter(),droolsNodeInstanceObject,this.ruleBaseSession.getRuleBaseID(),this.ruleBaseSession.getSessionId());
            ruleBaseSession.addHistoryElement(afterHistoryEvent);
        } finally {
            logger.debug("<<afterNodeLeft");
        }
    }

    @Override
    public void afterProcessCompleted(ProcessCompletedEvent event) {
        logger.debug(">>afterProcessCompleted", event);
        try {
            DroolsProcessInstanceObject droolsProcessInstanceObject = ruleBaseSession.getDroolsProcessInstanceObject(event.getProcessInstance());
            ProcessEndHistoryEvent processStart = new ProcessEndHistoryEvent(this.ruleBaseSession.getNextEventCounter(),droolsProcessInstanceObject,this.ruleBaseSession.getRuleBaseID(),this.ruleBaseSession.getSessionId());
            ruleBaseSession.addHistoryElement(processStart);
        } finally {
            logger.debug("<<afterProcessCompleted");
        }
    }

    @Override
    public void afterProcessStarted(ProcessStartedEvent event) {
        logger.debug(">>afterProcessStarted", event);
        try {
            DroolsProcessInstanceObject droolsProcessInstanceObject = ruleBaseSession.getDroolsProcessInstanceObject(event.getProcessInstance());
            ProcessStartHistoryEvent processStart = new ProcessStartHistoryEvent(this.ruleBaseSession.getNextEventCounter(),droolsProcessInstanceObject,this.ruleBaseSession.getRuleBaseID(),this.ruleBaseSession.getSessionId());
            ruleBaseSession.addHistoryElement(processStart);
        } finally {
            logger.debug("<<afterProcessStarted");
        }
    }

    @Override
    public void beforeNodeTriggered(ProcessNodeTriggeredEvent event) {
        logger.debug(">>beforeNodeTriggered", event);
        try {
            DroolsNodeInstanceObject droolsNodeInstanceObject = ruleBaseSession.getDroolsNodeInstanceObject(event.getNodeInstance());
            NodeInstanceAfterHistoryEvent afterHistoryEvent = new NodeInstanceAfterHistoryEvent(this.ruleBaseSession.getNextEventCounter(),droolsNodeInstanceObject,this.ruleBaseSession.getRuleBaseID(),this.ruleBaseSession.getSessionId());
            ruleBaseSession.addHistoryElement(afterHistoryEvent);
        } finally {
            logger.debug("<<beforeNodeTriggered");
        }
    }
}
