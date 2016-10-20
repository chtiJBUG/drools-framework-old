package org.chtijbug.drools.runtime.impl;

import org.chtijbug.drools.runtime.DroolsChtijbugException;
import org.chtijbug.drools.runtime.mbeans.StatefulSessionSupervision;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.management.*;
import java.lang.management.ManagementFactory;

import static junit.framework.Assert.*;
import static org.junit.Assert.assertTrue;
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
    public void should_register_and_unregister_mbeans() throws DroolsChtijbugException, MalformedObjectNameException, InstanceNotFoundException {
        //___ First create RuleBaseSingleton to initialize counter
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        int initialMbeanCount = server.getMBeanCount();
        int expectedMaxCount = initialMbeanCount + 2;
        int initialRuleId = RuleBaseSingleton.ruleBaseCounter;

        //____ 1rst assertion set
        RuleBaseSingleton singleton = new RuleBaseSingleton();
        assertEquals(initialRuleId + 1, singleton.getRuleBaseID());
        assertMbeansAreRegistered(server, singleton);
        assertTrue(server.getMBeanCount() <= expectedMaxCount);

        //____ 2ns set. --> MBean count should stay the same...
        singleton = new RuleBaseSingleton();
        assertEquals(initialRuleId + 2, singleton.getRuleBaseID());
        assertMbeansAreRegistered(server, singleton);
        assertTrue(server.getMBeanCount() <= expectedMaxCount);
    }

    private void assertMbeansAreRegistered(MBeanServer server, RuleBaseSingleton singleton) throws InstanceNotFoundException, MalformedObjectNameException {
        assertNotNull(server.getObjectInstance(singleton.getRuleBaseObjectName()));
        assertNotNull(server.getObjectInstance(singleton.getRuleSessionObjectName()));
    }

    @Test
    public void should_unregister_mbeans_ond_demand() throws Exception {
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();

        RuleBaseSingleton singleton = new RuleBaseSingleton();
        assertNotNull(server.getMBeanInfo(singleton.getRuleBaseObjectName()));
        assertNotNull(server.getMBeanInfo(singleton.getRuleSessionObjectName()));

        singleton.cleanup();
        assertInstanceNotFound(server, singleton.getRuleBaseObjectName());
        assertInstanceNotFound(server, singleton.getRuleSessionObjectName());
    }

    private void assertInstanceNotFound(MBeanServer server, ObjectName ruleBaseObjectName) throws IntrospectionException, ReflectionException {
        try {
            server.getMBeanInfo(ruleBaseObjectName);
            fail("Expected an exception");
        } catch (InstanceNotFoundException e) {
            // as expected
        }

    }
}

