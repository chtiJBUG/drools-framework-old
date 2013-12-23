/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chtijbug.drools.entity.history;

import org.chtijbug.drools.runtime.DroolsChtijbugException;

import java.io.Serializable;
import java.util.Date;

/**
 * @author nheron
 */
public class HistoryEvent implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -6640538290066213804L;
    private int eventID;
    private int ruleBaseID;
    private int sessionId;
    private DroolsChtijbugException droolsChtijbugException;

    public enum TypeEvent {
        Fact, Rule, BPMN, RuleFlowGroup, KnowledgeBaseSingleton, Session
    }

	/**
	 * Mandatory for GWT Serialization
	 */
	public HistoryEvent(){
	}

    protected Date dateEvent;
    protected TypeEvent typeEvent;

    public HistoryEvent(int eventID, Date dateEvent, TypeEvent typeEvent) {

            this.eventID = eventID;
            this.dateEvent = dateEvent;
            this.typeEvent = typeEvent;
        }

    public DroolsChtijbugException getDroolsChtijbugException() {
        return droolsChtijbugException;
    }

    public void setDroolsChtijbugException(DroolsChtijbugException droolsChtijbugException) {
        this.droolsChtijbugException = droolsChtijbugException;
    }

    public Date getDateEvent() {
        return dateEvent;
    }

    public TypeEvent getTypeEvent() {
        return typeEvent;
    }

    public int getEventID() {
        return eventID;
    }

    public int getRuleBaseID() {
        return ruleBaseID;
    }

    public void setRuleBaseID(int ruleBaseID) {
        this.ruleBaseID = ruleBaseID;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("HistoryEvent{");
        sb.append("eventID=").append(eventID);
        sb.append(", droolsChtijbugException=").append(droolsChtijbugException);
        sb.append(", dateEvent=").append(dateEvent);
        sb.append(", typeEvent=").append(typeEvent);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final HistoryEvent other = (HistoryEvent) obj;
        if (this.dateEvent != other.dateEvent && (this.dateEvent == null || !this.dateEvent.equals(other.dateEvent))) {
            return false;
        }
        if (this.typeEvent != other.typeEvent) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + (this.dateEvent != null ? this.dateEvent.hashCode() : 0);
        hash = 89 * hash + (this.typeEvent != null ? this.typeEvent.hashCode() : 0);
        return hash;
    }

}
