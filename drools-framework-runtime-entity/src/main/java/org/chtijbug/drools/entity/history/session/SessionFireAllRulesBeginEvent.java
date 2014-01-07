package org.chtijbug.drools.entity.history.session;

/**
 * Created by IntelliJ IDEA.
 * Date: 05/06/13
 * Time: 12:10
 * To change this template use File | Settings | File Templates.
 */
public class SessionFireAllRulesBeginEvent extends SessionEvent {
    public SessionFireAllRulesBeginEvent(int eventID,int ruleBaseId,int sessionId) {
        super(eventID,ruleBaseId,sessionId);
    }

    public SessionFireAllRulesBeginEvent() {
    }
}
