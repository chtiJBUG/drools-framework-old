package org.chtijbug.drools.runtime.impl;

import org.chtijbug.drools.entity.history.HistoryEvent;
import org.chtijbug.drools.entity.history.fact.DeletedFactHistoryEvent;
import org.chtijbug.drools.entity.history.fact.InsertedFactHistoryEvent;
import org.chtijbug.drools.entity.history.fact.UpdatedFactHistoryEvent;
import org.chtijbug.drools.entity.history.knowledge.KnowledgeBaseCreateSessionEvent;
import org.chtijbug.drools.entity.history.knowledge.KnowledgeBaseCreatedEvent;
import org.chtijbug.drools.entity.history.knowledge.KnowledgeBaseInitialLoadEvent;
import org.chtijbug.drools.entity.history.knowledge.KnowledgeBaseReloadedEvent;
import org.chtijbug.drools.entity.history.process.NodeInstanceAfterHistoryEvent;
import org.chtijbug.drools.entity.history.process.NodeInstanceBeforeHistoryEvent;
import org.chtijbug.drools.entity.history.process.ProcessEndHistoryEvent;
import org.chtijbug.drools.entity.history.process.ProcessStartHistoryEvent;
import org.chtijbug.drools.entity.history.rule.AfterRuleFiredHistoryEvent;
import org.chtijbug.drools.entity.history.rule.AfterRuleFlowActivatedHistoryEvent;
import org.chtijbug.drools.entity.history.rule.AfterRuleFlowDeactivatedHistoryEvent;
import org.chtijbug.drools.entity.history.rule.BeforeRuleFiredHistoryEvent;
import org.chtijbug.drools.entity.history.session.*;
import org.chtijbug.drools.runtime.DroolsChtijbugException;
import org.chtijbug.drools.runtime.Neo4jPersistenceService;
import org.neo4j.graphdb.GraphDatabaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by IntelliJ IDEA.
 * Date: 04/06/13
 * Time: 10:22
 * To change this template use File | Settings | File Templates.
 */
public class PersistentServiceImpl implements Neo4jPersistenceService {
    private static Logger logger = LoggerFactory.getLogger(PersistentServiceImpl.class);

    private GraphDatabaseService graphDb = null;

    public PersistentServiceImpl(GraphDatabaseService graphDb) {
        this.graphDb = graphDb;
    }

    @Override
    public void save(HistoryEvent historyEvent) throws DroolsChtijbugException {
        if (historyEvent instanceof InsertedFactHistoryEvent) {
            this.save((InsertedFactHistoryEvent) historyEvent);
        } else if (historyEvent instanceof UpdatedFactHistoryEvent) {
            this.save((UpdatedFactHistoryEvent) historyEvent);
        } else if (historyEvent instanceof DeletedFactHistoryEvent) {
            this.save((DeletedFactHistoryEvent) historyEvent);
        } else if (historyEvent instanceof NodeInstanceAfterHistoryEvent) {
            this.save((NodeInstanceAfterHistoryEvent) historyEvent);
        } else if (historyEvent instanceof NodeInstanceBeforeHistoryEvent) {
            this.save((NodeInstanceBeforeHistoryEvent) historyEvent);
        } else if (historyEvent instanceof ProcessEndHistoryEvent) {
            this.save((ProcessEndHistoryEvent) historyEvent);
        } else if (historyEvent instanceof ProcessStartHistoryEvent) {
            this.save((ProcessStartHistoryEvent) historyEvent);
        } else if (historyEvent instanceof AfterRuleFiredHistoryEvent) {
            this.save((AfterRuleFiredHistoryEvent) historyEvent);
        } else if (historyEvent instanceof AfterRuleFlowActivatedHistoryEvent) {
            this.save((AfterRuleFlowActivatedHistoryEvent) historyEvent);
        } else if (historyEvent instanceof AfterRuleFlowDeactivatedHistoryEvent) {
            this.save((AfterRuleFlowDeactivatedHistoryEvent) historyEvent);
        } else if (historyEvent instanceof BeforeRuleFiredHistoryEvent) {
            this.save((BeforeRuleFiredHistoryEvent) historyEvent);
        } else if (historyEvent instanceof KnowledgeBaseCreatedEvent) {
            this.save((KnowledgeBaseCreatedEvent) historyEvent);
        } else if (historyEvent instanceof KnowledgeBaseCreateSessionEvent) {
            this.save((KnowledgeBaseCreateSessionEvent) historyEvent);
        } else if (historyEvent instanceof KnowledgeBaseInitialLoadEvent) {
            this.save((KnowledgeBaseInitialLoadEvent) historyEvent);
        } else if (historyEvent instanceof KnowledgeBaseReloadedEvent) {
            this.save((KnowledgeBaseReloadedEvent) historyEvent);
        } else if (historyEvent instanceof SessionCreatedEvent) {
            this.save((SessionCreatedEvent) historyEvent);
        } else if (historyEvent instanceof SessionDisposedEvent) {
            this.save((SessionDisposedEvent) historyEvent);
        } else if (historyEvent instanceof SessionStartProcessBeginEvent) {
            this.save((SessionStartProcessBeginEvent) historyEvent);
        } else if (historyEvent instanceof SessionStartProcessEndEvent) {
            this.save((SessionStartProcessEndEvent) historyEvent);
        } else if (historyEvent instanceof SessionFireAllRulesBeginEvent) {
            this.save((SessionFireAllRulesBeginEvent) historyEvent);
        } else if (historyEvent instanceof SessionFireAllRulesEndEvent) {
            this.save((SessionFireAllRulesEndEvent) historyEvent);
        } else if (historyEvent instanceof SessionFireAllRulesMaxNumberReachedEvent) {
            this.save((SessionFireAllRulesMaxNumberReachedEvent) historyEvent);
        }

    }

    @Override
    public void save(DeletedFactHistoryEvent deletedFactHistoryEvent) throws DroolsChtijbugException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void save(InsertedFactHistoryEvent insertedFactHistoryEvent) throws DroolsChtijbugException {
        InsertFactHandler.execute(insertedFactHistoryEvent, this.graphDb);
    }

    @Override
    public void save(UpdatedFactHistoryEvent updatedFactHistoryEvent) throws DroolsChtijbugException {
        UpdateFactHandler.execute(updatedFactHistoryEvent, this.graphDb);
    }

    @Override
    public void save(NodeInstanceAfterHistoryEvent nodeInstanceAfterHistoryEvent) throws DroolsChtijbugException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void save(NodeInstanceBeforeHistoryEvent nodeInstanceBeforeHistoryEvent) throws DroolsChtijbugException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void save(ProcessEndHistoryEvent processEndHistoryEvent) throws DroolsChtijbugException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void save(ProcessStartHistoryEvent processStartHistoryEvent) throws DroolsChtijbugException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void save(AfterRuleFiredHistoryEvent afterRuleFiredHistoryEvent) throws DroolsChtijbugException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void save(AfterRuleFlowActivatedHistoryEvent afterRuleFlowActivatedHistoryEvent) throws DroolsChtijbugException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void save(AfterRuleFlowDeactivatedHistoryEvent afterRuleFlowDeactivatedHistoryEvent) throws DroolsChtijbugException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void save(BeforeRuleFiredHistoryEvent beforeRuleFiredHistoryEvent) throws DroolsChtijbugException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void save(KnowledgeBaseCreatedEvent knowledgeBaseCreatedEvent) throws DroolsChtijbugException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void save(KnowledgeBaseCreateSessionEvent knowledgeBaseCreateSessionEvent) throws DroolsChtijbugException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void save(KnowledgeBaseInitialLoadEvent knowledgeBaseInitialLoadEvent) throws DroolsChtijbugException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void save(KnowledgeBaseReloadedEvent knowledgeBaseReloadedEvent) throws DroolsChtijbugException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void save(SessionCreatedEvent sessionCreatedEvent) throws DroolsChtijbugException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void save(SessionDisposedEvent sessionDisposedEvent) throws DroolsChtijbugException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void save(SessionFireAllRulesBeginEvent sessionFireAllRulesBeginEvent) throws DroolsChtijbugException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void save(SessionFireAllRulesEndEvent sessionFireAllRulesEndEvent) throws DroolsChtijbugException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void save(SessionFireAllRulesMaxNumberReachedEvent sessionFireAllRulesMaxNumberReachedEvent) throws DroolsChtijbugException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void save(SessionStartProcessBeginEvent sessionStartProcessBeginEvent) throws DroolsChtijbugException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void save(SessionStartProcessEndEvent sessionStartProcessEndEvent) throws DroolsChtijbugException {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
