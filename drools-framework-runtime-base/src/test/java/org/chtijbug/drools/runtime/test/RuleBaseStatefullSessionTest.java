/**
 * 
 */
package org.chtijbug.drools.runtime.test;

import org.chtijbug.drools.entity.history.HistoryEvent;
import org.chtijbug.drools.runtime.RuleBasePackage;
import org.chtijbug.drools.runtime.RuleBaseSession;
import org.chtijbug.drools.runtime.impl.RuleBaseSingleton;
import org.chtijbug.drools.runtime.resource.DrlDroolsResource;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author Bertrand Gressier
 * @date 12 déc. 2011
 * 
 * 
 */
@RunWith(MockitoJUnitRunner.class)
public class RuleBaseStatefullSessionTest {

	RuleBaseSession session;

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

		DrlDroolsResource resource = DrlDroolsResource.createClassPathResource("fibonacci.drl");
		Assert.assertNotNull("DrlDroolsResource can't be null - fibonacci.drl not found", resource.getResource());

		RuleBasePackage ruleBasePackage = new RuleBaseSingleton();
		ruleBasePackage.addDroolsResouce(resource);

		ruleBasePackage.createKBase();
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
	public void testFireAllRulesIsOk() {
		Assert.assertNotNull("RuleBaseSession can't be null", session);

		Fibonacci fibonacci = new Fibonacci(5);
		session.insertObject(fibonacci);
		session.fireAllRules();

		for (HistoryEvent hevent : session.getHistoryContainer().getListHistoryEvent()) {
			System.out.println(hevent);
		}
	}
}
