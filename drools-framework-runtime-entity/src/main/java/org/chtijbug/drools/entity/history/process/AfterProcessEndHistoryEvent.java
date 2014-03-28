/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chtijbug.drools.entity.history.process;

import org.chtijbug.drools.entity.DroolsProcessInstanceObject;

/**
 * @author nheron
 */
public class AfterProcessEndHistoryEvent extends ProcessHistoryEvent {

    private static final long serialVersionUID = 6327973844897501016L;

    /**
     *
     */
    public AfterProcessEndHistoryEvent() {
    }

    public AfterProcessEndHistoryEvent(int eventID, DroolsProcessInstanceObject processInstance, int ruleBaseId, int sessionId) {
        super(eventID,ruleBaseId,sessionId);
        this.processInstance = processInstance;
    }

    public DroolsProcessInstanceObject getProcessInstance() {
        return processInstance;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("ProcessEndHistoryEvent");
        sb.append("{processInstance=").append(processInstance.toString());
        sb.append('}');
        return sb.toString();
    }
}
