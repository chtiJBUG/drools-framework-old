/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chtijbug.drools.entity.history.fact;

import java.util.Date;

import org.chtijbug.drools.entity.DroolsFactObject;
import org.chtijbug.drools.entity.DroolsFactObjectAttribute;

/**
 * 
 * @author nheron
 */
public class InsertedFactHistoryEvent extends FactHistoryEvent {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4530420814276942059L;
	private DroolsFactObject insertedObject;

	public InsertedFactHistoryEvent(DroolsFactObject insertedObject) {
		super(new Date());
		this.insertedObject = insertedObject;
	}

	public DroolsFactObject getInsertedObject() {
		return insertedObject;
	}

	public void setInsertedObject(DroolsFactObject insertedObject) {
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
		for (DroolsFactObjectAttribute foa : insertedObject.getListfactObjectAttributes()) {
			str.append("- " + foa.getAttributeType() + " " + foa.getAttributeName() + "=" + foa.getAttributeValue() + "\n");
		}

		return str.toString();
	}

}
