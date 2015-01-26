package org.chtijbug.drools.runtime;

import junit.framework.TestCase;
import org.chtijbug.drools.entity.history.HistoryEvent;
import org.chtijbug.drools.runtime.listener.HistoryListener;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class RuleBaseBuilderTest extends TestCase {

    @Test
    public void testCreateWorkbenchRuleBasePackage() throws Exception {
        final List<HistoryEvent> historyEvents = new ArrayList<HistoryEvent>();
        HistoryListener historyListener = new HistoryListener() {
            @Override
            public void fireEvent(HistoryEvent newHistoryEvent) throws DroolsChtijbugException {
                historyEvents.add(newHistoryEvent);
            }
        };
        RuleBasePackage ruleBase = RuleBaseBuilder.createWorkbenchRuleBasePackage(historyListener, "org.chtijbug.drools6", "fibonacci", "1.0.0", "http://192.168.59.103:8080/kie-wb/", "pymma", "abcde");


        RuleBaseSession session = ruleBase.createRuleBaseSession();

        org.chtijbug.Test test = new org.chtijbug.Test();
        test.setValue("coucou");
        session.insertObject(test);
        session.fireAllRules();



    }
}