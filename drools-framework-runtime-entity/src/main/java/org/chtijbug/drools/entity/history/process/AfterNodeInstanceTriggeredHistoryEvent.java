/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chtijbug.drools.entity.history.process;

import org.chtijbug.drools.entity.DroolsNodeInstanceObject;

/**
 * @author nheron
 */
public class AfterNodeInstanceTriggeredHistoryEvent extends ProcessHistoryEvent {
    /**
     *
     */
    private static final long serialVersionUID = 7909713944850159592L;
    protected DroolsNodeInstanceObject nodeInstance;

    /**
     *
     */
    public AfterNodeInstanceTriggeredHistoryEvent() {
    }

    public AfterNodeInstanceTriggeredHistoryEvent(int eventID, DroolsNodeInstanceObject nodeInstance, int ruleBaseId, int sessionId) {
        super(eventID,ruleBaseId,sessionId);
        this.nodeInstance = nodeInstance;
    }

    public DroolsNodeInstanceObject getNodeInstance() {
        return nodeInstance;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append(super.toString() + "\n");
        sb.append("NodeInstanceBeforeHistoryEvent");
        sb.append("{nodeInstance=").append(nodeInstance);
        sb.append('}');
        return sb.toString();
    }
}
