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

    public SessionEvent(int eventID) {

        super(eventID, new Date(), TypeEvent.Session);

    }

    public SessionEvent() {
    }



    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("SessionEvent");
        return sb.toString();
    }
}
