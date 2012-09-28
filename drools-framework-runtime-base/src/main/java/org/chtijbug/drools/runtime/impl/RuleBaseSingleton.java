/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chtijbug.drools.runtime.impl;

import org.chtijbug.drools.entity.history.HistoryContainer;
import org.chtijbug.drools.runtime.RuleBasePackage;
import org.chtijbug.drools.runtime.RuleBaseSession;
import org.chtijbug.drools.runtime.mbeans.RuleBaseSupervision;
import org.chtijbug.drools.runtime.mbeans.StatefullSessionSupervision;
import org.chtijbug.drools.runtime.resource.DroolsResource;
import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 *
 * @author Bertrand Gressier
 */
public class RuleBaseSingleton implements RuleBasePackage {

    static final Logger LOGGER = LoggerFactory.getLogger(RuleBaseSingleton.class);
    private KnowledgeBase kbase = null;
    private final List<DroolsResource> listResouces;
    private RuleBaseSupervision mbsRuleBase;
    private StatefullSessionSupervision mbsSession;
    private int maxNumberRuleToExecute = 2000;
    private Semaphore lockKbase = new Semaphore(1);
    MBeanServer server=null;

    public RuleBaseSingleton() {
        listResouces = new ArrayList<DroolsResource>();
    }

    public RuleBaseSingleton(int maxNumberRulesToExecute) {
        listResouces = new ArrayList<DroolsResource>();
        this.maxNumberRuleToExecute = maxNumberRulesToExecute;
    }

    public boolean isKbaseLoaded() {
        if (kbase == null) {
            return false;
        };
        return true;
    }

    public List<DroolsResource> getListDroolsRessources() {
        return listResouces;
    }

    @Override
    public RuleBaseSession createRuleBaseSession() throws Exception {
        RuleBaseSession newRuleBaseSession = null;
        if (kbase != null) {

            lockKbase.acquire();
            StatefulKnowledgeSession newDroolsSession = kbase.newStatefulKnowledgeSession();
            newRuleBaseSession = new RuleBaseStatefullSession(newDroolsSession, maxNumberRuleToExecute, mbsSession);
            lockKbase.release();
        } else {
            throw new UnsupportedOperationException("Kbase not initialized");
        }
        return newRuleBaseSession;
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
    public synchronized void createKBase() {

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
            server = ManagementFactory.getPlatformMBeanServer();

            ObjectName nameRuleBase = new ObjectName(HistoryContainer.nameRuleBaseObjectName);
            ObjectName nameSession = new ObjectName(HistoryContainer.nameSessionObjectName);
            mbsRuleBase = new RuleBaseSupervision(this);
            mbsSession = new StatefullSessionSupervision();
            server.registerMBean(mbsRuleBase, nameRuleBase);
            server.registerMBean(mbsSession, nameSession);


        } catch (Exception e) {
            LOGGER.error("error to load Agent", e);
        }
    }
}
