package org.chtijbug.drools.entity.history.session;

/**
 * Created by IntelliJ IDEA.
 * Date: 05/06/13
 * Time: 12:12
 * To change this template use File | Settings | File Templates.
 */
public class SessionStartProcessEndEvent extends SessionEvent {
    private String processName;
    private String processInstanceId;
    public SessionStartProcessEndEvent(int eventID, String processName,int ruleBaseId,int sessionId,String processInstanceId) {
        super(eventID,ruleBaseId,sessionId);
        this.processName = processName;
        this.processInstanceId = processInstanceId;
    }

    public SessionStartProcessEndEvent() {
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("SessionStartProcessEndEvent{");
        sb.append("processName='").append(processName).append('\'');
        sb.append(", processInstanceId='").append(processInstanceId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
