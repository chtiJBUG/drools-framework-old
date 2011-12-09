/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chtijbug.drools.entity.history;

import java.util.Date;

/**
 * 
 * @author nheron
 */
public class HistoryEvent {
	public enum TypeEvent {
		Fact, Rule, BPMN
	};

	protected Date dateEvent;
	protected TypeEvent typeEvent;

	public HistoryEvent(Date dateEvent, TypeEvent typeEvent) {
		this.dateEvent = dateEvent;
		this.typeEvent = typeEvent;
	}

	@Override
	public String toString() {
		return "HistoryEvent{" + "dateEvent=" + dateEvent + ", typeEvent=" + typeEvent + '}';
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
