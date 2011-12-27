/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chtijbug.drools.runtime.impl;

import org.chtijbug.drools.entity.DroolsFactObject;
import org.chtijbug.drools.entity.history.fact.DeletedFactHistoryEvent;
import org.chtijbug.drools.entity.history.fact.InsertedFactHistoryEvent;
import org.chtijbug.drools.entity.history.fact.UpdatedFactHistoryEvent;
import org.chtijbug.drools.runtime.DroolsFactObjectFactory;
import org.drools.event.rule.ObjectInsertedEvent;
import org.drools.event.rule.ObjectRetractedEvent;
import org.drools.event.rule.ObjectUpdatedEvent;
import org.drools.event.rule.WorkingMemoryEventListener;
import org.drools.runtime.rule.FactHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author nheron
 */
public class FactHandlerListener implements WorkingMemoryEventListener {

	private final RuleBaseStatefullSession ruleBaseSession;
	static final Logger LOGGER = LoggerFactory.getLogger(FactHandlerListener.class);

	public FactHandlerListener(RuleBaseStatefullSession ruleBaseSession) {
		this.ruleBaseSession = ruleBaseSession;
	}

	@Override
	public void objectInserted(ObjectInsertedEvent event) {

		LOGGER.debug("Fact inserted :: ", event.getObject());
		// events.add("Fact inserted :: " + event.getObject().toString());
		FactHandle f = event.getFactHandle();
		Object newIbject = event.getObject();
		DroolsFactObject ff = DroolsFactObjectFactory.createFactObject(newIbject);
		ruleBaseSession.setData(f, newIbject, ff);
		InsertedFactHistoryEvent insertFactHistoryEvent = new InsertedFactHistoryEvent(ff);
		this.ruleBaseSession.getHistoryContainer().addHistoryElement(insertFactHistoryEvent);
	}

	@Override
	public void objectUpdated(ObjectUpdatedEvent event) {
		LOGGER.debug("Fact updated :: ", event.getObject());
		FactHandle f = event.getFactHandle();
		Object oldValue = event.getOldObject();
		Object newValue = event.getObject();
		DroolsFactObject factOldValue = this.ruleBaseSession.getLastFactObjectVersion(oldValue);
		DroolsFactObject factnewValue = DroolsFactObjectFactory.createFactObject(newValue, factOldValue.getNextObjectVersion());
		ruleBaseSession.setData(f, newValue, factnewValue);
		UpdatedFactHistoryEvent updatedFactHistoryEvent = new UpdatedFactHistoryEvent(factOldValue, factnewValue);
		this.ruleBaseSession.getHistoryContainer().addHistoryElement(updatedFactHistoryEvent);
	}

	@Override
	public void objectRetracted(ObjectRetractedEvent event) {
		LOGGER.debug("Fact retracted :: ", event.getOldObject());
		// events.add("Fact retracted :: " + event.getOldObject().toString());
		FactHandle f = event.getFactHandle();
		Object newIbject = event.getOldObject();
		DroolsFactObject deletedFact = this.ruleBaseSession.getLastFactObjectVersion(newIbject);
		DeletedFactHistoryEvent deleteFactEvent = new DeletedFactHistoryEvent(deletedFact);
		this.ruleBaseSession.getHistoryContainer().addHistoryElement(deleteFactEvent);
		ruleBaseSession.unsetData(f, newIbject);
	}

	public void dispose() {
	}
}
