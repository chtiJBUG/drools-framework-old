package org.chtijbug.drools.entity.history.session;

/**
 * Created by IntelliJ IDEA.
 * Date: 05/06/13
 * Time: 12:11
 * To change this template use File | Settings | File Templates.
 */
public class SessionStartProcessBeginEvent extends SessionEvent {
    private String processName;

    public SessionStartProcessBeginEvent(int eventID, String processName) {
        super(eventID);
        this.processName = processName;
    }

    public SessionStartProcessBeginEvent() {
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
        sb.append("SessionStartProcessBeginEvent");
        sb.append("{processName='").append(processName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
