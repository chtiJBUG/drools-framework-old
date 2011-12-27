/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chtijbug.drools.entity.history.rule;

import java.util.Date;

import org.chtijbug.drools.entity.DroolsRuleObject;
import org.chtijbug.drools.entity.history.HistoryEvent;

/**
 * 
 * @author nheron
 */
public class RuleHistoryEvent extends HistoryEvent {

	private static final long serialVersionUID = 7433690026159716847L;
	protected DroolsRuleObject rule;

	/**
	 * 
	 */
	public RuleHistoryEvent() {
	}

	public RuleHistoryEvent(DroolsRuleObject rule) {

		super(new Date(), HistoryEvent.TypeEvent.Rule);
		this.rule = rule;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.chtijbug.drools.entity.history.HistoryEvent#toString()
	 */
	@Override
	public String toString() {

		return super.toString() + "\n" + rule;
	}
}
