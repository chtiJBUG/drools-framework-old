/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chtijbug.drools.entity.history.fact;

import java.util.Date;

/**
 * @author nheron
 */
public class InsertedByReflectionFactEndHistoryEvent extends FactHistoryEvent {


    public InsertedByReflectionFactEndHistoryEvent() {
    }

    public InsertedByReflectionFactEndHistoryEvent(int eventID, int ruleBaseId, int sessionId) {
        super(eventID,new Date(),ruleBaseId,sessionId);
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
        str.append("End InsertBuReflectionEndHistoryEvent\n");



        return str.toString();
    }

}
