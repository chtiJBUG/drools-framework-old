/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chtijbug.drools.entity.history.fact;

import org.chtijbug.drools.entity.DroolsFactObject;
import org.chtijbug.drools.entity.DroolsFactObjectAttribute;

import java.util.Date;

/**
 * @author nheron
 */
public class UpdatedFactHistoryEvent extends FactHistoryEvent {
    /**
     *
     */
    private static final long serialVersionUID = 2427277089998136875L;
    protected DroolsFactObject objectOldValue;
    protected DroolsFactObject objectNewValue;

    /**
     *
     */
    public UpdatedFactHistoryEvent() {
    }

    public UpdatedFactHistoryEvent(DroolsFactObject objectOldValue, DroolsFactObject objectNewValue) {
        super(new Date());
        this.objectOldValue = objectOldValue;
        this.objectNewValue = objectNewValue;

    }

    public DroolsFactObject getObjectNewValue() {
        return objectNewValue;
    }

    public void setObjectNewValue(DroolsFactObject objectNewValue) {
        this.objectNewValue = objectNewValue;
    }

    public DroolsFactObject getObjectOldValue() {
        return objectOldValue;
    }

    public void setObjectOldValue(DroolsFactObject objectOldValue) {
        this.objectOldValue = objectOldValue;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("UpdatedFactHistoryEvent");
        sb.append("Update Object : " + objectNewValue.getFullClassName() + "\n");
        sb.append("{objectOldValue=").append("\n");
        sb.append("version Object : " + objectOldValue.getObjectVersion() + "\n");
        sb.append("attributes :\n");
        for (DroolsFactObjectAttribute foa : objectOldValue.getListfactObjectAttributes()) {
            sb.append("- " + foa.getAttributeType() + " " + foa.getAttributeName() + "=" + foa.getAttributeValue() + "\n");
        }
        sb.append(", objectNewValue=").append(objectNewValue);
        sb.append("version Object : " + objectNewValue.getObjectVersion() + "\n");
        sb.append("attributes :\n");
        for (DroolsFactObjectAttribute foa : objectNewValue.getListfactObjectAttributes()) {
            sb.append("- " + foa.getAttributeType() + " " + foa.getAttributeName() + "=" + foa.getAttributeValue() + "\n");
        }
        sb.append('}');
        return sb.toString();
    }
}
