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
public class UpdatedFactHistoryEvent extends FactHistoryEvent{
    private DroolsFactObject objectOldValue;
    private DroolsFactObject objectNewValue;

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
    
}
