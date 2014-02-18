package org.chtijbug.drools.entity.history.process;

import org.chtijbug.drools.entity.DroolsJbpmVariableObject;
import org.chtijbug.drools.entity.DroolsProcessObject;

/**
 * Created by IntelliJ IDEA.
 * Date: 18/02/14
 * Time: 15:17
 * To change this template use File | Settings | File Templates.
 */
public class AfterVariableChangeChangedHistoryEvent extends ProcessHistoryEvent {

    private DroolsJbpmVariableObject oldValue;
    private DroolsJbpmVariableObject newValue;
    private DroolsProcessObject droolsProcessObject;
    public AfterVariableChangeChangedHistoryEvent() {
    }

    public AfterVariableChangeChangedHistoryEvent(int eventID, DroolsProcessObject droolsProcessObject, int ruleBaseId, int sessionId, DroolsJbpmVariableObject oldValue, DroolsJbpmVariableObject newValue) {
        super(eventID, ruleBaseId, sessionId);
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.droolsProcessObject = droolsProcessObject;    }

    public DroolsJbpmVariableObject getOldValue() {
        return oldValue;
    }

    public void setOldValue(DroolsJbpmVariableObject oldValue) {
        this.oldValue = oldValue;
    }

    public DroolsJbpmVariableObject getNewValue() {
        return newValue;
    }

    public void setNewValue(DroolsJbpmVariableObject newValue) {
        this.newValue = newValue;
    }

    public DroolsProcessObject getDroolsProcessObject() {
        return droolsProcessObject;
    }

    public void setDroolsProcessObject(DroolsProcessObject droolsProcessObject) {
        this.droolsProcessObject = droolsProcessObject;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("AfterVariableChangeChangedHistoryEvent{");
        sb.append("oldValue=").append(oldValue);
        sb.append(", newValue=").append(newValue);
        sb.append(", droolsProcessObject=").append(droolsProcessObject);
        sb.append('}');
        return sb.toString();
    }
}
