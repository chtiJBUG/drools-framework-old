package org.chtijbug.drools.runtime.test;

import org.chtijbug.drools.entity.DroolsRuleObject;
import org.chtijbug.drools.entity.history.HistoryEvent;
import org.chtijbug.drools.entity.history.rule.AfterRuleFiredHistoryEvent;
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
            RuleBasePackage ruleBasePackage = RuleBaseBuilder.createPackageBasePackageWithListener(historyListener, "ruleflow2.drl","RuleFlowProcess2.bpmn2");
            int rulePackageID = ruleBasePackage.getRuleBaseID();

            RuleBaseSession ruleBaseSession1 = ruleBasePackage.createRuleBaseSession();
            Fibonacci fibonacci = new Fibonacci(0);
            ruleBaseSession1.insertObject(fibonacci);
            ruleBaseSession1.startProcess("P1");
            ruleBaseSession1.fireAllRules();
/**            Assert.assertTrue(historyEvents.size() == 11);
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
 **/
        }


}
