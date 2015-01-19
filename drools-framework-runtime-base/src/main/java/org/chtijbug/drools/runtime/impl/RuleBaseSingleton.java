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

import org.chtijbug.drools.entity.history.EventCounter;
import org.chtijbug.drools.entity.history.knowledge.*;
import org.chtijbug.drools.runtime.DroolsChtijbugException;
import org.chtijbug.drools.runtime.RuleBasePackage;
import org.chtijbug.drools.runtime.RuleBaseSession;
import org.chtijbug.drools.runtime.listener.HistoryListener;
import org.chtijbug.drools.runtime.resource.KnowledgeModule;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Semaphore;

import static com.google.common.base.Throwables.propagate;

/**
 * @author Bertrand Gressier
 */
public class RuleBaseSingleton implements RuleBasePackage {
    /** Class Logger */
    private static Logger logger = LoggerFactory.getLogger(RuleBaseSingleton.class);
    /** default rule threshold */
    public static int DEFAULT_RULE_THRESHOLD = 2000;
    /**  Rule Base ID */
    private Long ruleBaseID;
    private KieContainer kieContainer;
    private ReleaseId releaseId;
    private final KnowledgeModule knowledgeModule;
    /** Max rule to be fired threshold. */
    private int maxNumberRuleToExecute = DEFAULT_RULE_THRESHOLD;
    /** Semaphore used to void concurrent access to the singleton */
    private Semaphore lockKbase = new Semaphore(1);
    private int sessionCounter = 0;
    /**
     * Guvnor Connection
     */
    private String guvnor_url;
    private String guvnor_username;
    private String guvnor_password;
    /** Java Dialect */
    private JavaDialect javaDialect = JavaDialect.ECLIPSE;

    /** History Listener */
    private HistoryListener historyListener = null;
    /** unique ID of the RuleBase in the JVM */
    protected static EventCounter ruleBaseCounter = EventCounter.newCounter();
    /** unique ID of the RuleBase in the JVM */
    protected static EventCounter eventCounter = EventCounter.newCounter();



    public RuleBaseSingleton(int maxNumberRulesToExecute, HistoryListener historyListener, String modulePackage, String moduleName) throws DroolsChtijbugException {
        this.ruleBaseID = ruleBaseCounter.next();
        this.maxNumberRuleToExecute = maxNumberRulesToExecute;
        this.historyListener = historyListener;
        if (this.historyListener != null) {
            KnowledgeBaseCreatedEvent knowledgeBaseCreatedEvent = new KnowledgeBaseCreatedEvent(eventCounter.next(), new Date(), ruleBaseID);
            this.historyListener.fireEvent(knowledgeBaseCreatedEvent);
        }
        this.knowledgeModule = new KnowledgeModule(this.ruleBaseID, this.historyListener, modulePackage, moduleName, this.eventCounter);
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
            KieSession newDroolsSession = this.kieContainer.newKieSession();
            //_____ Increment session counter
            this.sessionCounter++;
            if (this.historyListener != null) {
                KnowledgeBaseCreateSessionEvent knowledgeBaseCreateSessionEvent = new KnowledgeBaseCreateSessionEvent(eventCounter.next(), new Date(), this.ruleBaseID);
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

    public synchronized void createKBase(String packageName, String projectName, String version) throws DroolsChtijbugException {
        if (kieContainer != null) {
            if (this.historyListener != null) {
                KnowledgeBaseReloadedEvent knowledgeBaseReloadLoadEvent = new KnowledgeBaseReloadedEvent(eventCounter.next(), new Date(), this.ruleBaseID, this.guvnor_url);
                this.historyListener.fireEvent(knowledgeBaseReloadLoadEvent);
            }
            // TODO dispose all elements
        } else {
            if (this.historyListener != null) {
                KnowledgeBaseInitialLoadEvent knowledgeBaseInitialLoadEvent = new KnowledgeBaseInitialLoadEvent(eventCounter.next(), new Date(), this.ruleBaseID, this.guvnor_url);
                this.historyListener.fireEvent(knowledgeBaseInitialLoadEvent);
            }
        }

        try {
            // TODO carrying on with refactoring
            //this.releaseId = kieServices.newReleaseId(packageName, projectName, version);
            //lockKbase.acquire();
            //this.kieContainer = kieServices.newKieContainer(releaseId);
            //lockKbase.release();
            //if (this.historyListener != null) {
                // TODO change the following event...
            //    KnowledgeBaseAddResourceEvent knowledgeBaseAddResourceEvent = new KnowledgeBaseAddResourceEvent(EventCounter.Next(), new Date(), this.ruleBaseID, this.guvnor_url);
            //    this.historyListener.fireEvent(knowledgeBaseAddResourceEvent);
            //}
        } catch (Exception e) {
            logger.error("error to load Agent", e);
            throw new DroolsChtijbugException(DroolsChtijbugException.ErrorToLoadAgent, "", e);
        }
    }

    @Override
    public void loadKBase(String version) throws DroolsChtijbugException {
        try {
            lockKbase.acquire();
            //this.releaseId = kieServices.newReleaseId(this.releaseId.getGroupId(), this.releaseId.getArtifactId(), version);
            kieContainer.updateToVersion(this.releaseId);
            lockKbase.release();
            if (this.historyListener != null) {
                // TODO change the following event...
                KnowledgeBaseAddResourceEvent knowledgeBaseAddResourceEvent = new KnowledgeBaseAddResourceEvent(eventCounter.next(), new Date(), this.ruleBaseID, this.guvnor_url);
                this.historyListener.fireEvent(knowledgeBaseAddResourceEvent);
            }
        } catch (InterruptedException e) {
            throw propagate(e);
        }
    }

    @Override
    public HistoryListener getHistoryListener() {
        return historyListener;
    }

    public Long getRuleBaseID() {
        return ruleBaseID;
    }

    @Override
    public void dispose() {
        if (this.historyListener != null) {
            KnowledgeBaseDisposeEvent knowledgeBaseDisposeEvent = new KnowledgeBaseDisposeEvent(eventCounter.next(), new Date(), this.ruleBaseID);
            try {
                this.historyListener.fireEvent(knowledgeBaseDisposeEvent);
            } catch (DroolsChtijbugException e) {
                throw propagate(e);
            }

        }
        this.kieContainer = null;
    }

    @Override
    public void createKBase(List<String> filenames) {
        try {
            if (this.historyListener != null) {
                KnowledgeBaseInitialLoadEvent knowledgeBaseInitialLoadEvent = new KnowledgeBaseInitialLoadEvent(eventCounter.next(), new Date(), this.ruleBaseID, this.guvnor_url);
                this.historyListener.fireEvent(knowledgeBaseInitialLoadEvent);
            }
            lockKbase.acquire();
            this.knowledgeModule.addAllFiles(filenames);
            kieContainer = this.knowledgeModule.build();
            lockKbase.release();
        } catch (InterruptedException | DroolsChtijbugException e) {
            propagate(e);
        }
    }

    public String getGuvnor_url() {
        return guvnor_url;
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
