/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chtijbug.drools.entity.history.process;

import org.chtijbug.drools.entity.DroolsNodeInstanceObject;

/**
 *
 * @author nheron
 */
public class NodeInstanceHistoryEvent extends ProcessHistoryEvent{
    private DroolsNodeInstanceObject nodeInstance;

    public NodeInstanceHistoryEvent(DroolsNodeInstanceObject nodeInstance) {
        this.nodeInstance = nodeInstance;
    }

    public DroolsNodeInstanceObject getNodeInstance() {
        return nodeInstance;
    }

    public void setNodeInstance(DroolsNodeInstanceObject nodeInstance) {
        this.nodeInstance = nodeInstance;
    }
    
    
}
