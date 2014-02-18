/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chtijbug.drools.entity.history.process;

import org.chtijbug.drools.entity.DroolsProcessInstanceObject;

/**
 * @author nheron
 */
public class AfterProcessStartHistoryEvent extends ProcessHistoryEvent {


    protected DroolsProcessInstanceObject processInstance;

    /**
     *
     */
    public AfterProcessStartHistoryEvent() {
    }

    public AfterProcessStartHistoryEvent(int eventID, DroolsProcessInstanceObject processInstance, int ruleBaseId, int sessionId) {
        super(eventID,ruleBaseId,sessionId);
        this.processInstance = processInstance;
    }

    public DroolsProcessInstanceObject getProcessInstance() {
        return processInstance;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("AfterProcessStartHistoryEvent{");
        sb.append("processInstance=").append(processInstance);
        sb.append('}');
        return sb.toString();
    }
}
