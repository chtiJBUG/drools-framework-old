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
public class InsertedFactHistoryEvent extends FactHistoryEvent{
    private FactObject insertedObject;

    public InsertedFactHistoryEvent(FactObject insertedObject) {
        super(new Date());
        this.insertedObject = insertedObject;
    }

    public FactObject getInsertedObject() {
        return insertedObject;
    }

    public void setInsertedObject(FactObject insertedObject) {
        this.insertedObject = insertedObject;
    }
    
}
