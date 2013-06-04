package org.chtijbug.drools.runtime.impl;

import org.chtijbug.drools.entity.history.fact.InsertedFactHistoryEvent;
import org.chtijbug.drools.runtime.mapper.DroolsFactObjectMapper;
import org.chtijbug.drools.runtime.mapper.InsertedFactHistoryEventMapper;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by IntelliJ IDEA.
 * Date: 04/06/13
 * Time: 14:58
 * To change this template use File | Settings | File Templates.
 */
public class InsertFactHandler {
    private static Logger logger = LoggerFactory.getLogger(InsertFactHandler.class);

    public static void execute(InsertedFactHistoryEvent insertedFactHistoryEvent, GraphDatabaseService graphDb) {
        Transaction tx = graphDb.beginTx();
        try {
            Node fact = InsertedFactHistoryEventMapper.map(insertedFactHistoryEvent, graphDb);
            Node droolsFactObject = DroolsFactObjectMapper.map(insertedFactHistoryEvent.getInsertedObject(), graphDb);
            fact.createRelationshipTo(droolsFactObject, RunTimeRelationTypes.INSERTED_OBJECT);
            tx.success();
        } catch (Exception e) {
            tx.failure();
        } finally {
            tx.finish();
        }
    }
}
