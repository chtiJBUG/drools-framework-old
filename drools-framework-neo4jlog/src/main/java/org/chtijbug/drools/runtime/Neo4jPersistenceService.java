package org.chtijbug.drools.runtime;

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

/**
 * Created by IntelliJ IDEA.
 * Date: 04/06/13
 * Time: 10:23
 * To change this template use File | Settings | File Templates.
 */
public interface Neo4jPersistenceService {

    public void save(HistoryEvent historyEvent) throws DroolsChtijbugException;

    public void save(DeletedFactHistoryEvent deletedFactHistoryEvent) throws DroolsChtijbugException;

    public void save (InsertedFactHistoryEvent insertedFactHistoryEvent)throws DroolsChtijbugException;

    public void save (UpdatedFactHistoryEvent updatedFactHistoryEvent)throws DroolsChtijbugException;

    public void save (NodeInstanceAfterHistoryEvent nodeInstanceAfterHistoryEvent) throws DroolsChtijbugException;

    public void save (NodeInstanceBeforeHistoryEvent nodeInstanceBeforeHistoryEvent ) throws  DroolsChtijbugException;

    public void save (ProcessEndHistoryEvent processEndHistoryEvent)throws  DroolsChtijbugException;

    public void save (ProcessStartHistoryEvent processStartHistoryEvent ) throws  DroolsChtijbugException;

    public void save (AfterRuleFiredHistoryEvent afterRuleFiredHistoryEvent) throws  DroolsChtijbugException;

    public void save (AfterRuleFlowActivatedHistoryEvent afterRuleFlowActivatedHistoryEvent) throws DroolsChtijbugException;

    public void save (AfterRuleFlowDeactivatedHistoryEvent afterRuleFlowDeactivatedHistoryEvent) throws DroolsChtijbugException;

    public void save (BeforeRuleFiredHistoryEvent beforeRuleFiredHistoryEvent) throws DroolsChtijbugException;

    public void save (KnowledgeBaseCreatedEvent knowledgeBaseCreatedEvent) throws DroolsChtijbugException;

    public void save (KnowledgeBaseCreateSessionEvent knowledgeBaseCreateSessionEvent) throws DroolsChtijbugException;

    public void save (KnowledgeBaseInitialLoadEvent knowledgeBaseInitialLoadEvent) throws DroolsChtijbugException;

    public void save (KnowledgeBaseReloadedEvent knowledgeBaseReloadedEvent) throws DroolsChtijbugException;

    public void save (SessionCreatedEvent sessionCreatedEvent) throws DroolsChtijbugException;

    public void save (SessionDisposedEvent sessionDisposedEvent) throws DroolsChtijbugException;

    public void save (SessionFireAllRulesBeginEvent sessionFireAllRulesBeginEvent) throws DroolsChtijbugException;

    public void save (SessionFireAllRulesEndEvent sessionFireAllRulesEndEvent) throws DroolsChtijbugException;

    public void save (SessionFireAllRulesMaxNumberReachedEvent sessionFireAllRulesMaxNumberReachedEvent) throws DroolsChtijbugException;

    public void save (SessionStartProcessBeginEvent sessionStartProcessBeginEvent) throws DroolsChtijbugException;

    public void save (SessionStartProcessEndEvent sessionStartProcessEndEvent) throws DroolsChtijbugException;

}
