/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chtijbug.drools.runtime.impl;

import org.chtijbug.drools.common.log.Logger;
import org.chtijbug.drools.common.log.LoggerFactory;
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

/**
 * 
 * @author nheron
 */
public class FactHandlerListener implements WorkingMemoryEventListener {

	private final RuleBaseStatefulSession ruleBaseSession;
	private static Logger logger = LoggerFactory.getLogger(FactHandlerListener.class);

	public FactHandlerListener(RuleBaseStatefulSession ruleBaseSession) {
		this.ruleBaseSession = ruleBaseSession;
	}

	@Override
	public void objectInserted(ObjectInsertedEvent event) {
        logger.entry("objectInserted", event);
        try {
            //____ Updating reference into the facts map from knowledge Session
            FactHandle f = event.getFactHandle();
            Object newObject = event.getObject();
            DroolsFactObject ff = DroolsFactObjectFactory.createFactObject(newObject);
            ruleBaseSession.setData(f, newObject, ff);
            //____ Adding the Insert Event from the History Container
            InsertedFactHistoryEvent insertFactHistoryEvent = new InsertedFactHistoryEvent(ff);
            this.ruleBaseSession.getHistoryContainer().addHistoryElement(insertFactHistoryEvent);
        } finally {
            logger.exit("objectInserted");
        }
    }

	@Override
	public void objectUpdated(ObjectUpdatedEvent event) {
        logger.entry("objectUpdated", event);
        try {
            //____ Updating FactHandle Object reference from the knwoledge session
            FactHandle f = event.getFactHandle();
            Object oldValue = event.getOldObject();
            Object newValue = event.getObject();
            DroolsFactObject factOldValue = this.ruleBaseSession.getLastFactObjectVersion(oldValue);
            DroolsFactObject factNewValue = DroolsFactObjectFactory.createFactObject(newValue, factOldValue.getNextObjectVersion());
            ruleBaseSession.setData(f, newValue, factNewValue);
            //____ Adding the Update Event from the History Container
            UpdatedFactHistoryEvent updatedFactHistoryEvent = new UpdatedFactHistoryEvent(factOldValue, factNewValue);
            this.ruleBaseSession.getHistoryContainer().addHistoryElement(updatedFactHistoryEvent);
        } finally {
            logger.exit("objectUpdated");
        }
    }

	@Override
	public void objectRetracted(ObjectRetractedEvent event) {
        logger.entry("objectRetracted", event);
        try {
            //____ Removing FactHandle from the KnowledgeBase
            FactHandle f = event.getFactHandle();
            Object newObject = event.getOldObject();
            DroolsFactObject deletedFact = this.ruleBaseSession.getLastFactObjectVersion(newObject);
            ruleBaseSession.unsetData(f, newObject);
            //____ Adding a Delete Event from the HistoryContainer
            DeletedFactHistoryEvent deleteFactEvent = new DeletedFactHistoryEvent(deletedFact);
            this.ruleBaseSession.getHistoryContainer().addHistoryElement(deleteFactEvent);

        } finally {
            logger.exit("objectRetracted");
        }
    }

}