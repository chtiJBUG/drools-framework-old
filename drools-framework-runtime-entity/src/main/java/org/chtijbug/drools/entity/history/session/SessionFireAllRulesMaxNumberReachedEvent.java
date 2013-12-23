package org.chtijbug.drools.entity.history.session;

/**
 * Created by IntelliJ IDEA.
 * Date: 05/06/13
 * Time: 16:38
 * To change this template use File | Settings | File Templates.
 */
public class SessionFireAllRulesMaxNumberReachedEvent extends SessionEvent {
    private int numberOfRulesExecuted=0;
    private int maxNumberOfRulesForSession=0;

    public SessionFireAllRulesMaxNumberReachedEvent(int eventID, int numberOfRulesExecuted, int maxNumberOfRulesForSession) {
        super(eventID);
        this.numberOfRulesExecuted = numberOfRulesExecuted;
        this.maxNumberOfRulesForSession = maxNumberOfRulesForSession;
    }

    public SessionFireAllRulesMaxNumberReachedEvent() {
    }

    public int getNumberOfRulesExecuted() {
        return numberOfRulesExecuted;
    }

    public void setNumberOfRulesExecuted(int numberOfRulesExecuted) {
        this.numberOfRulesExecuted = numberOfRulesExecuted;
    }

    public int getMaxNumberOfRulesForSession() {
        return maxNumberOfRulesForSession;
    }

    public void setMaxNumberOfRulesForSession(int maxNumberOfRulesForSession) {
        this.maxNumberOfRulesForSession = maxNumberOfRulesForSession;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("SessionFireAllRulesMaxNumberReachedEvent");
        sb.append("{numberOfRulesExecuted=").append(numberOfRulesExecuted);
        sb.append(", maxNumberOfRulesForSession=").append(maxNumberOfRulesForSession);
        sb.append('}');
        return sb.toString();
    }
}
