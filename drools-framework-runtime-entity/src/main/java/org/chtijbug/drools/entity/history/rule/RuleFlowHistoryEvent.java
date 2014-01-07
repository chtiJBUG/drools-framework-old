package org.chtijbug.drools.entity.history.rule;

import org.chtijbug.drools.entity.DroolsRuleFlowGroupObject;
import org.chtijbug.drools.entity.history.HistoryEvent;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * Date: 23/11/12
 * Time: 15:10
 * To change this template use File | Settings | File Templates.
 */
public class RuleFlowHistoryEvent extends HistoryEvent {
        /**
    	 *
    	 */
        private DroolsRuleFlowGroupObject droolsRuleFlowGroupObject;
    	public RuleFlowHistoryEvent() {
    	}

    	public RuleFlowHistoryEvent(int eventID,DroolsRuleFlowGroupObject droolsRuleFlowGroupObject,int ruleBaseId,int sessionId) {


    		super(eventID, new Date(), HistoryEvent.TypeEvent.RuleFlowGroup);
            this.droolsRuleFlowGroupObject =droolsRuleFlowGroupObject;
            this.setRuleBaseID(ruleBaseId);
            this.setSessionId(sessionId);

    	}

}
