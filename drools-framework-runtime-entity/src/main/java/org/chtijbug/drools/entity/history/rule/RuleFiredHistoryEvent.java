/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chtijbug.drools.entity.history.rule;

import java.util.ArrayList;
import org.chtijbug.drools.entity.DroolsFactObject;
import org.chtijbug.drools.entity.DroolsRuleObject;

/**
 * 
 * @author nheron
 */
public class RuleFiredHistoryEvent extends RuleHistoryEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8587421328193577240L;
	private final ArrayList<DroolsFactObject> whenObjects;

	public RuleFiredHistoryEvent(DroolsRuleObject rule) {

		super(rule);
		this.whenObjects = new ArrayList<DroolsFactObject>();
	}

	public ArrayList<DroolsFactObject> getWhenObjects() {
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
		for (DroolsFactObject fact : whenObjects) {
			str.append("- " + fact + "\n");
		}
		return str.toString();
	}

}
