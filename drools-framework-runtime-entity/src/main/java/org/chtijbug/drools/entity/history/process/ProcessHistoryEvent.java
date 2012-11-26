/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chtijbug.drools.entity.history.process;

import org.chtijbug.drools.entity.history.HistoryEvent;

import java.util.Date;

/**
 * 
 * @author nheron
 */
public class ProcessHistoryEvent extends HistoryEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6446477978109706201L;

	public ProcessHistoryEvent() {

	}
    public ProcessHistoryEvent(int eventID) {
    		super(eventID,new Date(), TypeEvent.BPMN);
    	}
    @Override
      public String toString() {
          return super.toString();
      }

}
