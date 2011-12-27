/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chtijbug.drools.entity.history.fact;

import java.util.Date;

import org.chtijbug.drools.entity.history.HistoryEvent;

/**
 * 
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

	public FactHistoryEvent(Date dateEvent) {

		super(dateEvent, TypeEvent.Fact);
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
