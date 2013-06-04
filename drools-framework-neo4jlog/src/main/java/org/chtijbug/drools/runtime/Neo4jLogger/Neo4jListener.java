package org.chtijbug.drools.runtime.Neo4jLogger;

import org.chtijbug.drools.entity.history.HistoryEvent;
import org.chtijbug.drools.runtime.DroolsChtijbugException;
import org.chtijbug.drools.runtime.Neo4jPersistenceService;
import org.chtijbug.drools.runtime.impl.PersistentServiceImpl;
import org.chtijbug.drools.runtime.listener.HistoryListener;
import org.neo4j.graphdb.GraphDatabaseService;

/**
 * Created by IntelliJ IDEA.
 * Date: 03/06/13
 * Time: 16:05
 * To change this template use File | Settings | File Templates.
 */
public class Neo4jListener implements HistoryListener{
    private Neo4jPersistenceService neo4jPersistenceService=  null;

    public Neo4jListener(GraphDatabaseService graphDb) {
        this.neo4jPersistenceService    = new PersistentServiceImpl(graphDb);

    }

    public Neo4jPersistenceService getNeo4jPersistenceService() {
        return this.neo4jPersistenceService;
    }

    @Override
    public void fireEvent(HistoryEvent newHistoryEvent) throws DroolsChtijbugException{
        neo4jPersistenceService.save(newHistoryEvent);
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
