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
public class DeletedFactHistoryEvent extends FactHistoryEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1924924006386653359L;
	protected DroolsFactObject deletedObject;

	/**
	 * 
	 */
	public DeletedFactHistoryEvent() {
	}

	public DeletedFactHistoryEvent(DroolsFactObject deletedObject) {
		super(new Date());
		this.deletedObject = deletedObject;
	}

	public DroolsFactObject getDeletedObject() {
		return deletedObject;
	}

	public void setDeletedObject(DroolsFactObject deletedObject) {
		this.deletedObject = deletedObject;
	}
}
