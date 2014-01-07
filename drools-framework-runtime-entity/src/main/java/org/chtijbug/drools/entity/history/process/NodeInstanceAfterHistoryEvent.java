/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chtijbug.drools.entity.history.process;

import org.chtijbug.drools.entity.DroolsNodeInstanceObject;

/**
 * @author nheron
 */
public class NodeInstanceAfterHistoryEvent extends ProcessHistoryEvent {

    private static final long serialVersionUID = 1117121703139545755L;
    protected DroolsNodeInstanceObject nodeInstance;

    /**
     *
     */
    public NodeInstanceAfterHistoryEvent() {
    }

    public NodeInstanceAfterHistoryEvent(int eventID,DroolsNodeInstanceObject nodeInstance,int ruleBaseId,int sessionId) {
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
        sb.append("NodeInstanceAfterHistoryEvent");
        sb.append("{nodeInstance=").append(nodeInstance);
        sb.append('}');
        return sb.toString();
    }
}
