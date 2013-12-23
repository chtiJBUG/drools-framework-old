package org.chtijbug.drools.entity.history.knowledge;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * Date: 05/06/13
 * Time: 12:06
 * To change this template use File | Settings | File Templates.
 */
public class KnowledgeBaseCreatedEvent extends KnowledgeBaseEvent {
    public KnowledgeBaseCreatedEvent(int eventID, Date dateEvent,  int ruleBaseID) {
        super(eventID, dateEvent, ruleBaseID);
    }

    public KnowledgeBaseCreatedEvent() {
    }
}
