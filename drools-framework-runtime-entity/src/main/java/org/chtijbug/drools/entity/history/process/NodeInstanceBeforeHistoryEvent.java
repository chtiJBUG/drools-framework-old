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
public class NodeInstanceBeforeHistoryEvent extends ProcessHistoryEvent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7909713944850159592L;
	private final DroolsNodeInstanceObject nodeInstance;

	public NodeInstanceBeforeHistoryEvent(DroolsNodeInstanceObject nodeInstance) {
		this.nodeInstance = nodeInstance;
	}

	public DroolsNodeInstanceObject getNodeInstance() {
		return nodeInstance;
	}


}
