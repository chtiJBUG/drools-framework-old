/**
 *
 */
package org.chtijbug.drools.runtime.test;

import org.chtijbug.drools.runtime.resource.DrlRuleResource;
import org.chtijbug.drools.entity.history.HistoryEvent;
import org.chtijbug.drools.runtime.RuleBaseBuilder;
import org.chtijbug.drools.runtime.RuleBasePackage;
import org.chtijbug.drools.runtime.RuleBaseSession;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;

/**
 * @author Bertrand Gressier @date 12 déc. 2011
 */
@RunWith(MockitoJUnitRunner.class)
public class RuleBaseStatefullSessionfibonacciTest {

    RuleBaseSession session;
    static RuleBasePackage ruleBasePackage;

    static private DrlRuleResource fibonacciFile;

    @Before
    public void justBefore(){

    }

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        fibonacciFile = DrlRuleResource.createClassPathResource( "fibonacci.drl");
        ruleBasePackage = RuleBaseBuilder.createRuleBasePackage(1L,"com.pymmasoftware.test", "fibonacci", Arrays.asList(fibonacciFile));
    }

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        session = ruleBasePackage.createRuleBaseSession();
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
        session.dispose();
    }

    @Test
    public void testFireAllRulesIsOk() throws Exception {
        Assert.assertNotNull("RuleBaseSession can't be null", session);
        // for (int i = 0; i < 1000; i++) {
        RuleBaseSession session1 = ruleBasePackage.createRuleBaseSession();
        Fibonacci fibonacci = new Fibonacci(5);
        session1.insertObject(fibonacci);
        session1.fireAllRules();
        Assert.assertEquals(3, session1.listRules().size());
        session1.dispose();
        //Thread.sleep(2000);
        //}


    }

    @Test
    public void testFireAllRulesIsOk1() throws Exception {
        Assert.assertNotNull("RuleBaseSession can't be null", session);

        Fibonacci fibonacci = new Fibonacci(5);
        session.insertObject(fibonacci);
        session.fireAllRules();

        for (HistoryEvent hevent : session.getHistoryContainer().getListHistoryEvent()) {
            System.out.println(hevent);
        }

        Assert.assertEquals(3, session.listRules().size());
    }

    @Test
    public void testFireAllRulesIsOk2() throws Exception {
        Assert.assertNotNull("RuleBaseSession can't be null", session);

        Fibonacci fibonacci = new Fibonacci(5);
        session.insertObject(fibonacci);
        session.fireAllRules();

        for (HistoryEvent hevent : session.getHistoryContainer().getListHistoryEvent()) {
            System.out.println(hevent);
        }

        Assert.assertEquals(3, session.listRules().size());
    }

    @Test
    public void testFireAllRulesIs3Ok() throws Exception {
        Assert.assertNotNull("RuleBaseSession can't be null", session);

        Fibonacci fibonacci = new Fibonacci(5);
        session.insertObject(fibonacci);
        session.fireAllRules();

        for (HistoryEvent hevent : session.getHistoryContainer().getListHistoryEvent()) {
            System.out.println(hevent);
        }

        Assert.assertEquals(3, session.listRules().size());
    }

}
