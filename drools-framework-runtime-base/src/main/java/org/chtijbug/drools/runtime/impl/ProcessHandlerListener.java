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

    private final RuleBaseStatefullSession ruleBaseSession;
    static final Logger LOGGER = LoggerFactory.getLogger(ProcessHandlerListener.class);

    public ProcessHandlerListener(RuleBaseStatefullSession ruleBaseSession) {

        this.ruleBaseSession = ruleBaseSession;
    }

    @Override
    public void afterNodeLeft(ProcessNodeLeftEvent event) {
        DroolsNodeInstanceObject droolsNodeInstanceObject = ruleBaseSession.getDroolsNodeInstanceObject(event.getNodeInstance());
        NodeInstanceAfterHistoryEvent afterHistoryEvent = new NodeInstanceAfterHistoryEvent(droolsNodeInstanceObject);
        ruleBaseSession.getHistoryContainer().addHistoryElement(afterHistoryEvent);
    }

    @Override
    public void afterProcessCompleted(ProcessCompletedEvent event) {
        DroolsProcessInstanceObject droolsProcessInstanceObject = ruleBaseSession.getDroolsProcessInstanceObject(event.getProcessInstance());
        ProcessEndHistoryEvent processStart = new ProcessEndHistoryEvent(droolsProcessInstanceObject);
        ruleBaseSession.getHistoryContainer().addHistoryElement(processStart);

    }

    @Override
    public void afterProcessStarted(ProcessStartedEvent event) {
        DroolsProcessInstanceObject droolsProcessInstanceObject = ruleBaseSession.getDroolsProcessInstanceObject(event.getProcessInstance());
        ProcessStartHistoryEvent processStart = new ProcessStartHistoryEvent(droolsProcessInstanceObject);
        ruleBaseSession.getHistoryContainer().addHistoryElement(processStart);

    }

    @Override
    public void beforeNodeTriggered(ProcessNodeTriggeredEvent event) {
        DroolsNodeInstanceObject droolsNodeInstanceObject = ruleBaseSession.getDroolsNodeInstanceObject(event.getNodeInstance());
        NodeInstanceAfterHistoryEvent afterHistoryEvent = new NodeInstanceAfterHistoryEvent(droolsNodeInstanceObject);
        ruleBaseSession.getHistoryContainer().addHistoryElement(afterHistoryEvent);
    }
}
