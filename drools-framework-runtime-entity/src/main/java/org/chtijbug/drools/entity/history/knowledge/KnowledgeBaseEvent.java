package org.chtijbug.drools.entity.history.knowledge;

import org.chtijbug.drools.entity.history.HistoryEvent;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * Date: 05/06/13
 * Time: 11:04
 * To change this template use File | Settings | File Templates.
 */
public class KnowledgeBaseEvent extends HistoryEvent {
    private int ruleBaseID;


    public KnowledgeBaseEvent(int eventID, Date dateEvent, int ruleBaseID) {
        super(eventID, dateEvent, TypeEvent.KnowledgeBaseSingleton);
        this.ruleBaseID = ruleBaseID;
    }

    public int getRuleBaseID() {
        return ruleBaseID;
    }

    public void setRuleBaseID(int ruleBaseID) {
        this.ruleBaseID = ruleBaseID;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("KnowledgeBaseEvent");
        sb.append("{ruleBaseID=").append(ruleBaseID);
        sb.append('}');
        return sb.toString();
    }
}
