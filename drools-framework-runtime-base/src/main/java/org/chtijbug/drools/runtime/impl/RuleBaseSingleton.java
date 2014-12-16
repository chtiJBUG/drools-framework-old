/*
 * Copyright 2014 Pymma Software
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.chtijbug.drools.runtime.impl;

import com.google.common.base.Throwables;
import org.chtijbug.drools.entity.history.HistoryContainer;
import org.chtijbug.drools.entity.history.knowledge.*;
import org.chtijbug.drools.runtime.DroolsChtijbugException;
import org.chtijbug.drools.runtime.RuleBasePackage;
import org.chtijbug.drools.runtime.RuleBaseSession;
import org.chtijbug.drools.runtime.listener.HistoryListener;
import org.chtijbug.drools.runtime.resource.Bpmn2DroolsResource;
import org.chtijbug.drools.runtime.resource.DrlDroolsResource;
import org.chtijbug.drools.runtime.resource.DroolsResource;
import org.chtijbug.drools.runtime.resource.GuvnorDroolsResource;
import org.kie.api.KieServices;
import org.kie.api.builder.KieScanner;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private KieContainer kContainer;
    /**
     * All Drools resources inserted into the RuleBase
     */
    private final List<DroolsResource> listResouces = new ArrayList<DroolsResource>();
    /**
     * Max rule to be fired threshold.
     */
    private int maxNumberRuleToExecute = DEFAULT_RULE_THRESHOLD;
    /**
     * Semaphore used to void concurrent access to the singleton
     */
    private Semaphore lockKbase = new Semaphore(1);
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
     * Java Dialect
     */
    private JavaDialect javaDialect = JavaDialect.ECLIPSE;
    /**
     * History Listener
     */
    private HistoryListener historyListener = null;

    private int eventCounter;

    public RuleBaseSingleton() throws DroolsChtijbugException {
        this((Integer) null);
    }

    public RuleBaseSingleton(Integer givenRuleBaseID) throws DroolsChtijbugException {
        //____ Increment the ruleBaseID
        if (givenRuleBaseID != null) {
            this.ruleBaseID = givenRuleBaseID;
        } else {
            this.ruleBaseID = addRuleBase();
        }
    }

    public RuleBaseSingleton(Integer ruleBaseID, int maxNumberRulesToExecute) throws DroolsChtijbugException {
        this(ruleBaseID);
        this.maxNumberRuleToExecute = maxNumberRulesToExecute;
    }

    public RuleBaseSingleton(int maxNumberRulesToExecute) throws DroolsChtijbugException {
        this((Integer) null);
        this.maxNumberRuleToExecute = maxNumberRulesToExecute;
    }

    public RuleBaseSingleton(Integer ruleBaseID, int maxNumberRulesToExecute, HistoryListener historyListener) throws DroolsChtijbugException {
        this(ruleBaseID, maxNumberRulesToExecute);
        this.historyListener = historyListener;
        if (this.historyListener != null) {
            KnowledgeBaseCreatedEvent knowledgeBaseCreatedEvent = new KnowledgeBaseCreatedEvent(this.getNextEventCounter(), new Date(), ruleBaseCounter);
            this.historyListener.fireEvent(knowledgeBaseCreatedEvent);
        }
    }

    public RuleBaseSingleton(int maxNumberRulesToExecute, HistoryListener historyListener) throws DroolsChtijbugException {
        this(maxNumberRulesToExecute);
        this.historyListener = historyListener;
        if (this.historyListener != null) {
            KnowledgeBaseCreatedEvent knowledgeBaseCreatedEvent = new KnowledgeBaseCreatedEvent(this.getNextEventCounter(), new Date(), ruleBaseCounter);
            this.historyListener.fireEvent(knowledgeBaseCreatedEvent);
        }
    }

    public RuleBaseSingleton(Integer ruleBaseID, HistoryListener historyListener) throws DroolsChtijbugException {
        this(ruleBaseID);
        this.historyListener = historyListener;
        if (this.historyListener != null) {
            KnowledgeBaseCreatedEvent knowledgeBaseCreatedEvent = new KnowledgeBaseCreatedEvent(this.getNextEventCounter(), new Date(), ruleBaseCounter);
            this.historyListener.fireEvent(knowledgeBaseCreatedEvent);
        }
    }

    public RuleBaseSingleton(HistoryListener historyListener) throws DroolsChtijbugException {
        this((Integer) null);
        this.historyListener = historyListener;
        if (this.historyListener != null) {
            KnowledgeBaseCreatedEvent knowledgeBaseCreatedEvent = new KnowledgeBaseCreatedEvent(this.getNextEventCounter(), new Date(), ruleBaseCounter);
            this.historyListener.fireEvent(knowledgeBaseCreatedEvent);
        }
    }

    public List<DroolsResource> getListDroolsRessources() {
        return listResouces;
    }

    @Override
    public RuleBaseSession createRuleBaseSession() throws DroolsChtijbugException {
        logger.debug(">>createRuleBaseSession");
        try {
            //____ Creating new Rule Base Session using default rule threshold
            return this.createRuleBaseSession(this.maxNumberRuleToExecute);
        } finally {
            logger.debug("<<createRuleBaseSession");
        }
    }

    @Override
    public RuleBaseSession createRuleBaseSession(int maxNumberRulesToExecute) throws DroolsChtijbugException {
        logger.debug(">>createRuleBaseSession", maxNumberRulesToExecute);
        RuleBaseSession newRuleBaseSession = null;
        try {
                //____ Acquire semaphore
                try {
                    lockKbase.acquire();
                } catch (Exception e) {
                    throw new DroolsChtijbugException(DroolsChtijbugException.KbaseAcquire, "", e);
                }
                //_____ Now we can create a new stateful session using KnowledgeBase
                KieSession newDroolsSession = this.kContainer.newKieSession();
                //_____ Increment session counter
                this.sessionCounter++;
                if (this.historyListener != null) {
                    KnowledgeBaseCreateSessionEvent knowledgeBaseCreateSessionEvent = new KnowledgeBaseCreateSessionEvent(this.getNextEventCounter(), new Date(), this.ruleBaseID);
                    knowledgeBaseCreateSessionEvent.setSessionId(this.sessionCounter);
                    this.historyListener.fireEvent(knowledgeBaseCreateSessionEvent);
                }

                //_____ Wrapping the knowledge Session
                newRuleBaseSession = new RuleBaseStatefulSession(this.ruleBaseID, this.sessionCounter, newDroolsSession, maxNumberRulesToExecute, this.historyListener);
                //_____ Release semaphore
                lockKbase.release();
            //____ return the wrapped KnowledgeSession
            return newRuleBaseSession;
        } finally {
            logger.debug("<<createRuleBaseSession", newRuleBaseSession);
        }
    }


    private void addDroolsResource(DroolsResource res) throws DroolsChtijbugException {
        if (this.listResouces.contains(res)) {
            throw new DroolsChtijbugException(DroolsChtijbugException.RessourceAlreadyAdded, res.toString(), null);
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

        } else if (res instanceof DrlDroolsResource) {
            DrlDroolsResource drlDroolsResource = (DrlDroolsResource) res;
            if (this.historyListener != null) {
                KnowledgeBaseAddRessourceEvent knowledgeBaseAddRessourceEvent = new KnowledgeBaseAddRessourceEvent(this.getNextEventCounter(), new Date(), this.ruleBaseID, drlDroolsResource.getFileName(), drlDroolsResource.getFileContent());
                this.historyListener.fireEvent(knowledgeBaseAddRessourceEvent);
            }

        } else if (res instanceof Bpmn2DroolsResource) {
            Bpmn2DroolsResource bpmn2DroolsResource = (Bpmn2DroolsResource) res;
            if (this.historyListener != null) {
                KnowledgeBaseAddRessourceEvent knowledgeBaseAddRessourceEvent = new KnowledgeBaseAddRessourceEvent(this.getNextEventCounter(), new Date(), this.ruleBaseID, bpmn2DroolsResource.getFileName(), bpmn2DroolsResource.getFileContent());
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
        if (!this.listResouces.contains(res)) {
            throw new DroolsChtijbugException(DroolsChtijbugException.RessourceDoesNotExist, res.toString(), null);
        }
        if (this.historyListener != null) {
            if (res instanceof GuvnorDroolsResource) {
                KnowledgeBaseDelResourceEvent knowledgeBaseDelResourceEvent = new KnowledgeBaseDelResourceEvent(this.getNextEventCounter(), new Date(), this.ruleBaseID, this.guvnor_url, this.guvnor_appName, this.guvnor_packageName, this.guvnor_packageVersion);
                this.historyListener.fireEvent(knowledgeBaseDelResourceEvent);
            } else if (res instanceof DrlDroolsResource) {
                DrlDroolsResource drlDroolsResource = (DrlDroolsResource) res;
                KnowledgeBaseDelResourceEvent knowledgeBaseDelResourceEvent = new KnowledgeBaseDelResourceEvent(this.getNextEventCounter(), new Date(), this.ruleBaseID, drlDroolsResource.getFileName(), drlDroolsResource.getFileContent());
                this.historyListener.fireEvent(knowledgeBaseDelResourceEvent);

            } else if (res instanceof Bpmn2DroolsResource) {
                Bpmn2DroolsResource bpmn2DroolsResource = (Bpmn2DroolsResource) res;
                KnowledgeBaseDelResourceEvent knowledgeBaseDelResourceEvent = new KnowledgeBaseDelResourceEvent(this.getNextEventCounter(), new Date(), this.ruleBaseID, bpmn2DroolsResource.getFileName(), bpmn2DroolsResource.getFileContent());
                this.historyListener.fireEvent(knowledgeBaseDelResourceEvent);
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
        if (kContainer != null) {
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
            KieServices kieServices = KieServices.Factory.get();
            ReleaseId releaseId = kieServices.newReleaseId("com.pymmasoftware.drools", "hello-world", "4.0");
            this.kContainer = kieServices.newKieContainer(releaseId);
            KieScanner kScanner = kieServices.newKieScanner( kContainer );
            kScanner.start(1000L);
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
        this.kContainer = null;
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

    public JavaDialect getJavaDialect() {
        return javaDialect;
    }

    public void setJavaDialect(JavaDialect javaDialect) {
        this.javaDialect = javaDialect;
    }
}
