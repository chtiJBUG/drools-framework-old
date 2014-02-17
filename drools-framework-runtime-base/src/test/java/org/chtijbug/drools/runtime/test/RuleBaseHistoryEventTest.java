package org.chtijbug.drools.runtime.test;

import org.chtijbug.drools.entity.history.DrlRessourceFile;
import org.chtijbug.drools.entity.history.HistoryEvent;
import org.chtijbug.drools.entity.history.knowledge.KnowledgeBaseAddRessourceEvent;
import org.chtijbug.drools.entity.history.knowledge.KnowledgeBaseCreatedEvent;
import org.chtijbug.drools.entity.history.knowledge.KnowledgeBaseInitialLoadEvent;
import org.chtijbug.drools.entity.history.knowledge.KnowledgeBaseReloadedEvent;
import org.chtijbug.drools.runtime.DroolsChtijbugException;
import org.chtijbug.drools.runtime.RuleBaseBuilder;
import org.chtijbug.drools.runtime.RuleBasePackage;
import org.chtijbug.drools.runtime.listener.HistoryListener;
import org.chtijbug.drools.runtime.resource.DrlDroolsRessource;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Date: 17/02/14
 * Time: 14:02
 * To change this template use File | Settings | File Templates.
 */
public class RuleBaseHistoryEventTest {
    @Test
    public void PackageCreationEvent() throws DroolsChtijbugException {

        final List<HistoryEvent> historyEvents = new ArrayList<HistoryEvent>();
        HistoryListener historyListener = new HistoryListener() {
            @Override
            public void fireEvent(HistoryEvent newHistoryEvent) throws DroolsChtijbugException {
                historyEvents.add(newHistoryEvent);
            }
        };
        RuleBasePackage ruleBasePackage = RuleBaseBuilder.createPackageBasePackageWithListener(historyListener, "fibonacci.drl");
        int rulePackageID = ruleBasePackage.getRuleBaseID();

        Assert.assertTrue(historyEvents.size() == 3);
        Assert.assertTrue(historyEvents.get(0) instanceof KnowledgeBaseCreatedEvent);
        KnowledgeBaseCreatedEvent knowledgeBaseCreatedEvent = (KnowledgeBaseCreatedEvent) historyEvents.get(0);
        Assert.assertEquals(knowledgeBaseCreatedEvent.getRuleBaseID(), rulePackageID);
        Assert.assertEquals(knowledgeBaseCreatedEvent.getEventID(), 1l);
        Assert.assertEquals(knowledgeBaseCreatedEvent.getSessionId(), 0l);
        Assert.assertEquals(knowledgeBaseCreatedEvent.getTypeEvent(), HistoryEvent.TypeEvent.KnowledgeBaseSingleton);
        Assert.assertTrue(historyEvents.get(1) instanceof KnowledgeBaseAddRessourceEvent);
        KnowledgeBaseAddRessourceEvent knowledgeBaseAddRessourceEvent = (KnowledgeBaseAddRessourceEvent) historyEvents.get(1);
        Assert.assertEquals(knowledgeBaseAddRessourceEvent.getEventID(), 2l);
        Assert.assertEquals(knowledgeBaseAddRessourceEvent.getTypeEvent(), HistoryEvent.TypeEvent.KnowledgeBaseSingleton);
        Assert.assertTrue(knowledgeBaseAddRessourceEvent.getDrlRessourceFiles().size() == 1);
        DrlRessourceFile drlRessourceFile = knowledgeBaseAddRessourceEvent.getDrlRessourceFiles().get(0);
        Assert.assertTrue(drlRessourceFile.getFileName().equals("fibonacci.drl"));
        Assert.assertTrue(historyEvents.get(2) instanceof KnowledgeBaseInitialLoadEvent);
        KnowledgeBaseInitialLoadEvent knowledgeBaseInitialLoadEvent = (KnowledgeBaseInitialLoadEvent) historyEvents.get(2);
        Assert.assertEquals(knowledgeBaseInitialLoadEvent.getEventID(), 3l);
        Assert.assertEquals(knowledgeBaseInitialLoadEvent.getTypeEvent(), HistoryEvent.TypeEvent.KnowledgeBaseSingleton);
        ruleBasePackage.RecreateKBaseWithNewRessources(DrlDroolsRessource.createClassPathResource("fibonacciBis.drl"));
        Assert.assertTrue(historyEvents.size() == 5);
        Assert.assertTrue(historyEvents.get(3) instanceof KnowledgeBaseAddRessourceEvent);
        KnowledgeBaseAddRessourceEvent knowledgeBaseAddRessourceEvent2 = (KnowledgeBaseAddRessourceEvent) historyEvents.get(3);
        Assert.assertEquals(knowledgeBaseAddRessourceEvent2.getEventID(), 4l);
        Assert.assertEquals(knowledgeBaseAddRessourceEvent2.getTypeEvent(), HistoryEvent.TypeEvent.KnowledgeBaseSingleton);
        Assert.assertTrue(knowledgeBaseAddRessourceEvent2.getDrlRessourceFiles().size() == 1);
        DrlRessourceFile drlRessourceFile2 = knowledgeBaseAddRessourceEvent2.getDrlRessourceFiles().get(0);
        Assert.assertTrue(drlRessourceFile2.getFileName().equals("fibonacciBis.drl"));


        Assert.assertTrue(historyEvents.get(4) instanceof KnowledgeBaseReloadedEvent);
        KnowledgeBaseReloadedEvent knowledgeBaseReloadedEvent = (KnowledgeBaseReloadedEvent) historyEvents.get(4);
        Assert.assertEquals(knowledgeBaseReloadedEvent.getRuleBaseID(), rulePackageID);
        Assert.assertEquals(knowledgeBaseReloadedEvent.getEventID(), 5l);
        Assert.assertEquals(knowledgeBaseReloadedEvent.getSessionId(), 0l);
        Assert.assertEquals(knowledgeBaseReloadedEvent.getTypeEvent(), HistoryEvent.TypeEvent.KnowledgeBaseSingleton);


    }
}
