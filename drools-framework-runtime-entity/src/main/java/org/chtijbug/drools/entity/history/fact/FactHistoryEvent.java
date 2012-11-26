/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chtijbug.drools.entity.history.fact;

import org.chtijbug.drools.entity.history.HistoryEvent;

import java.util.Date;

/**
 * @author nheron
 */
public abstract class FactHistoryEvent extends HistoryEvent {

    /**
     *
     */
    private static final long serialVersionUID = 5320437389177977457L;

    /**
     * Mandatory for GWT Serialization
     */
    public FactHistoryEvent() {

    }

    public FactHistoryEvent(int eventID,Date dateEvent) {

        super(eventID,dateEvent, TypeEvent.Fact);
    }

    /*
      * (non-Javadoc)
      *
      * @see org.chtijbug.drools.entity.history.HistoryEvent#toString()
      */
    @Override
    public String toString() {
        return super.toString();
    }
}
