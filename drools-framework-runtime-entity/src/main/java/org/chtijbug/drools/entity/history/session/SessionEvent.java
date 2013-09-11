package org.chtijbug.drools.entity.history.session;

import org.chtijbug.drools.entity.history.HistoryEvent;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * Date: 05/06/13
 * Time: 11:04
 * To change this template use File | Settings | File Templates.
 */
public class SessionEvent extends HistoryEvent {
    private int sessionId;

    public SessionEvent(int eventID, Date dateEvent, int sessionId) {

        super(eventID, dateEvent, TypeEvent.Session);
        this.sessionId = sessionId;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("SessionEvent");
        sb.append("{sessionId=").append(sessionId);
        sb.append('}');
        return sb.toString();
    }
}
