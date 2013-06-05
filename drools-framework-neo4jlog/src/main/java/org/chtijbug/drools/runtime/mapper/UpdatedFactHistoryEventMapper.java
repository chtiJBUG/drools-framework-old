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
            fact.setProperty(MapperVariables.EVENT_DATE, DateHelper.getDate(updatedFactHistoryEvent.getDateEvent()));
            fact.setProperty(MapperVariables.EVENT_DATE_LONG, updatedFactHistoryEvent.getDateEvent().getTime());
            fact.setProperty(MapperVariables.NODE_TYPE,"history");
            fact.setProperty(MapperVariables.EVENT_TYPE,updatedFactHistoryEvent.getTypeEvent().toString());
            fact.setProperty(MapperVariables.EVENT_ID,updatedFactHistoryEvent.getEventID());
            return fact;
        }
}
