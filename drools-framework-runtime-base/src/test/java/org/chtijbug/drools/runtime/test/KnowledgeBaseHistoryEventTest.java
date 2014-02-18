package org.chtijbug.drools.runtime.test;

import org.chtijbug.drools.entity.history.HistoryEvent;
import org.chtijbug.drools.entity.history.knowledge.KnowledgeBaseCreateSessionEvent;
import org.chtijbug.drools.entity.history.session.SessionCreatedEvent;
import org.chtijbug.drools.entity.history.session.SessionDisposedEvent;
import org.chtijbug.drools.entity.history.session.SessionFireAllRulesBeginEvent;
import org.chtijbug.drools.entity.history.session.SessionFireAllRulesEndEvent;
import org.chtijbug.drools.runtime.DroolsChtijbugException;
import org.chtijbug.drools.runtime.RuleBaseBuilder;
import org.chtijbug.drools.runtime.RuleBasePackage;
import org.chtijbug.drools.runtime.RuleBaseSession;
import org.chtijbug.drools.runtime.listener.HistoryListener;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Date: 17/02/14
 * Time: 15:09
 * To change this template use File | Settings | File Templates.
 */
public class KnowledgeBaseHistoryEventTest {
    @Test
    public void KnowledgeSessionEventOneSession() throws DroolsChtijbugException {

        final List<HistoryEvent> historyEvents = new ArrayList<HistoryEvent>();
        HistoryListener historyListener = new HistoryListener() {
            @Override
            public void fireEvent(HistoryEvent newHistoryEvent) throws DroolsChtijbugException {
                historyEvents.add(newHistoryEvent);
            }
        };
        RuleBasePackage ruleBasePackage = RuleBaseBuilder.createPackageBasePackageWithListener(historyListener, "fibonacci.drl");
        int rulePackageID = ruleBasePackage.getRuleBaseID();

        RuleBaseSession ruleBaseSession = ruleBasePackage.createRuleBaseSession();
        Assert.assertTrue(historyEvents.size() == 5);
        Assert.assertTrue(historyEvents.get(3) instanceof KnowledgeBaseCreateSessionEvent);
        KnowledgeBaseCreateSessionEvent knowledgeBaseCreateSessionEvent = (KnowledgeBaseCreateSessionEvent) historyEvents.get(3);
        Assert.assertEquals(knowledgeBaseCreateSessionEvent.getRuleBaseID(), rulePackageID);
        Assert.assertEquals(knowledgeBaseCreateSessionEvent.getEventID(), 4l);
        Assert.assertEquals(knowledgeBaseCreateSessionEvent.getSessionId(), 1l);
        Assert.assertEquals(knowledgeBaseCreateSessionEvent.getTypeEvent(), HistoryEvent.TypeEvent.KnowledgeBaseSingleton);
        Assert.assertTrue(historyEvents.get(4) instanceof SessionCreatedEvent);
        SessionCreatedEvent sessionCreatedEvent = (SessionCreatedEvent) historyEvents.get(4);
        Assert.assertEquals(sessionCreatedEvent.getRuleBaseID(), rulePackageID);
        Assert.assertEquals(sessionCreatedEvent.getEventID(), 1l);
        Assert.assertEquals(sessionCreatedEvent.getSessionId(), 1l);
        Assert.assertEquals(sessionCreatedEvent.getTypeEvent(), HistoryEvent.TypeEvent.Session);
        ruleBaseSession.dispose();
        Assert.assertTrue(historyEvents.size() == 6);
        Assert.assertTrue(historyEvents.get(5) instanceof SessionDisposedEvent);
        SessionDisposedEvent sessionDisposedEvent = (SessionDisposedEvent) historyEvents.get(5);
        Assert.assertEquals(sessionDisposedEvent.getRuleBaseID(), rulePackageID);
        Assert.assertEquals(sessionDisposedEvent.getEventID(), 2l);
        Assert.assertEquals(sessionDisposedEvent.getSessionId(), 1l);
        Assert.assertEquals(sessionCreatedEvent.getTypeEvent(), HistoryEvent.TypeEvent.Session);
    }

    @Test
    public void KnowledgeSessionEventTwoSession() throws DroolsChtijbugException {

        final List<HistoryEvent> historyEvents = new ArrayList<HistoryEvent>();
        HistoryListener historyListener = new HistoryListener() {
            @Override
            public void fireEvent(HistoryEvent newHistoryEvent) throws DroolsChtijbugException {
                historyEvents.add(newHistoryEvent);
            }
        };
        RuleBasePackage ruleBasePackage = RuleBaseBuilder.createPackageBasePackageWithListener(historyListener, "fibonacci.drl");
        int rulePackageID = ruleBasePackage.getRuleBaseID();

        RuleBaseSession ruleBaseSession1 = ruleBasePackage.createRuleBaseSession();
        Assert.assertTrue(historyEvents.size() == 5);
        Assert.assertTrue(historyEvents.get(3) instanceof KnowledgeBaseCreateSessionEvent);
        KnowledgeBaseCreateSessionEvent knowledgeBaseCreateSessionEvent = (KnowledgeBaseCreateSessionEvent) historyEvents.get(3);
        Assert.assertEquals(knowledgeBaseCreateSessionEvent.getRuleBaseID(), rulePackageID);
        Assert.assertEquals(knowledgeBaseCreateSessionEvent.getEventID(), 4l);
        Assert.assertEquals(knowledgeBaseCreateSessionEvent.getSessionId(), 1l);
        Assert.assertEquals(knowledgeBaseCreateSessionEvent.getTypeEvent(), HistoryEvent.TypeEvent.KnowledgeBaseSingleton);
        Assert.assertTrue(historyEvents.get(4) instanceof SessionCreatedEvent);
        SessionCreatedEvent sessionCreatedEvent = (SessionCreatedEvent) historyEvents.get(4);
        Assert.assertEquals(sessionCreatedEvent.getRuleBaseID(), rulePackageID);
        Assert.assertEquals(sessionCreatedEvent.getEventID(), 1l);
        Assert.assertEquals(sessionCreatedEvent.getSessionId(), 1l);
        Assert.assertEquals(sessionCreatedEvent.getTypeEvent(), HistoryEvent.TypeEvent.Session);
        RuleBaseSession ruleBaseSession2 = ruleBasePackage.createRuleBaseSession();
        Assert.assertTrue(historyEvents.size() == 7);
        Assert.assertTrue(historyEvents.get(5) instanceof KnowledgeBaseCreateSessionEvent);
        KnowledgeBaseCreateSessionEvent knowledgeBaseCreateSessionEvent2 = (KnowledgeBaseCreateSessionEvent) historyEvents.get(5);
        Assert.assertEquals(knowledgeBaseCreateSessionEvent2.getRuleBaseID(), rulePackageID);
        Assert.assertEquals(knowledgeBaseCreateSessionEvent2.getEventID(), 5l);
        Assert.assertEquals(knowledgeBaseCreateSessionEvent2.getSessionId(), 2l);
        Assert.assertEquals(knowledgeBaseCreateSessionEvent2.getTypeEvent(), HistoryEvent.TypeEvent.KnowledgeBaseSingleton);
        Assert.assertTrue(historyEvents.get(6) instanceof SessionCreatedEvent);
        SessionCreatedEvent sessionCreatedEvent2 = (SessionCreatedEvent) historyEvents.get(6);
        Assert.assertEquals(sessionCreatedEvent2.getRuleBaseID(), rulePackageID);
        Assert.assertEquals(sessionCreatedEvent2.getEventID(), 1l);
        Assert.assertEquals(sessionCreatedEvent2.getSessionId(), 2l);
        Assert.assertEquals(sessionCreatedEvent2.getTypeEvent(), HistoryEvent.TypeEvent.Session);


        ruleBaseSession1.dispose();
        Assert.assertTrue(historyEvents.size() == 8);
        Assert.assertTrue(historyEvents.get(7) instanceof SessionDisposedEvent);
        SessionDisposedEvent sessionDisposedEvent = (SessionDisposedEvent) historyEvents.get(7);
        Assert.assertEquals(sessionDisposedEvent.getRuleBaseID(), rulePackageID);
        Assert.assertEquals(sessionDisposedEvent.getEventID(), 2l);
        Assert.assertEquals(sessionDisposedEvent.getSessionId(), 1l);
        Assert.assertEquals(sessionCreatedEvent.getTypeEvent(), HistoryEvent.TypeEvent.Session);
        ruleBaseSession2.dispose();
        Assert.assertTrue(historyEvents.size() == 9);
        Assert.assertTrue(historyEvents.get(8) instanceof SessionDisposedEvent);
        SessionDisposedEvent sessionDisposedEvent2 = (SessionDisposedEvent) historyEvents.get(8);
        Assert.assertEquals(sessionDisposedEvent2.getRuleBaseID(), rulePackageID);
        Assert.assertEquals(sessionDisposedEvent2.getEventID(), 2l);
        Assert.assertEquals(sessionDisposedEvent2.getSessionId(), 2l);
        Assert.assertEquals(sessionDisposedEvent2.getTypeEvent(), HistoryEvent.TypeEvent.Session);
    }

    @Test
    public void KnowledgeBaseFireAllRules() throws DroolsChtijbugException {

        final List<HistoryEvent> historyEvents = new ArrayList<HistoryEvent>();
        HistoryListener historyListener = new HistoryListener() {
            @Override
            public void fireEvent(HistoryEvent newHistoryEvent) throws DroolsChtijbugException {
                historyEvents.add(newHistoryEvent);
            }
        };
        RuleBasePackage ruleBasePackage = RuleBaseBuilder.createPackageBasePackageWithListener(historyListener, "fibonacci.drl");
        int rulePackageID = ruleBasePackage.getRuleBaseID();

        RuleBaseSession ruleBaseSession1 = ruleBasePackage.createRuleBaseSession();
        Fibonacci fibonacci = new Fibonacci(1);
        ruleBaseSession1.insertObject(fibonacci);
        ruleBaseSession1.fireAllRules();
        Assert.assertTrue(historyEvents.size() == 11);
        Assert.assertTrue(historyEvents.get(6) instanceof SessionFireAllRulesBeginEvent);
        SessionFireAllRulesBeginEvent sessionFireAllRulesBeginEvent = (SessionFireAllRulesBeginEvent) historyEvents.get(6);
        Assert.assertEquals(sessionFireAllRulesBeginEvent.getRuleBaseID(), rulePackageID);
        Assert.assertEquals(sessionFireAllRulesBeginEvent.getEventID(), 3l);
        Assert.assertEquals(sessionFireAllRulesBeginEvent.getSessionId(), 1l);
        Assert.assertEquals(sessionFireAllRulesBeginEvent.getTypeEvent(), HistoryEvent.TypeEvent.Session);
        Assert.assertTrue(historyEvents.get(10) instanceof SessionFireAllRulesEndEvent);
        SessionFireAllRulesEndEvent sessionFireAllRulesEndEvent = (SessionFireAllRulesEndEvent) historyEvents.get(10);
        Assert.assertEquals(sessionFireAllRulesEndEvent.getRuleBaseID(), rulePackageID);
        Assert.assertEquals(sessionFireAllRulesEndEvent.getEventID(), 7l);
        Assert.assertEquals(sessionFireAllRulesEndEvent.getSessionId(), 1l);
        Assert.assertEquals(sessionFireAllRulesEndEvent.getNumberRulesExecuted(), 1l);
        Assert.assertTrue(sessionFireAllRulesEndEvent.getExecutionTime()> 0l);
        Assert.assertEquals(sessionFireAllRulesEndEvent.getTypeEvent(), HistoryEvent.TypeEvent.Session);
    }
}
