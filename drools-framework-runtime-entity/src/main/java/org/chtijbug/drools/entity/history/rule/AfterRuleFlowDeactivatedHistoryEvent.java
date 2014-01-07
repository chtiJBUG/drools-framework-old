package org.chtijbug.drools.entity.history.rule;

import org.chtijbug.drools.entity.DroolsRuleFlowGroupObject;

/**
 * Created by IntelliJ IDEA.
 * Date: 21/02/12
 * Time: 20:54
 * To change this template use File | Settings | File Templates.
 */
public class AfterRuleFlowDeactivatedHistoryEvent extends RuleFlowHistoryEvent {
    public AfterRuleFlowDeactivatedHistoryEvent() {
    }

    public AfterRuleFlowDeactivatedHistoryEvent(int eventID,DroolsRuleFlowGroupObject droolsRuleFlowGroupObject,int ruleBaseId,int sessionId) {
        super(eventID,droolsRuleFlowGroupObject,ruleBaseId,sessionId);
    }
}
