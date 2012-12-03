package org.chtijbug.drools.runtime.impl;

import org.chtijbug.drools.runtime.DroolsChtijbugException;
import org.chtijbug.drools.runtime.impl.RuleBaseSingleton;
import org.chtijbug.drools.runtime.mbeans.StatefulSessionSupervision;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.management.InstanceNotFoundException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectInstance;

import java.lang.management.ManagementFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest(RuleBaseSingleton.class)
@PowerMockIgnore("javax.management.*")
public class RuleBaseSingletonTest {

    @Test
    public void should_not_fail_on_mbean_error() throws Exception {
        whenNew(StatefulSessionSupervision.class).withNoArguments().thenThrow(IllegalArgumentException.class);
        new RuleBaseSingleton();
    }

    @Test
    public void ensureMBeanUnregistering() {
        //___ First create RuleBaseSingleton to initialize counter
        try {

            MBeanServer server = ManagementFactory.getPlatformMBeanServer();

            Integer mBeanInstancesCount = server.getMBeanCount();
            Integer expectedCount = mBeanInstancesCount+2;
            int expectedId = RuleBaseSingleton.ruleBaseCounter;

            //____ 1rst assertion set
            RuleBaseSingleton singleton = new RuleBaseSingleton();
            assertEquals(expectedId+1,singleton.getRuleBaseID());

            ObjectInstance objectInstance = server.getObjectInstance(singleton.getRuleBaseObjectName());
            assertNotNull(objectInstance);

            objectInstance = server.getObjectInstance(singleton.getRuleSessionObjectName());
            assertNotNull(objectInstance);

            assertEquals(expectedCount, server.getMBeanCount());

            //____ 2ns set. --> MBean count should stay the same...
            RuleBaseSingleton secondInstance = new RuleBaseSingleton();
            assertEquals(expectedId+2,secondInstance.getRuleBaseID());

            objectInstance = server.getObjectInstance(secondInstance.getRuleBaseObjectName());
            assertNotNull(objectInstance);

            objectInstance = server.getObjectInstance(secondInstance.getRuleSessionObjectName());
            assertNotNull(objectInstance);

            assertEquals(expectedCount, server.getMBeanCount());



        } catch (DroolsChtijbugException e) {
            fail();
        } catch (MalformedObjectNameException e) {
            fail();
        } catch (InstanceNotFoundException e) {
            fail();
        }
    }
}
