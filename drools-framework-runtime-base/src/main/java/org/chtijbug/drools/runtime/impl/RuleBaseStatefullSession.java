/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chtijbug.drools.runtime.impl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.chtijbug.drools.entity.DroolsFactObject;
import org.chtijbug.drools.entity.DroolsNodeInstanceObject;
import org.chtijbug.drools.entity.DroolsNodeObject;
import org.chtijbug.drools.entity.DroolsProcessInstanceObject;
import org.chtijbug.drools.entity.DroolsProcessObject;
import org.chtijbug.drools.entity.history.HistoryContainer;
import org.chtijbug.drools.runtime.RuleBaseSession;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.rule.FactHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author nheron
 */
public class RuleBaseStatefullSession implements RuleBaseSession {

    private static Logger LOGGER = LoggerFactory.getLogger(RuleBaseStatefullSession.class);
    private StatefulKnowledgeSession knowledgeSession = null;
    private final Map<FactHandle, Object> listObject = new HashMap<FactHandle, Object>();
    private final Map<Object, FactHandle> listFact = new HashMap<Object, FactHandle>();
    private final Map<Object, List<DroolsFactObject>> listFactObjects = new HashMap<Object, List<DroolsFactObject>>();
    private FactHandlerListener factListener;
    private final RuleHandlerListener runHandlerListener;
    private final HistoryContainer historyContainer = new HistoryContainer();
    private final Map<DroolsProcessObject, List<DroolsNodeObject>> processDefinitions = new HashMap<DroolsProcessObject, List<DroolsNodeObject>>();
    private final Map<DroolsProcessInstanceObject, List<DroolsNodeInstanceObject>> processInstances = new HashMap<DroolsProcessInstanceObject, List<DroolsNodeInstanceObject>>();

    public RuleBaseStatefullSession(StatefulKnowledgeSession knowledgeSession) {
        this.knowledgeSession = knowledgeSession;

        factListener = new FactHandlerListener(this);
        runHandlerListener = new RuleHandlerListener(this);

        knowledgeSession.addEventListener(factListener);
        knowledgeSession.addEventListener(runHandlerListener);

    }

    public DroolsFactObject getLastFactObjectVersion(Object searchO) {
        int lastVersion = listFactObjects.get(searchO).size() - 1;
        return getFactObjectVersion(searchO, lastVersion);
    }

    public DroolsFactObject getFactObjectVersion(Object search0, int version) {
        return listFactObjects.get(search0).get(version);
    }

    public DroolsFactObject getLastFactObjectVersionFromFactHandle(FactHandle factToFind) {

        Object searchObject = this.listObject.get(factToFind);
        if (searchObject == null) {
            return null;
        }

        List<DroolsFactObject> facto = listFactObjects.get(searchObject);

        if (facto == null) {
            LOGGER.error("List of FactObject can not be null for FactHandle {}", factToFind);
            return null;
        }

        int lastVersion = facto.size() - 1;
        return listFactObjects.get(searchObject).get(lastVersion);
    }

    public DroolsFactObject getFactObjectVersionFromFactHandle(FactHandle factToFind, int version) {
        Object searchObject = this.listObject.get(factToFind);
        if (searchObject == null) {
            return null;
        }
        return listFactObjects.get(searchObject).get(version);
    }

    @Override
    public HistoryContainer getHistoryContainer() {
        return historyContainer;
    }

    public StatefulKnowledgeSession getKnowledgeSession() {
        return knowledgeSession;
    }

    public void setData(FactHandle f, Object o, DroolsFactObject fObject) {

        Object objectSearch = listObject.containsKey(f);
        if (objectSearch != null) {
            listFact.remove(objectSearch);
        }

        listObject.put(f, o);
        listFact.put(o, f);

        if (listFactObjects.containsKey(o) == false) {
            List<DroolsFactObject> newList = new LinkedList<DroolsFactObject>();
            newList.add(fObject);
            listFactObjects.put(o, newList);
        } else {
            listFactObjects.get(o).add(fObject);
        }
    }

    public void unsetData(FactHandle f, Object o) {
        listObject.remove(f);
        listFact.remove(o);
    }

    @Override
    public void dispose() {

        knowledgeSession.removeEventListener(factListener);
        knowledgeSession.removeEventListener(runHandlerListener);

        // knowledgeSession.removeEventListener(aFiredRulesListener);
        // knowledgeSession.removeEventListener(processHandler);
        for (FactHandle f : listObject.keySet()) {
            knowledgeSession.retract(f);
        }
        // aFiredRulesListener.dispose();
        // aFiredRulesListener.dispose();
        // aFiredRulesListener = null;
        // processHandler.dispose();
        // processHandler = null;
        factListener.dispose();
        factListener = null;
        knowledgeSession.dispose();
        knowledgeSession = null;

    }

    @Override
    public void insertObject(Object newObject) {
        this.knowledgeSession.insert(newObject);
    }

    @Override
    public void updateObject(Object updatedObject) {
        FactHandle factToUpdate = listFact.get(updatedObject);
        this.knowledgeSession.update(factToUpdate, updatedObject);
    }

    @Override
    public void retractObject(Object oldObject) {
        FactHandle factToDelete = listFact.get(oldObject);
        this.knowledgeSession.retract(factToDelete);
    }

    @Override
    public void fireAllRules() {
        this.knowledgeSession.fireAllRules();
    }

    @Override
    public void startProcess(String processName) {
        this.knowledgeSession.startProcess(processName);
    }
}
