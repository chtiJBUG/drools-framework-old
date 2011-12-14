/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chtijbug.drools.entity.history.fact;

import java.util.Date;

import org.chtijbug.drools.entity.DroolsFactObject;

/**
 *
 * @author nheron
 */
public class DeletedFactHistoryEvent extends FactHistoryEvent{

    private DroolsFactObject deletedObject;

    public DeletedFactHistoryEvent(DroolsFactObject deletedObject) {
        super(new Date());
        this.deletedObject = deletedObject;
    }

    public DroolsFactObject getDeletedObject() {
        return deletedObject;
    }

    public void setDeletedObject(DroolsFactObject deletedObject) {
        this.deletedObject = deletedObject;
    }
}
