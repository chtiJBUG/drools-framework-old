/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chtijbug.drools.runtime.impl;

import org.chtijbug.drools.common.log.Logger;
import org.chtijbug.drools.common.log.LoggerFactory;
import org.chtijbug.drools.entity.DroolsNodeInstanceObject;
import org.chtijbug.drools.entity.DroolsProcessInstanceObject;
import org.chtijbug.drools.entity.history.process.NodeInstanceAfterHistoryEvent;
import org.chtijbug.drools.entity.history.process.ProcessEndHistoryEvent;
import org.chtijbug.drools.entity.history.process.ProcessStartHistoryEvent;
import org.drools.event.process.*;

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
        logger.entry("afterNodeLeft", event);
        try {
            DroolsNodeInstanceObject droolsNodeInstanceObject = ruleBaseSession.getDroolsNodeInstanceObject(event.getNodeInstance());
            NodeInstanceAfterHistoryEvent afterHistoryEvent = new NodeInstanceAfterHistoryEvent(this.ruleBaseSession.getNextEventCounter(),droolsNodeInstanceObject);
            ruleBaseSession.getHistoryContainer().addHistoryElement(afterHistoryEvent);
        } finally {
            logger.exit("afterNodeLeft");
        }
    }

    @Override
    public void afterProcessCompleted(ProcessCompletedEvent event) {
        logger.entry("afterProcessCompleted", event);
        try {
            DroolsProcessInstanceObject droolsProcessInstanceObject = ruleBaseSession.getDroolsProcessInstanceObject(event.getProcessInstance());
            ProcessEndHistoryEvent processStart = new ProcessEndHistoryEvent(this.ruleBaseSession.getNextEventCounter(),droolsProcessInstanceObject);
            ruleBaseSession.getHistoryContainer().addHistoryElement(processStart);
        } finally {
            logger.exit("afterProcessCompleted");
        }
    }

    @Override
    public void afterProcessStarted(ProcessStartedEvent event) {
        logger.entry("afterProcessStarted", event);
        try {
            DroolsProcessInstanceObject droolsProcessInstanceObject = ruleBaseSession.getDroolsProcessInstanceObject(event.getProcessInstance());
            ProcessStartHistoryEvent processStart = new ProcessStartHistoryEvent(this.ruleBaseSession.getNextEventCounter(),droolsProcessInstanceObject);
            ruleBaseSession.getHistoryContainer().addHistoryElement(processStart);
        } finally {
            logger.exit("afterProcessStarted");
        }
    }

    @Override
    public void beforeNodeTriggered(ProcessNodeTriggeredEvent event) {
        logger.entry("beforeNodeTriggered", event);
        try {
            DroolsNodeInstanceObject droolsNodeInstanceObject = ruleBaseSession.getDroolsNodeInstanceObject(event.getNodeInstance());
            NodeInstanceAfterHistoryEvent afterHistoryEvent = new NodeInstanceAfterHistoryEvent(this.ruleBaseSession.getNextEventCounter(),droolsNodeInstanceObject);
            ruleBaseSession.getHistoryContainer().addHistoryElement(afterHistoryEvent);
        } finally {
            logger.exit("beforeNodeTriggered");
        }
    }
}
