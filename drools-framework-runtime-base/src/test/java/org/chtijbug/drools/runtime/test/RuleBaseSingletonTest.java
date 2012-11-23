package org.chtijbug.drools.runtime.test;

import org.chtijbug.drools.runtime.RuleBasePackage;
import org.chtijbug.drools.runtime.RuleBaseSession;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by IntelliJ IDEA.
 * Date: 23/11/12
 * Time: 11:01
 * To change this template use File | Settings | File Templates.
 */
public class RuleBaseSingletonTest {
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
     }
}
