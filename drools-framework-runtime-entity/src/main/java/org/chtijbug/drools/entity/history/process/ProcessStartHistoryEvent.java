/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chtijbug.drools.entity.history.process;

import org.chtijbug.drools.entity.DroolsProcessInstanceObject;

/**
 * @author nheron
 */
public class ProcessStartHistoryEvent extends ProcessHistoryEvent {

    private static final long serialVersionUID = -9002244608850950935L;
    protected DroolsProcessInstanceObject processInstance;

    /**
     *
     */
    public ProcessStartHistoryEvent() {
    }

    public ProcessStartHistoryEvent(int eventID,DroolsProcessInstanceObject processInstance,int ruleBaseId,int sessionId) {
        super(eventID,ruleBaseId,sessionId);
        this.processInstance = processInstance;
    }

    public DroolsProcessInstanceObject getProcessInstance() {
        return processInstance;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("ProcessStartHistoryEvent");
        sb.append("{processInstance=").append(processInstance.toString());
        sb.append('}');
        return sb.toString();
    }
}
