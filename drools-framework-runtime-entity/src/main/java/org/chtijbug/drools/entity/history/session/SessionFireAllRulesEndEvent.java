package org.chtijbug.drools.entity.history.session;

/**
 * Created by IntelliJ IDEA.
 * Date: 05/06/13
 * Time: 12:11
 * To change this template use File | Settings | File Templates.
 */
public class SessionFireAllRulesEndEvent extends SessionEvent {
    private long executionTime;
    private long numberRulesExecuted ;
    public SessionFireAllRulesEndEvent(int eventID,int ruleBaseId,int sessionId,long executionTime,long numberRulesExecuted) {
        super(eventID,ruleBaseId,sessionId);
        this.executionTime = executionTime;
        this.numberRulesExecuted = numberRulesExecuted;
    }

    public SessionFireAllRulesEndEvent() {
    }

    public long getExecutionTime() {
        return executionTime;
    }

    public long getNumberRulesExecuted() {
        return numberRulesExecuted;
    }
}
