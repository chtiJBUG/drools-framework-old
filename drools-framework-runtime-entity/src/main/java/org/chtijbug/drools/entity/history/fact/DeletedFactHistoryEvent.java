/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chtijbug.drools.entity.history.fact;

import java.util.Date;
import org.chtijbug.drools.entity.history.FactObject;

/**
 *
 * @author nheron
 */
public class DeletedFactHistoryEvent extends FactHistoryEvent{

    private FactObject deletedObject;

    public DeletedFactHistoryEvent(FactObject deletedObject) {
        super(new Date());
        this.deletedObject = deletedObject;
    }

    public FactObject getDeletedObject() {
        return deletedObject;
    }

    public void setDeletedObject(FactObject deletedObject) {
        this.deletedObject = deletedObject;
    }
}
