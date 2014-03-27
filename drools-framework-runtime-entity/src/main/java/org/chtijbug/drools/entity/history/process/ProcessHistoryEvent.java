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


	public ProcessHistoryEvent() {

	}
    public ProcessHistoryEvent(int eventID,int ruleBaseId,int sessionId) {
    		super(eventID,new Date(), TypeEvent.BPMN);
        this.setRuleBaseID(ruleBaseId);
        this.setSessionId(sessionId);
    	}
    @Override
      public String toString() {
          return super.toString();
      }

}
