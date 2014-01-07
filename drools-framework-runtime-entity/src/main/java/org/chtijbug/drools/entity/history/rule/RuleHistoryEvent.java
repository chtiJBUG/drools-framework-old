/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chtijbug.drools.entity.history.rule;

import org.chtijbug.drools.entity.DroolsRuleObject;
import org.chtijbug.drools.entity.history.HistoryEvent;

import java.util.Date;

/**
 * 
 * @author nheron
 */
public class RuleHistoryEvent extends HistoryEvent {

	private static final long serialVersionUID = 7433690026159716847L;
	protected DroolsRuleObject rule;
    private int ruleInstanceId;
	/**
	 * 
	 */
	public RuleHistoryEvent() {
	}

	public RuleHistoryEvent(int eventID,int ruleInstanceId,DroolsRuleObject rule,int ruleBaseId,int sessionId) {

		super(eventID, new Date(), HistoryEvent.TypeEvent.Rule);
        this.ruleInstanceId = ruleInstanceId;
		this.rule = rule;
        this.setRuleBaseID(ruleBaseId);
        this.setSessionId(sessionId);
	}

    public DroolsRuleObject getRule() {
        return rule;
    }

    public void setRule(DroolsRuleObject rule) {
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
