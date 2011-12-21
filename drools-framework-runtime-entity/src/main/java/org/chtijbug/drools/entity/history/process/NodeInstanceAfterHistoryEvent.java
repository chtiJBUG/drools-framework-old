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
public class NodeInstanceAfterHistoryEvent extends ProcessHistoryEvent {

	private static final long serialVersionUID = 1117121703139545755L;
	protected DroolsNodeInstanceObject nodeInstance;

	public NodeInstanceAfterHistoryEvent(DroolsNodeInstanceObject nodeInstance) {
		this.nodeInstance = nodeInstance;
	}

	public DroolsNodeInstanceObject getNodeInstance() {
		return nodeInstance;
	}
}
