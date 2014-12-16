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
import org.chtijbug.drools.entity.history.knowledge.*;
import org.chtijbug.drools.runtime.DroolsChtijbugException;
import org.chtijbug.drools.runtime.RuleBasePackage;
import org.chtijbug.drools.runtime.RuleBaseSession;
import org.chtijbug.drools.runtime.listener.HistoryListener;
import org.kie.api.KieServices;
import org.kie.api.builder.KieScanner;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
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
     * unique indentifier of the RuleBase in the JVM
     */
    protected static int ruleBaseCounter = 0;
    /**
     * Rule Base ID
     */
    private int ruleBaseID;
    private final KieServices kieServices;
    private KieContainer kContainer;
    private ReleaseId releaseId;
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
        this(null);
    }

    public RuleBaseSingleton(Integer givenRuleBaseID) throws DroolsChtijbugException {
        //____ Increment the ruleBaseID
        if (givenRuleBaseID != null) {
            this.ruleBaseID = givenRuleBaseID;
        } else {
            this.ruleBaseID = addRuleBase();
        }
        this.kieServices = KieServices.Factory.get();
    }

    public RuleBaseSingleton(Integer ruleBaseID, int maxNumberRulesToExecute) throws DroolsChtijbugException {
        this(ruleBaseID);
        this.maxNumberRuleToExecute = maxNumberRulesToExecute;
    }

    public RuleBaseSingleton(int maxNumberRulesToExecute) throws DroolsChtijbugException {
        this(null);
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

    public void setGuvnor_username(String guvnor_username) {
        this.guvnor_username = guvnor_username;
    }

    public void setGuvnor_password(String guvnor_password) {
        this.guvnor_password = guvnor_password;
    }

    public synchronized void createKBase(String packageName, String projectName, String version) throws DroolsChtijbugException {
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
            this.releaseId = kieServices.newReleaseId(packageName, projectName, version);
            lockKbase.acquire();
            this.kContainer = kieServices.newKieContainer(releaseId);
            KieScanner kScanner = kieServices.newKieScanner( kContainer );
            kScanner.start(1000L);
            lockKbase.release();
            if (this.historyListener != null) {
                // TODO change the following event...
                KnowledgeBaseAddRessourceEvent knowledgeBaseAddRessourceEvent = new KnowledgeBaseAddRessourceEvent(this.getNextEventCounter(), new Date(), this.ruleBaseID, this.guvnor_url, this.guvnor_appName, this.guvnor_packageName, this.guvnor_packageVersion);
                this.historyListener.fireEvent(knowledgeBaseAddRessourceEvent);
            }
        } catch (Exception e) {
            logger.error("error to load Agent", e);
            throw new DroolsChtijbugException(DroolsChtijbugException.ErrorToLoadAgent, "", e);
        }
    }

    @Override
    public void loadKBase(String version) throws DroolsChtijbugException {
        try {
            this.releaseId = kieServices.newReleaseId(this.releaseId.getGroupId(), this.releaseId.getArtifactId(), version);
            lockKbase.acquire();
            this.kContainer = kieServices.newKieContainer(releaseId);
            KieScanner kScanner = kieServices.newKieScanner( kContainer );
            kScanner.start(1000L);
            lockKbase.release();
            if (this.historyListener != null) {
                // TODO change the following event...
                KnowledgeBaseAddRessourceEvent knowledgeBaseAddRessourceEvent = new KnowledgeBaseAddRessourceEvent(this.getNextEventCounter(), new Date(), this.ruleBaseID, this.guvnor_url, this.guvnor_appName, this.guvnor_packageName, this.guvnor_packageVersion);
                this.historyListener.fireEvent(knowledgeBaseAddRessourceEvent);
            }
        } catch (InterruptedException e) {
         throw Throwables.propagate(e);
        }
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
