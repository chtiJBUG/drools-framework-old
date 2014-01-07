/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chtijbug.drools.entity.history.rule;

import org.chtijbug.drools.entity.DroolsRuleObject;

/**
 * @author nheron
 */
public class AfterRuleFiredHistoryEvent extends RuleHistoryEvent {

    /**
     *
     */
    private static final long serialVersionUID = -8587421328193577240L;

    /**
     *
     */
    public AfterRuleFiredHistoryEvent() {
    }

    public AfterRuleFiredHistoryEvent(int eventID,int ruleInstanceID,DroolsRuleObject rule,int ruleBaseId,int sessionId) {

        super(eventID,ruleInstanceID,rule,ruleBaseId,sessionId);
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

        str.append("End Rule :\n");
        return str.toString();
    }

}
