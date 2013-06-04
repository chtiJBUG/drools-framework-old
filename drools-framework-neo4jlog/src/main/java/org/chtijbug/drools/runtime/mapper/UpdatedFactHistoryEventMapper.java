package org.chtijbug.drools.runtime.mapper;

import org.chtijbug.drools.common.date.DateHelper;
import org.chtijbug.drools.entity.history.fact.UpdatedFactHistoryEvent;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;

/**
 * Created by IntelliJ IDEA.
 * Date: 04/06/13
 * Time: 15:53
 * To change this template use File | Settings | File Templates.
 */
public class UpdatedFactHistoryEventMapper {
    public static Node map(UpdatedFactHistoryEvent updatedFactHistoryEvent,GraphDatabaseService graphDb){
            Node fact = graphDb.createNode();
            fact.setProperty("date", DateHelper.getDate(updatedFactHistoryEvent.getDateEvent()));
            fact.setProperty("type","history");
            fact.setProperty("EventType",updatedFactHistoryEvent.getTypeEvent().toString());
            fact.setProperty("eventID",updatedFactHistoryEvent.getEventID());
            return fact;
        }
}
