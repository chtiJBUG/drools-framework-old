package org.chtijbug.drools.runtime.test;

import org.chtijbug.drools.entity.DroolsRuleObject;
import org.chtijbug.drools.entity.history.HistoryEvent;
import org.chtijbug.drools.entity.history.rule.AfterRuleFiredHistoryEvent;
import org.chtijbug.drools.entity.history.rule.AfterRuleFlowActivatedHistoryEvent;
import org.chtijbug.drools.entity.history.rule.AfterRuleFlowDeactivatedHistoryEvent;
import org.chtijbug.drools.entity.history.rule.BeforeRuleFiredHistoryEvent;
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
 * Date: 18/02/14
 * Time: 13:11
 * To change this template use File | Settings | File Templates.
 */
public class RuleHistoryEventTest {
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
        Assert.assertTrue(historyEvents.get(7) instanceof BeforeRuleFiredHistoryEvent);
        BeforeRuleFiredHistoryEvent beforeRuleFiredHistoryEvent = (BeforeRuleFiredHistoryEvent) historyEvents.get(7);
        Assert.assertEquals(beforeRuleFiredHistoryEvent.getRuleBaseID(), rulePackageID);
        Assert.assertEquals(beforeRuleFiredHistoryEvent.getEventID(), 4l);
        Assert.assertEquals(beforeRuleFiredHistoryEvent.getSessionId(), 1l);
        Assert.assertEquals(beforeRuleFiredHistoryEvent.getTypeEvent(), HistoryEvent.TypeEvent.Rule);
        DroolsRuleObject droolsRuleObject = beforeRuleFiredHistoryEvent.getRule();
        Assert.assertEquals(droolsRuleObject.getRuleName(), "Bootstrap");
        Assert.assertEquals(droolsRuleObject.getRulePackageName(), "org.chtijbug.drools.runtime.test");

        Assert.assertTrue(historyEvents.get(9) instanceof AfterRuleFiredHistoryEvent);
        AfterRuleFiredHistoryEvent afterRuleFiredHistoryEvent = (AfterRuleFiredHistoryEvent) historyEvents.get(9);
        Assert.assertEquals(afterRuleFiredHistoryEvent.getRuleBaseID(), rulePackageID);
        Assert.assertEquals(afterRuleFiredHistoryEvent.getEventID(), 6l);
        Assert.assertEquals(afterRuleFiredHistoryEvent.getSessionId(), 1l);
        Assert.assertEquals(afterRuleFiredHistoryEvent.getTypeEvent(), HistoryEvent.TypeEvent.Rule);
        DroolsRuleObject droolsRuleObject2 = afterRuleFiredHistoryEvent.getRule();
        Assert.assertEquals(droolsRuleObject2.getRuleName(), "Bootstrap");
        Assert.assertEquals(droolsRuleObject2.getRulePackageName(), "org.chtijbug.drools.runtime.test");
        Assert.assertEquals(afterRuleFiredHistoryEvent.getRuleInstanceId(), 1l);
    }

    @Test
    public void KnowledgeBaseFireAllRulesRuleFlowGroup() throws DroolsChtijbugException {

        final List<HistoryEvent> historyEvents = new ArrayList<HistoryEvent>();
        HistoryListener historyListener = new HistoryListener() {
            @Override
            public void fireEvent(HistoryEvent newHistoryEvent) throws DroolsChtijbugException {
                historyEvents.add(newHistoryEvent);
            }
        };
        RuleBasePackage ruleBasePackage = RuleBaseBuilder.createPackageBasePackageWithListener(historyListener, "ruleflow2.drl", "RuleFlowProcess2.bpmn2");
        int rulePackageID = ruleBasePackage.getRuleBaseID();

        RuleBaseSession ruleBaseSession1 = ruleBasePackage.createRuleBaseSession();
        Fibonacci fibonacci = new Fibonacci(0);
        ruleBaseSession1.insertObject(fibonacci);
        ruleBaseSession1.startProcess("P1");
        ruleBaseSession1.fireAllRules();
        Assert.assertTrue(historyEvents.size() == 40);
        Assert.assertTrue(historyEvents.get(11) instanceof AfterRuleFlowActivatedHistoryEvent);
        AfterRuleFlowActivatedHistoryEvent afterRuleFlowActivatedHistoryEvent = (AfterRuleFlowActivatedHistoryEvent) historyEvents.get(11);
        Assert.assertEquals(afterRuleFlowActivatedHistoryEvent.getRuleBaseID(), rulePackageID);
        Assert.assertEquals(afterRuleFlowActivatedHistoryEvent.getEventID(), 8l);
        Assert.assertEquals(afterRuleFlowActivatedHistoryEvent.getSessionId(), 1l);
        Assert.assertEquals(afterRuleFlowActivatedHistoryEvent.getTypeEvent(), HistoryEvent.TypeEvent.RuleFlowGroup);
        Assert.assertEquals(afterRuleFlowActivatedHistoryEvent.getDroolsRuleFlowGroupObject().getName(), "Group1");

        Assert.assertTrue(historyEvents.get(20) instanceof AfterRuleFiredHistoryEvent);
         AfterRuleFiredHistoryEvent afterRuleFiredHistoryEvent = (AfterRuleFiredHistoryEvent) historyEvents.get(20);
         Assert.assertEquals(afterRuleFiredHistoryEvent.getRuleBaseID(), rulePackageID);
         Assert.assertEquals(afterRuleFiredHistoryEvent.getEventID(), 17l);
         Assert.assertEquals(afterRuleFiredHistoryEvent.getSessionId(), 1l);
         Assert.assertEquals(afterRuleFiredHistoryEvent.getTypeEvent(), HistoryEvent.TypeEvent.Rule);
         DroolsRuleObject droolsRuleObject2 = afterRuleFiredHistoryEvent.getRule();
         Assert.assertEquals(droolsRuleObject2.getRuleName(), "Account group1");
         Assert.assertEquals(droolsRuleObject2.getRulePackageName(), "org.chtijbug.drools.runtime.test");
         Assert.assertEquals(afterRuleFiredHistoryEvent.getRuleInstanceId(), 1l);


        Assert.assertTrue(historyEvents.get(21) instanceof AfterRuleFlowDeactivatedHistoryEvent);
        AfterRuleFlowDeactivatedHistoryEvent afterRuleFlowDeactivatedHistoryEvent = (AfterRuleFlowDeactivatedHistoryEvent) historyEvents.get(21);
        Assert.assertEquals(afterRuleFlowDeactivatedHistoryEvent.getRuleBaseID(), rulePackageID);
        Assert.assertEquals(afterRuleFlowDeactivatedHistoryEvent.getEventID(), 18l);
        Assert.assertEquals(afterRuleFlowDeactivatedHistoryEvent.getSessionId(), 1l);
        Assert.assertEquals(afterRuleFlowDeactivatedHistoryEvent.getTypeEvent(), HistoryEvent.TypeEvent.RuleFlowGroup);
        Assert.assertEquals(afterRuleFlowDeactivatedHistoryEvent.getDroolsRuleFlowGroupObject().getName(), "Group1");



    }


}
