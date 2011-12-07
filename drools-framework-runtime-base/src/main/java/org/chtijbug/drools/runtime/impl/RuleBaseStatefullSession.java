/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chtijbug.drools.runtime.impl;

import java.util.HashMap;
import java.util.Map;
import org.chtijbug.drools.entity.history.FactObject;
import org.chtijbug.drools.entity.history.HistoryContainer;
import org.chtijbug.drools.runtime.RuleBaseSession;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.rule.FactHandle;

/**
 *
 * @author nheron
 */
public class RuleBaseStatefullSession implements RuleBaseSession {

    private StatefulKnowledgeSession knowledgeSession = null;
    private Map<FactHandle, Object> listObject = new HashMap<FactHandle, Object>();
    private Map<Object, FactHandle> listFact = new HashMap<Object, FactHandle>();
    private Map<Object, FactObject> listFactObjects = new HashMap<Object, FactObject>();
    private FactHandlerListener factListener;
    private HistoryContainer historyContainer = new HistoryContainer();

    public RuleBaseStatefullSession(StatefulKnowledgeSession knowledgeSession) {
        this.knowledgeSession = knowledgeSession;
        factListener = new FactHandlerListener(this);
        knowledgeSession.addEventListener(factListener);

    }
    public FactObject getFactObject(Object searchO){
        return listFactObjects.get(searchO);
    }
    public HistoryContainer getHistoryContainer() {
        return historyContainer;
    }

    public StatefulKnowledgeSession getKnowledgeSession() {
        return knowledgeSession;
    }

    public void setData(FactHandle f, Object o,FactObject fObject) {
        listObject.put(f, o);
        listFact.put(o, f);
        listFactObjects.put(o, fObject);
    }

    public void unsetData(FactHandle f, Object o) {
        listObject.remove(f);
        listFact.remove(o);
    }

    @Override
    public void dispose() {

        knowledgeSession.removeEventListener(factListener);
        //knowledgeSession.removeEventListener(aFiredRulesListener);
        //knowledgeSession.removeEventListener(processHandler);
        for (FactHandle f : listObject.keySet()) {
            knowledgeSession.retract(f);
        }

        //aFiredRulesListener.dispose();
        //aFiredRulesListener.dispose();
        //aFiredRulesListener = null;
        //processHandler.dispose();
        //processHandler = null;
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
