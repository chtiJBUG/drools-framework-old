package org.chtijbug.drools.entity.history.knowledge;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * Date: 25/02/14
 * Time: 16:17
 * To change this template use File | Settings | File Templates.
 */
public class KnowledgeBaseDisposeEvent extends KnowledgeBaseEvent implements Serializable {

    public KnowledgeBaseDisposeEvent() {

    }

    public KnowledgeBaseDisposeEvent(int eventID, Date dateEvent, int ruleBaseID) {
        super(eventID, dateEvent, ruleBaseID);
    }

}
