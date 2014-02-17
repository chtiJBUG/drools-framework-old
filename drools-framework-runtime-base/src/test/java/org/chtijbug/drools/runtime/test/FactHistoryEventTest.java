package org.chtijbug.drools.runtime.test;

import org.chtijbug.drools.entity.DroolsFactObject;
import org.chtijbug.drools.entity.DroolsFactObjectAttribute;
import org.chtijbug.drools.entity.history.HistoryEvent;
import org.chtijbug.drools.entity.history.fact.*;
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
 * Time: 15:37
 * To change this template use File | Settings | File Templates.
 */
public class FactHistoryEventTest {
    @Test
    public void KnowledgeSessionFact() throws DroolsChtijbugException {

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
        Fibonacci fibonacci = new Fibonacci(1);
        ruleBaseSession.insertObject(fibonacci);
        Assert.assertTrue(historyEvents.size() == 6);
        Assert.assertTrue(historyEvents.get(5) instanceof InsertedFactHistoryEvent);
        InsertedFactHistoryEvent insertedFactHistoryEvent = (InsertedFactHistoryEvent) historyEvents.get(5);
        Assert.assertEquals(insertedFactHistoryEvent.getRuleBaseID(), rulePackageID);
        Assert.assertEquals(insertedFactHistoryEvent.getEventID(), 2l);
        Assert.assertEquals(insertedFactHistoryEvent.getSessionId(), 1l);
        Assert.assertEquals(insertedFactHistoryEvent.getTypeEvent(), HistoryEvent.TypeEvent.Fact);
        DroolsFactObject droolsFactObject = insertedFactHistoryEvent.getInsertedObject();
        Assert.assertEquals(droolsFactObject.getFullClassName(), "org.chtijbug.drools.runtime.test.Fibonacci");
        Assert.assertEquals(droolsFactObject.getObjectVersion(), 0l);
        Assert.assertTrue(droolsFactObject.getRealObject() instanceof Fibonacci);
        Assert.assertEquals(droolsFactObject.getListfactObjectAttributes().size(), 2);
        DroolsFactObjectAttribute droolsFactObjectAttribute1 = droolsFactObject.getListfactObjectAttributes().get(0);
        Assert.assertEquals(droolsFactObjectAttribute1.getAttributeName(), "sequence");
        Assert.assertEquals(droolsFactObjectAttribute1.getAttributeValue(), "1");
        Assert.assertEquals(droolsFactObjectAttribute1.getAttributeType(), "Integer");
        DroolsFactObjectAttribute droolsFactObjectAttribute2 = droolsFactObject.getListfactObjectAttributes().get(1);
        Assert.assertEquals(droolsFactObjectAttribute2.getAttributeName(), "value");
        Assert.assertEquals(droolsFactObjectAttribute2.getAttributeValue(), "-1");
        Assert.assertEquals(droolsFactObjectAttribute2.getAttributeType(), "Long");
        fibonacci.setValue(-2);
        ruleBaseSession.updateObject(fibonacci);
        Assert.assertTrue(historyEvents.size() == 7);
        Assert.assertTrue(historyEvents.get(6) instanceof UpdatedFactHistoryEvent);
        UpdatedFactHistoryEvent updatedFactHistoryEvent = (UpdatedFactHistoryEvent) historyEvents.get(6);
        Assert.assertEquals(updatedFactHistoryEvent.getRuleBaseID(), rulePackageID);
        Assert.assertEquals(updatedFactHistoryEvent.getEventID(), 3l);
        Assert.assertEquals(updatedFactHistoryEvent.getSessionId(), 1l);
        Assert.assertEquals(updatedFactHistoryEvent.getTypeEvent(), HistoryEvent.TypeEvent.Fact);
        DroolsFactObject oldDroolsFactObject = updatedFactHistoryEvent.getObjectOldValue();
        Assert.assertEquals(oldDroolsFactObject.getFullClassName(), "org.chtijbug.drools.runtime.test.Fibonacci");
        Assert.assertEquals(oldDroolsFactObject.getObjectVersion(), 0l);
        Assert.assertTrue(oldDroolsFactObject.getRealObject() instanceof Fibonacci);
        Assert.assertEquals(oldDroolsFactObject.getListfactObjectAttributes().size(), 2);
        DroolsFactObjectAttribute oldDroolsFactObjectAttribute1 = oldDroolsFactObject.getListfactObjectAttributes().get(0);
        Assert.assertEquals(oldDroolsFactObjectAttribute1.getAttributeName(), "sequence");
        Assert.assertEquals(oldDroolsFactObjectAttribute1.getAttributeValue(), "1");
        Assert.assertEquals(oldDroolsFactObjectAttribute1.getAttributeType(), "Integer");
        DroolsFactObjectAttribute oldDroolsFactObjectAttribute2 = oldDroolsFactObject.getListfactObjectAttributes().get(1);
        Assert.assertEquals(oldDroolsFactObjectAttribute2.getAttributeName(), "value");
        Assert.assertEquals(oldDroolsFactObjectAttribute2.getAttributeValue(), "-1");
        Assert.assertEquals(oldDroolsFactObjectAttribute2.getAttributeType(), "Long");
        DroolsFactObject newDroolsFactObject = updatedFactHistoryEvent.getObjectNewValue();
        Assert.assertEquals(newDroolsFactObject.getFullClassName(), "org.chtijbug.drools.runtime.test.Fibonacci");
        Assert.assertEquals(newDroolsFactObject.getObjectVersion(), 1l);
        Assert.assertTrue(newDroolsFactObject.getRealObject() instanceof Fibonacci);
        Assert.assertEquals(newDroolsFactObject.getListfactObjectAttributes().size(), 2);
        DroolsFactObjectAttribute newDroolsFactObjectAttribute1 = newDroolsFactObject.getListfactObjectAttributes().get(0);
        Assert.assertEquals(newDroolsFactObjectAttribute1.getAttributeName(), "sequence");
        Assert.assertEquals(newDroolsFactObjectAttribute1.getAttributeValue(), "1");
        Assert.assertEquals(newDroolsFactObjectAttribute1.getAttributeType(), "Integer");
        DroolsFactObjectAttribute newDroolsFactObjectAttribute2 = newDroolsFactObject.getListfactObjectAttributes().get(1);
        Assert.assertEquals(newDroolsFactObjectAttribute2.getAttributeName(), "value");
        Assert.assertEquals(newDroolsFactObjectAttribute2.getAttributeValue(), "-2");
        Assert.assertEquals(newDroolsFactObjectAttribute2.getAttributeType(), "Long");
        ruleBaseSession.retractObject(fibonacci);
        Assert.assertTrue(historyEvents.size() == 8);
        Assert.assertTrue(historyEvents.get(7) instanceof DeletedFactHistoryEvent);
        DeletedFactHistoryEvent deletedFactHistoryEvent = (DeletedFactHistoryEvent) historyEvents.get(7);
        Assert.assertEquals(deletedFactHistoryEvent.getRuleBaseID(), rulePackageID);
        Assert.assertEquals(deletedFactHistoryEvent.getEventID(), 4l);
        Assert.assertEquals(deletedFactHistoryEvent.getSessionId(), 1l);
        Assert.assertEquals(deletedFactHistoryEvent.getTypeEvent(), HistoryEvent.TypeEvent.Fact);

        DroolsFactObject deletedDroolsFactObject = deletedFactHistoryEvent.getDeletedObject();
        Assert.assertEquals(deletedDroolsFactObject.getFullClassName(), "org.chtijbug.drools.runtime.test.Fibonacci");
        Assert.assertEquals(deletedDroolsFactObject.getObjectVersion(), 1l);
        Assert.assertTrue(deletedDroolsFactObject.getRealObject() instanceof Fibonacci);
        Assert.assertEquals(deletedDroolsFactObject.getListfactObjectAttributes().size(), 2);
        DroolsFactObjectAttribute deletedDroolsFactObjectAttribute1 = deletedDroolsFactObject.getListfactObjectAttributes().get(0);
        Assert.assertEquals(deletedDroolsFactObjectAttribute1.getAttributeName(), "sequence");
        Assert.assertEquals(deletedDroolsFactObjectAttribute1.getAttributeValue(), "1");
        Assert.assertEquals(deletedDroolsFactObjectAttribute1.getAttributeType(), "Integer");
        DroolsFactObjectAttribute deletedDroolsFactObjectAttribute2 = deletedDroolsFactObject.getListfactObjectAttributes().get(1);
        Assert.assertEquals(deletedDroolsFactObjectAttribute2.getAttributeName(), "value");
        Assert.assertEquals(deletedDroolsFactObjectAttribute2.getAttributeValue(), "-2");
        Assert.assertEquals(deletedDroolsFactObjectAttribute2.getAttributeType(), "Long");
    }

    @Test
    public void KnowledgeSessionFactByReflection() throws DroolsChtijbugException {

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
        Fibonacci fibonacci = new Fibonacci(1);
        ruleBaseSession.insertByReflection(fibonacci);
        Assert.assertTrue(historyEvents.size() == 8);
        Assert.assertTrue(historyEvents.get(5) instanceof InsertedByReflectionFactStartHistoryEvent);
        InsertedByReflectionFactStartHistoryEvent insertedByReflectionFactHistoryEvent = (InsertedByReflectionFactStartHistoryEvent) historyEvents.get(5);
        Assert.assertEquals(insertedByReflectionFactHistoryEvent.getRuleBaseID(), rulePackageID);
        Assert.assertEquals(insertedByReflectionFactHistoryEvent.getEventID(), 2l);
        Assert.assertEquals(insertedByReflectionFactHistoryEvent.getSessionId(), 1l);
        Assert.assertEquals(insertedByReflectionFactHistoryEvent.getTypeEvent(), HistoryEvent.TypeEvent.Fact);
        Assert.assertTrue(historyEvents.get(6) instanceof InsertedFactHistoryEvent);
        InsertedFactHistoryEvent insertedFactHistoryEvent = (InsertedFactHistoryEvent) historyEvents.get(6);
        Assert.assertEquals(insertedFactHistoryEvent.getRuleBaseID(), rulePackageID);
        Assert.assertEquals(insertedFactHistoryEvent.getEventID(), 3l);
        Assert.assertEquals(insertedFactHistoryEvent.getSessionId(), 1l);
        Assert.assertEquals(insertedFactHistoryEvent.getTypeEvent(), HistoryEvent.TypeEvent.Fact);
        DroolsFactObject droolsFactObject = insertedFactHistoryEvent.getInsertedObject();
        Assert.assertEquals(droolsFactObject.getFullClassName(), "org.chtijbug.drools.runtime.test.Fibonacci");
        Assert.assertEquals(droolsFactObject.getObjectVersion(), 0l);
        Assert.assertTrue(droolsFactObject.getRealObject() instanceof Fibonacci);
        Assert.assertEquals(droolsFactObject.getListfactObjectAttributes().size(), 2);
        DroolsFactObjectAttribute droolsFactObjectAttribute1 = droolsFactObject.getListfactObjectAttributes().get(0);
        Assert.assertEquals(droolsFactObjectAttribute1.getAttributeName(), "sequence");
        Assert.assertEquals(droolsFactObjectAttribute1.getAttributeValue(), "1");
        Assert.assertEquals(droolsFactObjectAttribute1.getAttributeType(), "Integer");
        DroolsFactObjectAttribute droolsFactObjectAttribute2 = droolsFactObject.getListfactObjectAttributes().get(1);
        Assert.assertEquals(droolsFactObjectAttribute2.getAttributeName(), "value");
        Assert.assertEquals(droolsFactObjectAttribute2.getAttributeValue(), "-1");
        Assert.assertEquals(droolsFactObjectAttribute2.getAttributeType(), "Long");

        Assert.assertTrue(historyEvents.get(7) instanceof InsertedByReflectionFactEndHistoryEvent);
        InsertedByReflectionFactEndHistoryEvent insertedByReflectionFactEndHistoryEvent = (InsertedByReflectionFactEndHistoryEvent) historyEvents.get(7);
        Assert.assertEquals(insertedByReflectionFactEndHistoryEvent.getRuleBaseID(), rulePackageID);
        Assert.assertEquals(insertedByReflectionFactEndHistoryEvent.getEventID(), 4l);
        Assert.assertEquals(insertedByReflectionFactEndHistoryEvent.getSessionId(), 1l);
        Assert.assertEquals(insertedByReflectionFactEndHistoryEvent.getTypeEvent(), HistoryEvent.TypeEvent.Fact);

    }
}
