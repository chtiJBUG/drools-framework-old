package org.chtijbug.drools.runtime.impl;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import org.chtijbug.drools.entity.*;
import org.chtijbug.drools.entity.history.HistoryContainer;
import org.chtijbug.drools.runtime.DroolsChtijbugException;
import org.chtijbug.drools.runtime.RuleBaseSession;
import org.chtijbug.drools.runtime.mbeans.StatefullSessionSupervision;
import org.drools.definition.rule.Rule;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.process.NodeInstance;
import org.drools.runtime.process.ProcessInstance;
import org.drools.runtime.rule.FactHandle;
import org.jbpm.workflow.instance.node.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @author nheron
 */
public class RuleBaseStatefullSession implements RuleBaseSession {

    private static Logger LOGGER = LoggerFactory.getLogger(RuleBaseStatefullSession.class);
    private StatefulKnowledgeSession knowledgeSession = null;
    private final Map<FactHandle, Object> listObject;
    private final Map<Object, FactHandle> listFact;
    private final Map<Object, List<DroolsFactObject>> listFactObjects;
    private final HistoryContainer historyContainer;
    private final Map<String, DroolsRuleObject> listRules;
    private final Map<String, DroolsProcessObject> processList;
    private final Map<String, DroolsProcessInstanceObject> processInstanceList;
    // Listeners can be dispose ...
    private FactHandlerListener factListener;
    private RuleHandlerListener ruleHandlerListener;
    private ProcessHandlerListener processHandlerListener;
    private int maxNumberRuleToExecute;
    private StatefullSessionSupervision mbeanStatefulleSessionSupervision;

    private XStream xstream = new XStream(new JettisonMappedXmlDriver());

    public RuleBaseStatefullSession(StatefulKnowledgeSession knowledgeSession, int maxNumberRuleToExecute, StatefullSessionSupervision mbeanStatefulleSessionSupervision) {
        this.knowledgeSession = knowledgeSession;
        this.maxNumberRuleToExecute = maxNumberRuleToExecute;
        this.factListener = new FactHandlerListener(this);
        this.ruleHandlerListener = new RuleHandlerListener(this);
        this.processHandlerListener = new ProcessHandlerListener(this);
        this.historyContainer = new HistoryContainer();
        this.listFactObjects = new HashMap<Object, List<DroolsFactObject>>();
        this.listFact = new HashMap<Object, FactHandle>();
        this.listObject = new HashMap<FactHandle, Object>();
        this.listRules = new HashMap<String, DroolsRuleObject>();
        this.processList = new HashMap<String, DroolsProcessObject>();
        this.processInstanceList = new HashMap<String, DroolsProcessInstanceObject>();
        this.mbeanStatefulleSessionSupervision = mbeanStatefulleSessionSupervision;
        knowledgeSession.addEventListener(factListener);
        knowledgeSession.addEventListener(ruleHandlerListener);
        knowledgeSession.addEventListener(processHandlerListener);
    }

    public int getMaxNumberRuleToExecute() {
        return maxNumberRuleToExecute;
    }

    public void setMaxNumberRuleToExecute(int maxNumberRuleToExecute) {
        this.maxNumberRuleToExecute = maxNumberRuleToExecute;
    }

    public DroolsProcessInstanceObject getDroolsProcessInstanceObject(ProcessInstance processInstance) {

        DroolsProcessInstanceObject droolsProcessInstanceObject = processInstanceList.get(processInstance.getId());
        if (droolsProcessInstanceObject == null) {
            DroolsProcessObject droolsProcessObject = processList.get(processInstance.getProcess().getId());

            if (droolsProcessObject == null) {
                droolsProcessObject = DroolsProcessObject.createDroolsProcessObject(processInstance.getProcess().getId(),
                        processInstance.getProcessId(),
                        processInstance.getProcess().getPackageName(), processInstance.getProcess().getType(), processInstance.getProcess().getVersion());
                processList.put(processInstance.getProcess().getId(), droolsProcessObject);
            }

            droolsProcessInstanceObject = DroolsProcessInstanceObject.createDroolsProcessInstanceObject(String.valueOf(processInstance.getId()), droolsProcessObject);
            processInstanceList.put(droolsProcessInstanceObject.getId(), droolsProcessInstanceObject);
        }
        return droolsProcessInstanceObject;
    }

    public DroolsNodeInstanceObject getDroolsNodeInstanceObject(NodeInstance nodeInstance) {
        String nodeType = "UNKNOWN";
        if (nodeInstance instanceof StartNodeInstance) {
            nodeType = StartNodeInstance.class.getCanonicalName();
        } else if (nodeInstance instanceof RuleSetNodeInstance) {
            RuleSetNodeInstance aRuleSet = (RuleSetNodeInstance) nodeInstance;
            //String ruleFlowName = aRuleSet.getRuleSetEventType();
            nodeType = RuleSetNodeInstance.class.getCanonicalName();
        } else if (nodeInstance instanceof SplitInstance) {
            nodeType = SplitInstance.class.getCanonicalName();
        } else if (nodeInstance instanceof JoinInstance) {
            nodeType = JoinInstance.class.getCanonicalName();
        } else if (nodeInstance instanceof EndNodeInstance) {
            nodeType = EndNodeInstance.class.getCanonicalName();
        }
        DroolsProcessInstanceObject droolsProcessInstanceObject = processInstanceList.get(nodeInstance.getProcessInstance().getId());
        if (droolsProcessInstanceObject == null) {
            droolsProcessInstanceObject = this.getDroolsProcessInstanceObject(nodeInstance.getProcessInstance());
        }

        DroolsNodeInstanceObject droolsNodeInstanceObject = droolsProcessInstanceObject.getDroolsNodeInstanceObjet(String.valueOf(nodeInstance.getId()));
        if (droolsNodeInstanceObject == null) {
            DroolsNodeObject droolsNodeObject = DroolsNodeObject.createDroolsNodeObject(String.valueOf(nodeInstance.getNode().getId()), nodeType);
            droolsProcessInstanceObject.getProcess().addDroolsNodeObject(droolsNodeObject);
            droolsNodeInstanceObject = DroolsNodeInstanceObject.createDroolsNodeInstanceObject(String.valueOf(nodeInstance.getId()), droolsNodeObject);
            droolsProcessInstanceObject.addDroolsNodeInstanceObject(droolsNodeInstanceObject);
        }


        return droolsNodeInstanceObject;
    }

    public DroolsRuleObject getDroolsRuleObject(Rule rule) {
        DroolsRuleObject droolsRuleObject = listRules.get(rule);

        if (droolsRuleObject == null) {
            droolsRuleObject = DroolsRuleObject.createDroolRuleObject(rule.getName(), rule.getPackageName());
            addDroolsRuleObject(droolsRuleObject);
        }

        return droolsRuleObject;
    }

    public void addDroolsRuleObject(DroolsRuleObject droolsRuleObject) {
        listRules.put(droolsRuleObject.getRulePackageName() + droolsRuleObject.getRuleName(), droolsRuleObject);
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

    @Override
    public String getHistoryContainerXML() {
        String result = null;
        if (historyContainer != null) {
            xstream.setMode(XStream.NO_REFERENCES);
            result = xstream.toXML(historyContainer);
        }
        return result;
    }

    @Override
    public Collection<DroolsFactObject> listLastVersionObjects() {
        Collection<DroolsFactObject> list = new ArrayList<DroolsFactObject>();
        for (Object o : this.listFact.keySet()) {
            FactHandle factHandle = this.listFact.get(o);
            List<DroolsFactObject> versionList = this.listFactObjects.get(o);
            list.add(this.getLastFactObjectVersionFromFactHandle(factHandle));

        }
        return list;
    }

    @Override
    public String listLastVersionObjectsXML() {
        String result = null;
        Collection<DroolsFactObject> list = this.listLastVersionObjects();
        if (list != null) {
            xstream.setMode(XStream.NO_REFERENCES);
            result = xstream.toXML(list);
        }
        return result;
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
        knowledgeSession.removeEventListener(ruleHandlerListener);
        knowledgeSession.removeEventListener(processHandlerListener);
        for (FactHandle f : listObject.keySet()) {
            knowledgeSession.retract(f);
        }

        factListener.dispose();
        factListener = null;
        ruleHandlerListener = null;
        processHandlerListener = null;
        knowledgeSession.dispose();
        knowledgeSession = null;

    }

    @Override
    public void insertObject(Object newObject) {
        FactHandle newFactHandle = this.knowledgeSession.insert(newObject);
        listFact.put(newObject,newFactHandle);
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
    public void fireAllRules() throws DroolsChtijbugException {
        long startTime = System.currentTimeMillis();
        long beforeNumberRules = ruleHandlerListener.getNbRuleFired();
        try{
        this.knowledgeSession.fireAllRules();
        }catch (Exception e){
            DroolsChtijbugException droolsChtijbugException = new DroolsChtijbugException("fireAllRules","",e);
            throw droolsChtijbugException;
        }

        long stopTime = System.currentTimeMillis();
        long afterNumberRules = ruleHandlerListener.getNbRuleFired();
        mbeanStatefulleSessionSupervision.fireAllRulesExecuted(stopTime - startTime, afterNumberRules - beforeNumberRules, historyContainer);
    }

    @Override
    public void startProcess(String processName) {
        this.knowledgeSession.startProcess(processName);
    }

    @Override
    public Collection<DroolsRuleObject> listRules() {
        return listRules.values();
    }
}
