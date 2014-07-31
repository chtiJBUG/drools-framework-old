/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chtijbug.drools.entity.history.fact;

import org.chtijbug.drools.entity.history.HistoryEvent;

import java.util.Date;

/**
 * @author nheron
 */
public abstract class FactHistoryEvent extends HistoryEvent {

    /**
     *
     */
    private static final long serialVersionUID = 5320437389177977457L;

    private String ruleName = null;
    private String rulePackageName = null;
    private String ruleflowGroup = null;

    /**
     * Mandatory for GWT Serialization
     */
    public FactHistoryEvent() {

    }

    public FactHistoryEvent(int eventID, Date dateEvent, int ruleBaseId, int sessionId) {

        super(eventID, dateEvent, TypeEvent.Fact);
        this.setRuleBaseID(ruleBaseId);
        this.setSessionId(sessionId);
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getRulePackageName() {
        return rulePackageName;
    }

    public void setRulePackageName(String rulePackageName) {
        this.rulePackageName = rulePackageName;
    }

    public String getRuleflowGroup() {
        return ruleflowGroup;
    }

    public void setRuleflowGroup(String ruleflowGroup) {
        this.ruleflowGroup = ruleflowGroup;
    }

    /*
          * (non-Javadoc)
          *
          * @see org.chtijbug.drools.entity.history.HistoryEvent#toString()
          */
    @Override
    public String toString() {
        return super.toString();
    }
}
