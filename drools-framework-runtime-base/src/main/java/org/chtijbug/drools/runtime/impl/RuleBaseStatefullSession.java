/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chtijbug.drools.runtime.impl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
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
	private final Map<FactHandle, Object> listObject = new HashMap<FactHandle, Object>();
	private final Map<Object, FactHandle> listFact = new HashMap<Object, FactHandle>();
	private final Map<Object, List<FactObject>> listFactObjects = new HashMap<Object, List<FactObject>>();
	private FactHandlerListener factListener;
	private final RuleHandlerListener runHandlerListener;
	private final HistoryContainer historyContainer = new HistoryContainer();

	public RuleBaseStatefullSession(StatefulKnowledgeSession knowledgeSession) {
		this.knowledgeSession = knowledgeSession;

		factListener = new FactHandlerListener(this);
		runHandlerListener = new RuleHandlerListener(this);

		knowledgeSession.addEventListener(factListener);
		knowledgeSession.addEventListener(runHandlerListener);

	}

	public FactObject getLastFactObjectVersion(Object searchO) {
		int lastVersion = listFactObjects.get(searchO).size() - 1;
		return getFactObjectVersion(searchO, lastVersion);
	}

	public FactObject getFactObjectVersion(Object search0, int version) {
		return listFactObjects.get(search0).get(version);
	}

	public FactObject getLastFactObjectVersionFromFactHandle(FactHandle factToFind) {

		Object searchObject = this.listObject.get(factToFind);
		if (searchObject == null) {
			return null;
		}
		int lastVersion = listFactObjects.get(factToFind).size() - 1;
		return listFactObjects.get(searchObject).get(lastVersion);
	}

	public FactObject getFactObjectVersionFromFactHandle(FactHandle factToFind, int version) {
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

	public void setData(FactHandle f, Object o, FactObject fObject) {
		if (listObject.containsKey(f) == true) {
			listFact.remove(listObject.get(f));
		}
		listObject.put(f, o);
		listFact.put(o, f);
		if (listFactObjects.containsKey(o) == false) {
			List<FactObject> newList = new LinkedList<FactObject>();
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
