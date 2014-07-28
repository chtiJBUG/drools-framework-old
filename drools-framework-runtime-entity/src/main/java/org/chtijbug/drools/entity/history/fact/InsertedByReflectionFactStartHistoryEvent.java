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
public class InsertedByReflectionFactStartHistoryEvent extends FactHistoryEvent {

    protected DroolsFactObject topObject;

    public InsertedByReflectionFactStartHistoryEvent() {
    }

    public InsertedByReflectionFactStartHistoryEvent(int eventID, DroolsFactObject topObject, int ruleBaseId, int sessionId) {
        super(eventID,new Date(),ruleBaseId,sessionId);
        this.topObject = topObject;
    }

    public DroolsFactObject getTopObject() {
        return topObject;
    }

    public void setTopObject(DroolsFactObject topObject) {
        this.topObject = topObject;
    }

    /*
      * (non-Javadoc)
      *
      * @see org.chtijbug.drools.entity.history.HistoryEvent#toString()
      */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(super.toString() + "\n");

        if (topObject != null) {
            str.append("inserted Top Object : " + topObject.getFullClassName() + "\n");
            str.append("version Object : " + topObject.getObjectVersion() + "\n");
            str.append("attributes :\n");
            for (DroolsFactObjectAttribute foa : topObject.getListfactObjectAttributes()) {
                str.append("- " + foa.getAttributeType() + " " + foa.getAttributeName() + "=" + foa.getAttributeValue() + "\n");
            }

        }

        return str.toString();
    }

}
