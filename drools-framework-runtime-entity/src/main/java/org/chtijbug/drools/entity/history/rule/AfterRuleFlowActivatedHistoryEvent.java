package org.chtijbug.drools.entity.history.rule;

import org.chtijbug.drools.entity.DroolsRuleFlowGroupObject;

/**
 * Created by IntelliJ IDEA.
 * Date: 21/02/12
 * Time: 20:49
 * To change this template use File | Settings | File Templates.
 */
public class AfterRuleFlowActivatedHistoryEvent extends RuleFlowHistoryEvent {

    public AfterRuleFlowActivatedHistoryEvent() {
    }

    public AfterRuleFlowActivatedHistoryEvent(int eventID,DroolsRuleFlowGroupObject droolsRuleFlowGroupObject) {
        super(eventID,droolsRuleFlowGroupObject);
    }

}
