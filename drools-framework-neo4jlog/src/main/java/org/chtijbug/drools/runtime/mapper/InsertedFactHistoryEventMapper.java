package org.chtijbug.drools.runtime.mapper;

import org.chtijbug.drools.common.date.DateHelper;
import org.chtijbug.drools.entity.history.fact.InsertedFactHistoryEvent;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
/**
 * Created by IntelliJ IDEA.
 * Date: 04/06/13
 * Time: 15:19
 * To change this template use File | Settings | File Templates.
 */
public class InsertedFactHistoryEventMapper {

    public static Node map(InsertedFactHistoryEvent insertedFactHistoryEvent,GraphDatabaseService graphDb){
        Node fact = graphDb.createNode();
        fact.setProperty("date", DateHelper.getDate(insertedFactHistoryEvent.getDateEvent()));
        fact.setProperty("type","history");
        fact.setProperty("EventType",insertedFactHistoryEvent.getTypeEvent().toString());
        fact.setProperty("eventID",insertedFactHistoryEvent.getEventID());
        return fact;
    }
}
