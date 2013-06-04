package com.chtijbug.drools.runtime; /**
 *
 */

import org.chtijbug.drools.entity.history.HistoryEvent;
import org.chtijbug.drools.runtime.Fibonacci;
import org.chtijbug.drools.runtime.NeojLoggerRuleBaseBuilder;
import org.chtijbug.drools.runtime.RuleBasePackage;
import org.chtijbug.drools.runtime.RuleBaseSession;
import org.junit.*;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Bertrand Gressier @date 12 déc. 2011
 */

public class RuleBaseStatefullSessionfibonacciTest {

    RuleBaseSession session;
    static RuleBasePackage ruleBasePackage;

    /**
     * @throws Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        Map<String, String> config = new HashMap<String, String>();
        config.put( "neostore.nodestore.db.mapped_memory", "10M" );
        config.put( "string_block_size", "60" );
        config.put( "array_block_size", "300" );
        GraphDatabaseService graphDb = new GraphDatabaseFactory()
            .newEmbeddedDatabaseBuilder("target/database/location")
            .setConfig(config)
            .newGraphDatabase();

        ruleBasePackage = NeojLoggerRuleBaseBuilder.createPackageBasePackage(graphDb,"fibonacci.drl");
    }

    /**
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        session = ruleBasePackage.createRuleBaseSession();
    }

    /**
     * @throws Exception
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
