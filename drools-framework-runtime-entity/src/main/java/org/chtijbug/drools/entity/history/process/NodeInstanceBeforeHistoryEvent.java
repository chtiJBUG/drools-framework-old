/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chtijbug.drools.entity.history.process;

import org.chtijbug.drools.entity.DroolsNodeInstanceObject;

/**
 * @author nheron
 */
public class NodeInstanceBeforeHistoryEvent extends ProcessHistoryEvent {
    /**
     *
     */
    private static final long serialVersionUID = 7909713944850159592L;
    protected DroolsNodeInstanceObject nodeInstance;

    /**
     *
     */
    public NodeInstanceBeforeHistoryEvent() {
    }

    public NodeInstanceBeforeHistoryEvent(int eventID,DroolsNodeInstanceObject nodeInstance) {
        super(eventID);
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
