/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chtijbug.drools.entity.history.process;

import org.chtijbug.drools.entity.DroolsProcessInstanceObject;

/**
 *
 * @author nheron
 */
public class ProcessEndHistoryEvent extends ProcessHistoryEvent {
    private final DroolsProcessInstanceObject processInstance;

    public ProcessEndHistoryEvent(DroolsProcessInstanceObject processInstance) {
        this.processInstance = processInstance;
    }

    public DroolsProcessInstanceObject getProcessInstance() {
        return processInstance;
    } 
}
