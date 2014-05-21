/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chtijbug.drools.runtime.impl;

import com.google.common.base.Throwables;
import org.chtijbug.drools.entity.history.HistoryContainer;
import org.chtijbug.drools.entity.history.knowledge.*;
import org.chtijbug.drools.runtime.DroolsChtijbugException;
import org.chtijbug.drools.runtime.RuleBasePackage;
import org.chtijbug.drools.runtime.RuleBaseSession;
import org.chtijbug.drools.runtime.listener.HistoryListener;
import org.chtijbug.drools.runtime.mbeans.RuleBaseSupervision;
import org.chtijbug.drools.runtime.mbeans.StatefulSessionSupervision;
import org.chtijbug.drools.runtime.resource.Bpmn2DroolsRessource;
import org.chtijbug.drools.runtime.resource.DrlDroolsRessource;
import org.chtijbug.drools.runtime.resource.DroolsResource;
import org.chtijbug.drools.runtime.resource.GuvnorDroolsResource;
import org.drools.KnowledgeBase;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderConfiguration;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.*;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.Date;
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
    /**
     * Guvnor Connection
     */
    private String guvnor_url;
    private String guvnor_appName;
    private String guvnor_packageName;
    private String guvnor_packageVersion;
    private String guvnor_username;
    private String guvnor_password;
    /**
     *  Java Dialect
     */
     private JavaDialect javaDialect=JavaDialect.ECLIPSE;
    /**
     * History Listener
     */
    private HistoryListener historyListener = null;

    private int eventCounter;

    public RuleBaseSingleton() throws DroolsChtijbugException {
        //____ Remove Existing MBean from the MBeanServer
        if (ruleBaseCounter != 0)
            clearPreviousInstanceMBeans();
        //____ Increment the ruleBaseID
        this.ruleBaseID = addRuleBase();
        initMBeans();
    }


    public RuleBaseSingleton(int maxNumberRulesToExecute) throws DroolsChtijbugException {
        this();
        this.maxNumberRuleToExecute = maxNumberRulesToExecute;
    }

    public RuleBaseSingleton(int maxNumberRulesToExecute, HistoryListener historyListener) throws DroolsChtijbugException {
        this(maxNumberRulesToExecute);
        this.historyListener = historyListener;
        if (this.historyListener != null) {
            KnowledgeBaseCreatedEvent knowledgeBaseCreatedEvent = new KnowledgeBaseCreatedEvent(this.getNextEventCounter(), new Date(), ruleBaseCounter);
            this.historyListener.fireEvent(knowledgeBaseCreatedEvent);
        }
    }

    public RuleBaseSingleton(HistoryListener historyListener) throws DroolsChtijbugException {
        this();
        this.historyListener = historyListener;
        if (this.historyListener != null) {
            KnowledgeBaseCreatedEvent knowledgeBaseCreatedEvent = new KnowledgeBaseCreatedEvent(this.getNextEventCounter(), new Date(), ruleBaseCounter);
            this.historyListener.fireEvent(knowledgeBaseCreatedEvent);
        }
    }

    /**
     * This methods unregisters all existing MBean from the MBean Server.
     * All statistics data will be erased !!
     */
    private static void clearPreviousInstanceMBeans() {
        logger.debug(">>clearPreviousInstanceMBeans");
        logger.debug("ruleBaseCounter : {}", ruleBaseCounter);
        try {
            MBeanServer server = ManagementFactory.getPlatformMBeanServer();
            unregisterMBean(server, new ObjectName(RULE_BASE_OBJECT_NAME + ruleBaseCounter));
            unregisterMBean(server, new ObjectName(RULE_SESSION_OBJECT_NAME + ruleBaseCounter));
        } catch (MalformedObjectNameException e) {
            logger.warn("Oups, wrong object name", e);
        } finally {
            logger.debug("<<clearPreviousInstanceMBeans");
        }
    }

    private static void unregisterMBean(MBeanServer server, ObjectName objectName) {
        try {
            server.unregisterMBean(objectName);
        } catch (InstanceNotFoundException e) {
            logger.info("MBean not found while unregistering it {}", objectName);
        } catch (MBeanRegistrationException e) {
            logger.warn("Error append while unregistering MBean with name {}. Continue unregister process execution", objectName);
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
            ObjectName objectName = getRuleBaseObjectName();
            logger.info("Registering MBean with name {}", objectName);
            server.registerMBean(mbsRuleBase, objectName);

            objectName = getRuleSessionObjectName();
            logger.info("Registering MBean with name {}", objectName);
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
        logger.debug(">>createRuleBaseSession");
        try {
            //____ Creating new Rule Base Session using default rule threshold
            RuleBaseSession newRuleBaseSession = this.createRuleBaseSession(this.maxNumberRuleToExecute);
            //____ Returning it
            return newRuleBaseSession;
        } finally {
            logger.debug("<<createRuleBaseSession");
        }
    }

    @Override
    public RuleBaseSession createRuleBaseSession(int maxNumberRulesToExecute) throws DroolsChtijbugException {
        logger.debug(">>createRuleBaseSession", maxNumberRulesToExecute);
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
                if (this.historyListener != null) {
                    KnowledgeBaseCreateSessionEvent knowledgeBaseCreateSessionEvent = new KnowledgeBaseCreateSessionEvent(this.getNextEventCounter(), new Date(), this.ruleBaseID);
                    knowledgeBaseCreateSessionEvent.setSessionId(this.sessionCounter);
                    this.historyListener.fireEvent(knowledgeBaseCreateSessionEvent);
                }

                //_____ Wrapping the knowledge Session
                newRuleBaseSession = new RuleBaseStatefulSession(this.ruleBaseID, this.sessionCounter, newDroolsSession, maxNumberRulesToExecute, mbsSession, this.historyListener);
                //_____ Release semaphore
                lockKbase.release();
            } else {
                throw new DroolsChtijbugException(DroolsChtijbugException.KbaseNotInitialised, "", null);
            }
            //____ return the wrapped KnowledgeSession
            return newRuleBaseSession;
        } finally {
            logger.debug("<<createRuleBaseSession", newRuleBaseSession);
        }
    }


    private void addDroolsResource(DroolsResource res) throws DroolsChtijbugException {
        if (this.listResouces.contains(res) == true) {
            DroolsChtijbugException droolsChtijbugException = new DroolsChtijbugException(DroolsChtijbugException.RessourceAlreadyAdded, res.toString(), null);
            throw droolsChtijbugException;
        }
        if (res instanceof GuvnorDroolsResource) {
            GuvnorDroolsResource guvnorDroolsResource = (GuvnorDroolsResource) res;
            this.guvnor_url = guvnorDroolsResource.getBaseUrl();
            this.guvnor_appName = guvnorDroolsResource.getWebappName();
            this.guvnor_packageName = guvnorDroolsResource.getPackageName();
            this.guvnor_packageVersion = guvnorDroolsResource.getPackageVersion();
            this.guvnor_username = guvnorDroolsResource.getUsername();
            this.guvnor_password = guvnorDroolsResource.getPassword();
            if (this.historyListener != null) {
                KnowledgeBaseAddRessourceEvent knowledgeBaseAddRessourceEvent = new KnowledgeBaseAddRessourceEvent(this.getNextEventCounter(), new Date(), this.ruleBaseID, this.guvnor_url, this.guvnor_appName, this.guvnor_packageName, this.guvnor_packageVersion);
                this.historyListener.fireEvent(knowledgeBaseAddRessourceEvent);
            }

        } else if (res instanceof DrlDroolsRessource) {
            DrlDroolsRessource drlDroolsRessource = (DrlDroolsRessource) res;
            if (this.historyListener != null) {
                KnowledgeBaseAddRessourceEvent knowledgeBaseAddRessourceEvent = new KnowledgeBaseAddRessourceEvent(this.getNextEventCounter(), new Date(), this.ruleBaseID, drlDroolsRessource.getFileName(), drlDroolsRessource.getFileContent());
                this.historyListener.fireEvent(knowledgeBaseAddRessourceEvent);
            }

        } else if (res instanceof Bpmn2DroolsRessource) {
            Bpmn2DroolsRessource bpmn2DroolsRessource = (Bpmn2DroolsRessource) res;
            if (this.historyListener != null) {
                KnowledgeBaseAddRessourceEvent knowledgeBaseAddRessourceEvent = new KnowledgeBaseAddRessourceEvent(this.getNextEventCounter(), new Date(), this.ruleBaseID, bpmn2DroolsRessource.getFileName(), bpmn2DroolsRessource.getFileContent());
                this.historyListener.fireEvent(knowledgeBaseAddRessourceEvent);
            }

        }
        listResouces.add(res);
    }
    private void deleteAllDroolsResources() throws DroolsChtijbugException {
        for (DroolsResource droolsResource : this.listResouces) {
            this.deleteDroolsResource(droolsResource);
        }
        this.listResouces.clear();

    }

    private void deleteDroolsResource(DroolsResource res) throws DroolsChtijbugException {
        if (this.listResouces.contains(res) == false) {
            DroolsChtijbugException droolsChtijbugException = new DroolsChtijbugException(DroolsChtijbugException.RessourceDoesNotExist, res.toString(), null);
            throw droolsChtijbugException;
        }
        if (this.historyListener != null) {
            if (res instanceof GuvnorDroolsResource) {
                KnowledgeBaseDelRessourceEvent knowledgeBaseDelRessourceEvent = new KnowledgeBaseDelRessourceEvent(this.getNextEventCounter(), new Date(), this.ruleBaseID, this.guvnor_url, this.guvnor_appName, this.guvnor_packageName, this.guvnor_packageVersion);
                this.historyListener.fireEvent(knowledgeBaseDelRessourceEvent);
            } else if (res instanceof DrlDroolsRessource) {
                DrlDroolsRessource drlDroolsRessource = (DrlDroolsRessource) res;
                if (this.historyListener != null) {
                    KnowledgeBaseDelRessourceEvent knowledgeBaseDelRessourceEvent = new KnowledgeBaseDelRessourceEvent(this.getNextEventCounter(), new Date(), this.ruleBaseID, drlDroolsRessource.getFileName(), drlDroolsRessource.getFileContent());
                    this.historyListener.fireEvent(knowledgeBaseDelRessourceEvent);
                }

            } else if (res instanceof Bpmn2DroolsRessource) {
                Bpmn2DroolsRessource bpmn2DroolsRessource = (Bpmn2DroolsRessource) res;
                if (this.historyListener != null) {
                    KnowledgeBaseDelRessourceEvent knowledgeBaseDelRessourceEvent = new KnowledgeBaseDelRessourceEvent(this.getNextEventCounter(), new Date(), this.ruleBaseID, bpmn2DroolsRessource.getFileName(), bpmn2DroolsRessource.getFileContent());
                    this.historyListener.fireEvent(knowledgeBaseDelRessourceEvent);
                }

            }
        }
    }

    public void setGuvnor_username(String guvnor_username) {
        this.guvnor_username = guvnor_username;
    }

    public void setGuvnor_password(String guvnor_password) {
        this.guvnor_password = guvnor_password;
    }

    private synchronized void createKBase() throws DroolsChtijbugException {

        if (kbase != null) {
            if (this.historyListener != null) {
                KnowledgeBaseReloadedEvent knowledgeBaseReloadLoadEvent = new KnowledgeBaseReloadedEvent(this.getNextEventCounter(), new Date(), this.ruleBaseID, this.guvnor_url, this.guvnor_appName, this.guvnor_packageName, this.guvnor_packageVersion);
                this.historyListener.fireEvent(knowledgeBaseReloadLoadEvent);
            }
            // TODO dispose all elements
        } else {
            if (this.historyListener != null) {
                KnowledgeBaseInitialLoadEvent knowledgeBaseInitialLoadEvent = new KnowledgeBaseInitialLoadEvent(this.getNextEventCounter(), new Date(), this.ruleBaseID, this.guvnor_url, this.guvnor_appName, this.guvnor_packageName, this.guvnor_packageVersion);
                this.historyListener.fireEvent(knowledgeBaseInitialLoadEvent);
            }
        }

        try {
            KnowledgeBuilderConfiguration config = KnowledgeBuilderFactory.newKnowledgeBuilderConfiguration();
            config.setProperty("drools.dialect.java.compiler", this.javaDialect.toString());
            KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder(config);

            for (DroolsResource res : listResouces) {
                kbuilder.add(res.getResource(), res.getResourceType());
            }
            KnowledgeBase newkbase = kbuilder.newKnowledgeBase();
            lockKbase.acquire();
            kbase = newkbase;
            lockKbase.release();
        } catch (Exception e) {
            logger.error("error to load Agent", e);
            throw new DroolsChtijbugException(DroolsChtijbugException.ErrorToLoadAgent, "", e);
        }
    }

    @Override
    public void createKBase(DroolsResource... res) throws DroolsChtijbugException {
       this.deleteAllDroolsResources();
        for (DroolsResource droolsResource : res) {
            this.addDroolsResource(droolsResource);
        }
        this.createKBase();
    }

    @Override
    public void createKBase(List<DroolsResource> res) throws DroolsChtijbugException {
        this.deleteAllDroolsResources();
        for (DroolsResource droolsResource : res) {
            this.addDroolsResource(droolsResource);
        }
        this.createKBase();
    }

    @Override
    public void RecreateKBaseWithNewRessources(DroolsResource... res) throws DroolsChtijbugException {
        this.deleteAllDroolsResources();
        for (DroolsResource droolsResource : res) {
            this.addDroolsResource(droolsResource);
        }
        this.createKBase();
    }

    @Override
    public void RecreateKBaseWithNewRessources(List<DroolsResource> res) throws DroolsChtijbugException {
        this.deleteAllDroolsResources();
        for (DroolsResource droolsResource : res) {
            this.addDroolsResource(droolsResource);
        }
        this.createKBase();
    }

    @Override
    public void ReloadWithSameRessources() throws DroolsChtijbugException {
        this.createKBase();
    }

    @Override
    public HistoryListener getHistoryListener() {
        return historyListener;
    }

    private static int addRuleBase() {
        ruleBaseCounter++;
        logger.info("New rule base ID : " + ruleBaseCounter);
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

    @Override
    public void dispose() {
          if (this.historyListener != null) {
            KnowledgeBaseDisposeEvent knowledgeBaseDisposeEvent = new KnowledgeBaseDisposeEvent(this.getNextEventCounter(), new Date(), this.ruleBaseID);
            try {
                this.historyListener.fireEvent(knowledgeBaseDisposeEvent);
            } catch (DroolsChtijbugException e) {
                throw Throwables.propagate(e);
            }

        }
        this.cleanup();
        this.kbase = null;

    }

    @Override
    public void cleanup() {
        clearInstanceMBeans();
    }

    private void clearInstanceMBeans() {
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        try {
            unregisterMBean(server, getRuleBaseObjectName());
            unregisterMBean(server, getRuleSessionObjectName());
        } catch (MalformedObjectNameException e) {
            logger.error("Error clearing instance MBeans", e);
        }

    }

    public int getNextEventCounter() {
        this.eventCounter++;
        return this.eventCounter;
    }

    public String getGuvnor_url() {
        return guvnor_url;
    }

    public String getGuvnor_appName() {
        return guvnor_appName;
    }

    public String getGuvnor_packageName() {
        return guvnor_packageName;
    }

    public String getGuvnor_packageVersion() {
        return guvnor_packageVersion;
    }

    public String getGuvnor_username() {
        return guvnor_username;
    }

    public String getGuvnor_password() {
        return guvnor_password;
    }

    public StatefulSessionSupervision getMbsSession() {
        return mbsSession;
    }

    public RuleBaseSupervision getMbsRuleBase() {
        return mbsRuleBase;
    }

    public JavaDialect getJavaDialect() {
        return javaDialect;
    }

    public void setJavaDialect(JavaDialect javaDialect) {
        this.javaDialect = javaDialect;
    }
}
