/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chtijbug.drools.runtime.impl;

import org.chtijbug.drools.common.log.Logger;
import org.chtijbug.drools.common.log.LoggerFactory;
import org.chtijbug.drools.entity.history.HistoryContainer;
import org.chtijbug.drools.runtime.DroolsChtijbugException;
import org.chtijbug.drools.runtime.RuleBasePackage;
import org.chtijbug.drools.runtime.RuleBaseSession;
import org.chtijbug.drools.runtime.mbeans.RuleBaseSupervision;
import org.chtijbug.drools.runtime.mbeans.StatefulSessionSupervision;
import org.chtijbug.drools.runtime.resource.DroolsResource;
import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.runtime.StatefulKnowledgeSession;

import javax.management.*;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * @author Bertrand Gressier
 */
public class RuleBaseSingleton implements RuleBasePackage {
    /**
     * Class Logger
     */
    private static Logger logger = LoggerFactory.getLogger(RuleBaseSingleton.class);
    /**
     * default rule threshold
     */
    public static int DEFAULT_RULE_THRESHOLD = 2000;
    /**
     * static part of the RuleBase Object Name *
     */
    private static String RULE_BASE_OBJECT_NAME = HistoryContainer.nameRuleBaseObjectName + "ruleBaseID ";
    /**
     * static part of the RuleBase Object Name *
     */
    private static String RULE_SESSION_OBJECT_NAME = HistoryContainer.nameSessionObjectName + "ruleBaseID ";
    /**
     * unique indentifier of the RuleBase in the JVM
     */
    protected static int ruleBaseCounter = 0;
    /**
     * Rule Base ID
     */
    private int ruleBaseID;
    /**
     * KnowledgeBase reference
     */
    private KnowledgeBase kbase = null;
    /**
     * All Drools resources inserted into the RuleBase
     */
    private final List<DroolsResource> listResouces = new ArrayList<DroolsResource>();
    /**
     * MBean JMX to enable Dynamics rule base operation
     */
    private RuleBaseSupervision mbsRuleBase;
    /**
     * MBean JMX To enable RuleSession statistics collect
     */
    private StatefulSessionSupervision mbsSession;
    /**
     * Max rule to be fired threshold.
     */
    private int maxNumberRuleToExecute = DEFAULT_RULE_THRESHOLD;
    /**
     * Semaphore used to void concurrent access to the singleton
     */
    private Semaphore lockKbase = new Semaphore(1);
    //private transient MBeanServer server = null;
    private int sessionCounter = 0;

    public RuleBaseSingleton() throws DroolsChtijbugException {
        //____ Remove Existing MBean from the MBeanServer
        try {
            if (ruleBaseCounter != 0)
                clearMBeans();
        } catch (MalformedObjectNameException e) {
            logger.warn("Error occured while clearing MBeanServer", e);
        }
        //____ Increment the ruleBaseID
        this.ruleBaseID = addRuleBase();
        initMBeans();
    }

    public RuleBaseSingleton(int maxNumberRulesToExecute) throws DroolsChtijbugException {
        this();
        this.maxNumberRuleToExecute = maxNumberRulesToExecute;
    }

    /**
     * This methods unregisters all existing MBean from the MBean Server.
     * All statistics data will be erased !!
     *
     * @throws MalformedObjectNameException
     */
    private void clearMBeans() throws MalformedObjectNameException {
        logger.entry("clearMBeans");
        try {
            MBeanServer server = ManagementFactory.getPlatformMBeanServer();
            ObjectName[] mBeans = new ObjectName[]{
                    new ObjectName(RULE_BASE_OBJECT_NAME + ruleBaseCounter),
                    new ObjectName(RULE_SESSION_OBJECT_NAME + ruleBaseCounter)
            };
            for (ObjectName objectName : mBeans) {
                try {
                    server.unregisterMBean(objectName);
                } catch (InstanceNotFoundException e) {
                    logger.info("MBean not found while unregistering it");
                } catch (MBeanRegistrationException e) {
                    logger.warn(String.format("Error append while unregistering MBean with name %s. Continue unregister process execution", objectName));
                }
            }
        } finally {
            logger.exit("clearMBeans");
        }
    }

    /**
     * This method creates and registers MBean Object into the MBeanServer.
     *
     * @throws DroolsChtijbugException
     */
    private void initMBeans() throws DroolsChtijbugException {
        //____ Get the MBeanServer from the platform
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        try {
            //____ Instanciate all expected MBeans Object
            mbsRuleBase = new RuleBaseSupervision(this);
            mbsSession = new StatefulSessionSupervision();
            //____ Register them to the MBeanServer
            server.registerMBean(mbsRuleBase, getRuleBaseObjectName());
            server.registerMBean(mbsSession, getRuleSessionObjectName());
        } catch (Exception e) {
            logger.warn("Error registering MBeans", e);
            logger.warn("All JMX Features are not available till the issue is not fixed.");
        }
    }

    /**
     * @return The ObjectName related to the Rulebase Object inside the MBeanServer
     * @throws MalformedObjectNameException
     */
    protected ObjectName getRuleBaseObjectName() throws MalformedObjectNameException {
        return new ObjectName(RULE_BASE_OBJECT_NAME + this.ruleBaseID);
    }

    /**
     * @return The ObjectName related to the RuleSession Object inside the MBeanServer
     * @throws MalformedObjectNameException
     */
    protected ObjectName getRuleSessionObjectName() throws MalformedObjectNameException {
        return new ObjectName(RULE_SESSION_OBJECT_NAME + this.ruleBaseID);
    }

    public boolean isKbaseLoaded() {
        if (kbase == null) {
            return false;
        }
        return true;
    }

    public List<DroolsResource> getListDroolsRessources() {
        return listResouces;
    }

    @Override
    public RuleBaseSession createRuleBaseSession() throws DroolsChtijbugException {
        logger.entry("createRuleBaseSession");
        try {
            //____ Creating new Rule Base Session using default rule threshold
            RuleBaseSession newRuleBaseSession = this.createRuleBaseSession(this.maxNumberRuleToExecute);
            //____ Returning it
            return newRuleBaseSession;
        } finally {
            logger.exit("createRuleBaseSession");
        }
    }

    @Override
    public RuleBaseSession createRuleBaseSession(int maxNumberRulesToExecute) throws DroolsChtijbugException {
        logger.entry("createRuleBaseSession", maxNumberRulesToExecute);
        RuleBaseSession newRuleBaseSession = null;
        try {
            if (kbase != null) {
                //____ Acquire semaphore
                try {
                    lockKbase.acquire();
                } catch (Exception e) {
                    throw new DroolsChtijbugException(DroolsChtijbugException.KbaseAcquire, "", e);
                }
                //_____ Now we can create a new stateful session using KnowledgeBase
                StatefulKnowledgeSession newDroolsSession = kbase.newStatefulKnowledgeSession();
                //_____ Increment session counter
                this.sessionCounter++;
                //_____ Wrapping the knowledge Session
                newRuleBaseSession = new RuleBaseStatefulSession(this.sessionCounter, newDroolsSession, maxNumberRulesToExecute, mbsSession);
                //_____ Release semaphore
                lockKbase.release();
            } else {
                throw new DroolsChtijbugException(DroolsChtijbugException.KbaseNotInitialised, "", null);
            }
            //____ return the wrapped KnowledgeSession
            return newRuleBaseSession;
        } finally {
            logger.exit("createRuleBaseSession", newRuleBaseSession);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.chtijbug.drools.runtime.RuleBasePackage#addDroolsResouce(org.chtijbug
     * .drools.runtime.resource.DroolsResource)
     */
    @Override
    public void addDroolsResouce(DroolsResource res) {
        listResouces.add(res);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.chtijbug.drools.runtime.RuleBasePackage#createKBase()
     */
    @Override
    public synchronized void createKBase() throws DroolsChtijbugException {

        if (kbase != null) {
            // TODO dispose all elements
        }

        try {
            KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();

            for (DroolsResource res : listResouces) {
                kbuilder.add(res.getResource(), res.getResourceType());
            }

            KnowledgeBase newkbase = KnowledgeBaseFactory.newKnowledgeBase();
            newkbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
            lockKbase.acquire();
            kbase = newkbase;
            lockKbase.release();
        } catch (Exception e) {
            logger.error("error to load Agent", e);
            throw new DroolsChtijbugException(DroolsChtijbugException.ErrorToLoadAgent, "", e);
        }
    }

    private static int addRuleBase() {
        ruleBaseCounter++;
        return ruleBaseCounter;
    }

    public int getRuleBaseID() {
        return ruleBaseID;
    }

    /**
     * This method is a helper one. It will be called automatically after XStream unmarshaling execution
     *
     * @return this object including all transient fileds
     * @throws DroolsChtijbugException
     */
    private Object readResolve() throws DroolsChtijbugException {
        initMBeans();
        return this;
    }

    /**
     * On object disposable, ensure that all MBeans have been removed from the MBeanServer
     * @throws Throwable
     */
    @Override
    protected void finalize() throws Throwable {
        clearMBeans();
    }
}
