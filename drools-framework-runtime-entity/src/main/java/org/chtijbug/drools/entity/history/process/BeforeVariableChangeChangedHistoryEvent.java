package org.chtijbug.drools.entity.history.process;

import org.chtijbug.drools.entity.DroolsJbpmVariableObject;
import org.chtijbug.drools.entity.DroolsProcessObject;

/**
 * Created by IntelliJ IDEA.
 * Date: 18/02/14
 * Time: 15:17
 * To change this template use File | Settings | File Templates.
 */
public class BeforeVariableChangeChangedHistoryEvent extends ProcessHistoryEvent {

    private DroolsJbpmVariableObject oldValue;
    private DroolsProcessObject droolsProcessObject;
    public BeforeVariableChangeChangedHistoryEvent() {
    }

    public BeforeVariableChangeChangedHistoryEvent(int eventID, DroolsProcessObject droolsProcessObject, int ruleBaseId, int sessionId, DroolsJbpmVariableObject oldValue) {
        super(eventID, ruleBaseId, sessionId);
        this.oldValue = oldValue;
        this.droolsProcessObject = droolsProcessObject;    }

    public DroolsJbpmVariableObject getOldValue() {
        return oldValue;
    }

    public void setOldValue(DroolsJbpmVariableObject oldValue) {
        this.oldValue = oldValue;
    }



    public DroolsProcessObject getDroolsProcessObject() {
        return droolsProcessObject;
    }

    public void setDroolsProcessObject(DroolsProcessObject droolsProcessObject) {
        this.droolsProcessObject = droolsProcessObject;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("BeforeVariableChangeChangedHistoryEvent{");
        sb.append("oldValue=").append(oldValue);
        sb.append(", droolsProcessObject=").append(droolsProcessObject);
        sb.append('}');
        return sb.toString();
    }
}
