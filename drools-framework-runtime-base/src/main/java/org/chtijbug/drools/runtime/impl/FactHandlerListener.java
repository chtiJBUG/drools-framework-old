/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chtijbug.drools.runtime.impl;

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

    private RuleBaseStatefullSession ruleBaseSession;
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
        ruleBaseSession.setData(f, newIbject);
    }

    @Override
    public void objectUpdated(ObjectUpdatedEvent event) {
        LOGGER.debug("Fact updated :: ", event.getObject());
        //events.add("Fact updated :: " + event.getObject().toString());
    }

    @Override
    public void objectRetracted(ObjectRetractedEvent event) {
        LOGGER.debug("Fact retracted :: ", event.getOldObject());
        // events.add("Fact retracted :: " + event.getOldObject().toString());
        FactHandle f = event.getFactHandle();
        Object newIbject = event.getOldObject();
        ruleBaseSession.unsetData(f, newIbject);
    }

    public void dispose() {
    }
}
