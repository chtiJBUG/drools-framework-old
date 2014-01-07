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
public class DeletedFactHistoryEvent extends FactHistoryEvent {

    /**
     *
     */
    private static final long serialVersionUID = -1924924006386653359L;
    protected DroolsFactObject deletedObject;

    /**
     *
     */
    public DeletedFactHistoryEvent() {
    }

    public DeletedFactHistoryEvent(int eventID,DroolsFactObject deletedObject,int ruleBaseId,int sessionId) {
        super(eventID,new Date(),ruleBaseId,sessionId);
        this.deletedObject = deletedObject;
    }

    public DroolsFactObject getDeletedObject() {
        return deletedObject;
    }

    public void setDeletedObject(DroolsFactObject deletedObject) {
        this.deletedObject = deletedObject;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(super.toString() + "\n");
        str.append("retracted Object : " + deletedObject.getFullClassName() + "\n");
        str.append("version Object : " + deletedObject.getObjectVersion() + "\n");
        str.append("attributes :\n");
        for (DroolsFactObjectAttribute foa : deletedObject.getListfactObjectAttributes()) {
            str.append("- " + foa.getAttributeType() + " " + foa.getAttributeName() + "=" + foa.getAttributeValue() + "\n");
        }
        return str.toString();
    }
}
