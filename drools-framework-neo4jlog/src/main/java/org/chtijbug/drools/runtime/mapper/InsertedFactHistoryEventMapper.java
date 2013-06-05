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
        fact.setProperty(MapperVariables.EVENT_DATE, DateHelper.getDate(insertedFactHistoryEvent.getDateEvent()));
        fact.setProperty(MapperVariables.EVENT_DATE_LONG, insertedFactHistoryEvent.getDateEvent().getTime());
        fact.setProperty(MapperVariables.NODE_TYPE,"history");
        fact.setProperty(MapperVariables.EVENT_TYPE,insertedFactHistoryEvent.getTypeEvent().toString());
        fact.setProperty(MapperVariables.EVENT_ID,insertedFactHistoryEvent.getEventID());
        return fact;
    }
}
