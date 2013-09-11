package org.chtijbug.drools.entity.history.session;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * Date: 05/06/13
 * Time: 12:11
 * To change this template use File | Settings | File Templates.
 */
public class SessionFireAllRulesEndEvent extends SessionEvent {
    public SessionFireAllRulesEndEvent(int eventID, Date dateEvent, int sessionId) {
        super(eventID, dateEvent, sessionId);
    }
}
