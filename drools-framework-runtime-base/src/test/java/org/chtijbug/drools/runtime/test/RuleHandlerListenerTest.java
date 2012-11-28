package org.chtijbug.drools.runtime.test;

import org.chtijbug.drools.entity.DroolsFactObjectAttribute;
import org.chtijbug.drools.entity.history.HistoryEvent;
import org.chtijbug.drools.entity.history.rule.AfterRuleFiredHistoryEvent;
import org.chtijbug.drools.entity.history.rule.BeforeRuleFiredHistoryEvent;
import org.chtijbug.drools.runtime.DroolsChtijbugException;
import org.chtijbug.drools.runtime.RuleBaseBuilder;
import org.chtijbug.drools.runtime.RuleBasePackage;
import org.chtijbug.drools.runtime.RuleBaseSession;
import org.junit.*;

import java.util.List;

import static org.junit.Assert.fail;

/**
 * Created by IntelliJ IDEA.
 * Date: 23/11/12
 * Time: 09:44
 * To change this template use File | Settings | File Templates.
 */
public class RuleHandlerListenerTest {
    RuleBaseSession session;
    static RuleBasePackage ruleBasePackage;

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {

    }

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void DefaultMaxNumberRUleExecuted() throws Exception {
        ruleBasePackage = RuleBaseBuilder.createPackageBasePackage("infiniteLoop.drl");
        session = ruleBasePackage.createRuleBaseSession();
        Fibonacci newObject = new Fibonacci(0);
        session.insertObject(newObject);
        try {
            session.fireAllRules();
            fail();
        } catch (DroolsChtijbugException e) {
            Assert.assertEquals(e.getKey(), DroolsChtijbugException.MaxNumberRuleExecutionReached);
        }
    }


    @Test
    public void max10tMaxNumberRUleExecuted() throws Exception {
        ruleBasePackage = RuleBaseBuilder.createPackageBasePackage("infiniteLoop.drl");

        session = ruleBasePackage.createRuleBaseSession(10);

        Fibonacci newObject = new Fibonacci(0);
        session.insertObject(newObject);
        try {
            session.fireAllRules();
            fail();
        } catch (DroolsChtijbugException e) {
            Assert.assertEquals(session.getNumberRulesExecuted(), 10);
            Assert.assertEquals(e.getKey(), DroolsChtijbugException.MaxNumberRuleExecutionReached);
        }
    }

    @Test
    public void RuleEvent() throws Exception {
        ruleBasePackage = RuleBaseBuilder.createPackageBasePackage("rulehandler1.drl");

        session = ruleBasePackage.createRuleBaseSession();

        Fibonacci newObject = new Fibonacci(0);
        session.insertObject(newObject);
        session.fireAllRules();
        List<HistoryEvent> eventList = session.getHistoryContainer().getListHistoryEvent();
        Assert.assertEquals(eventList.size(), 4);
        /*
            BeforeRuleFiredHistoryEvent
         */
        HistoryEvent event1 = eventList.get(1);
        Assert.assertEquals(event1 instanceof BeforeRuleFiredHistoryEvent, true);
        BeforeRuleFiredHistoryEvent beforeRuleFiredHistoryEvent = (BeforeRuleFiredHistoryEvent) event1;
        Assert.assertEquals(beforeRuleFiredHistoryEvent.getRule().getRuleName(), "rule1");
        Assert.assertEquals(beforeRuleFiredHistoryEvent.getWhenObjects().size(), 1);
        Assert.assertEquals(beforeRuleFiredHistoryEvent.getWhenObjects().get(0).getFullClassName(), "org.chtijbug.drools.runtime.test.Fibonacci");
        Assert.assertEquals(beforeRuleFiredHistoryEvent.getWhenObjects().get(0).getListfactObjectAttributes().size(), 2);
        List<DroolsFactObjectAttribute> droolsFactObjectAttributes = beforeRuleFiredHistoryEvent.getWhenObjects().get(0).getListfactObjectAttributes();
        Assert.assertEquals(droolsFactObjectAttributes.get(0).getAttributeValue(), "0");
        /*
           AfterRuleFiredHistoryEvent
        */
        HistoryEvent event3 = eventList.get(3);
        Assert.assertEquals(event3 instanceof AfterRuleFiredHistoryEvent, true);
        AfterRuleFiredHistoryEvent afterRuleFiredHistoryEvent = (AfterRuleFiredHistoryEvent) event3;
        Assert.assertEquals(afterRuleFiredHistoryEvent.getRule().getRuleName(), "rule1");
    }


    @Test
    public void RuleFLowgroup1() throws Exception {
        ruleBasePackage = RuleBaseBuilder.createPackageBasePackage("ruleflow1.drl", "RuleFlowProcess1.bpmn2");

        session = ruleBasePackage.createRuleBaseSession();

        Fibonacci newObject = new Fibonacci(0);
        session.insertObject(newObject);
        session.startProcess("P1");
        session.fireAllRules();
        //Assert.assertEquals(session.getNumberRulesExecuted(), 10);

    }

}
