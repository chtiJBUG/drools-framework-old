/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chtijbug.drools.entity.history.rule;

import java.util.ArrayList;

import org.chtijbug.drools.entity.history.FactObject;

/**
 * 
 * @author nheron
 */
public class RuleFiredHistoryEvent extends RuleHistoryEvent {

	private final ArrayList<FactObject> whenObjects;

	public RuleFiredHistoryEvent(String ruleName) {
		super(ruleName);
		this.whenObjects = new ArrayList<FactObject>();
	}

	public ArrayList<FactObject> getWhenObjects() {
		return whenObjects;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.chtijbug.drools.entity.history.HistoryEvent#toString()
	 */
	@Override
	public String toString() {

		StringBuilder str = new StringBuilder();
		str.append(super.toString() + "\n");

		str.append("When objects :\n");
		for (FactObject fact : whenObjects) {
			str.append("- " + fact + "\n");
		}
		return str.toString();
	}

}
