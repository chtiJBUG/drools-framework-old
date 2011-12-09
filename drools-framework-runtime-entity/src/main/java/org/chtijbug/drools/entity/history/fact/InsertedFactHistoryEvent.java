/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chtijbug.drools.entity.history.fact;

import java.util.Date;

import org.chtijbug.drools.entity.history.FactObject;
import org.chtijbug.drools.entity.history.FactObjectAttribute;

/**
 * 
 * @author nheron
 */
public class InsertedFactHistoryEvent extends FactHistoryEvent {
	private FactObject insertedObject;

	public InsertedFactHistoryEvent(FactObject insertedObject) {
		super(new Date());
		this.insertedObject = insertedObject;
	}

	public FactObject getInsertedObject() {
		return insertedObject;
	}

	public void setInsertedObject(FactObject insertedObject) {
		this.insertedObject = insertedObject;
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

		str.append("inserted Object : " + insertedObject.getFullClassName() + "\n");
		str.append("version Object : " + insertedObject.getObjectVersion() + "\n");
		str.append("attributes :\n");
		for (FactObjectAttribute foa : insertedObject.getListfactObjectAttributes()) {
			str.append("- " + foa.getAttributeType() + " " + foa.getAttributeName() + "=" + foa.getAttributeValue() + "\n");
		}

		return str.toString();
	}

}
