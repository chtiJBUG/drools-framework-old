package org.chtijbug.drools.runtime.impl;

import org.chtijbug.drools.entity.history.fact.UpdatedFactHistoryEvent;
import org.chtijbug.drools.runtime.mapper.DroolsFactObjectMapper;
import org.chtijbug.drools.runtime.mapper.UpdatedFactHistoryEventMapper;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by IntelliJ IDEA.
 * Date: 04/06/13
 * Time: 15:50
 * To change this template use File | Settings | File Templates.
 */
public class UpdateFactHandler {
    private static Logger logger = LoggerFactory.getLogger(UpdateFactHandler.class);

       public static void execute(UpdatedFactHistoryEvent updatedFactHistoryEvent, GraphDatabaseService graphDb) {
           Transaction tx = graphDb.beginTx();
           try {
               Node fact = UpdatedFactHistoryEventMapper.map(updatedFactHistoryEvent, graphDb);
               Node droolsOldFactObject = DroolsFactObjectMapper.map(updatedFactHistoryEvent.getObjectOldValue(), graphDb);
               fact.createRelationshipTo(droolsOldFactObject, RunTimeRelationTypes.OBJECT_OLD_VALUE);
               Node droolsNewFactObject = DroolsFactObjectMapper.map(updatedFactHistoryEvent.getObjectNewValue(), graphDb);
               fact.createRelationshipTo(droolsNewFactObject, RunTimeRelationTypes.OBJECT_NEW_VALUE);
               tx.success();
           } catch (Exception e) {
               tx.failure();
           } finally {
               tx.finish();
           }
       }
}
