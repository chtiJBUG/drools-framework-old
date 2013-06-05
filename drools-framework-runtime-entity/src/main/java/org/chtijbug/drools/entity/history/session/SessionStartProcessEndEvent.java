package org.chtijbug.drools.entity.history.session;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * Date: 05/06/13
 * Time: 12:12
 * To change this template use File | Settings | File Templates.
 */
public class SessionStartProcessEndEvent extends SessionEvent {
    private String processName;

    public SessionStartProcessEndEvent(int eventID, Date dateEvent, int sessionId, String processName) {
        super(eventID, dateEvent, sessionId);
        this.processName = processName;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("SessionStartProcessEndEvent");
        sb.append("{processName='").append(processName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
