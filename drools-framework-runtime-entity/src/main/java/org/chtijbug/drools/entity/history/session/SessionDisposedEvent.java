package org.chtijbug.drools.entity.history.session;

/**
 * Created by IntelliJ IDEA.
 * Date: 05/06/13
 * Time: 12:09
 * To change this template use File | Settings | File Templates.
 */
public class SessionDisposedEvent extends SessionEvent {
    public SessionDisposedEvent(int eventID) {
        super(eventID);
    }

    public SessionDisposedEvent() {
    }
}
