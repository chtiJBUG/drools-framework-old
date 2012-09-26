package org.chtijbug.drools.entity.history.rule;

import org.chtijbug.drools.entity.DroolsRuleObject;

/**
 * Created by IntelliJ IDEA.
 * Date: 21/02/12
 * Time: 20:54
 * To change this template use File | Settings | File Templates.
 */
public class AfterRuleFlowDeactivatedHistoryEvent extends RuleHistoryEvent {
    private String ruleFlowGroupName;
    public AfterRuleFlowDeactivatedHistoryEvent() {
    }

    public AfterRuleFlowDeactivatedHistoryEvent(DroolsRuleObject rule) {
        super(rule);
    }

    public String getRuleFlowGroupName() {
        return ruleFlowGroupName;
    }

    public void setRuleFlowGroupName(String ruleFlowGroupName) {
        this.ruleFlowGroupName = ruleFlowGroupName;
    }
}
