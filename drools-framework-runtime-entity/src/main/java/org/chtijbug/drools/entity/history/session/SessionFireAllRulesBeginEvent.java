package org.chtijbug.drools.entity.history.session;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * Date: 05/06/13
 * Time: 12:10
 * To change this template use File | Settings | File Templates.
 */
public class SessionFireAllRulesBeginEvent extends SessionEvent {
    public SessionFireAllRulesBeginEvent(int eventID, Date dateEvent,  int sessionId) {
        super(eventID, dateEvent,  sessionId);
    }
}
