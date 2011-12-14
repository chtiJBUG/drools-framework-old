/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chtijbug.drools.runtime.impl;

import org.drools.event.process.DefaultProcessEventListener;
import org.drools.event.process.ProcessCompletedEvent;
import org.drools.event.process.ProcessNodeLeftEvent;
import org.drools.event.process.ProcessStartedEvent;
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
        super.afterNodeLeft(event);
    }

    @Override
    public void afterProcessCompleted(ProcessCompletedEvent event) {
        super.afterProcessCompleted(event);
    }

    @Override
    public void afterProcessStarted(ProcessStartedEvent event) {
        super.afterProcessStarted(event);
    }
    
    
    
}
