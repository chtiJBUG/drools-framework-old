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
public class UpdatedFactHistoryEvent extends FactHistoryEvent{
    private FactObject objectOldValue;
    private FactObject objectNewValue;

    public UpdatedFactHistoryEvent(FactObject objectOldValue, FactObject objectNewValue) {
        super(new Date());
        this.objectOldValue = objectOldValue;
        this.objectNewValue = objectNewValue;
      
    }

    public FactObject getObjectNewValue() {
        return objectNewValue;
    }

    public void setObjectNewValue(FactObject objectNewValue) {
        this.objectNewValue = objectNewValue;
    }

    public FactObject getObjectOldValue() {
        return objectOldValue;
    }

    public void setObjectOldValue(FactObject objectOldValue) {
        this.objectOldValue = objectOldValue;
    }
    
}
