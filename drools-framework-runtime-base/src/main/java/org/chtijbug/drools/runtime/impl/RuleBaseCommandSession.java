package org.chtijbug.drools.runtime.impl;

import org.chtijbug.drools.entity.DroolsFactObject;
import org.chtijbug.drools.entity.DroolsRuleObject;
import org.chtijbug.drools.entity.history.HistoryContainer;
import org.chtijbug.drools.runtime.DroolsChtijbugException;
import org.chtijbug.drools.runtime.RuleBaseSession;
import org.kie.api.KieServices;
import org.kie.api.command.Command;
import org.kie.api.command.KieCommands;
import org.kie.api.runtime.ObjectFilter;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.api.runtime.process.WorkItemHandler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by nheron on 07/06/2016.
 */
public class RuleBaseCommandSession implements RuleBaseSession {

    private List<Command<?>> commands = new ArrayList<Command<?>>();
    private KieCommands commandsFactory = KieServices.Factory.get().getCommands();
    private int maxNumberRuleToExecute = 2000;

    public RuleBaseCommandSession(int maxNumberRuleToExecute) {
        this.maxNumberRuleToExecute = maxNumberRuleToExecute;
    }

    @Override
    public void insertObject(Object newObject) {
        commands.add(commandsFactory.newInsert(newObject, newObject.toString()));

    }

    @Override
    public void insertByReflection(Object newObject) throws DroolsChtijbugException {

    }

    @Override
    public void setGlobal(String identifier, Object value) {
        commands.add(commandsFactory.newSetGlobal(identifier, value));
    }

    @Override
    public void updateObject(Object updatedObject) {
        // Not Possible
    }

    @Override
    public void retractObject(Object oldObject) {
        // Not Possible
    }

    @Override
    public void fireAllRules() throws DroolsChtijbugException {
        commands.add(commandsFactory.newFireAllRules(maxNumberRuleToExecute));
    }

    @Override
    public Object fireAllRulesAndStartProcess(Object inputObject, String processName) throws DroolsChtijbugException {

        if (inputObject != null) {
            this.insertByReflection(inputObject);
        }
        if (processName != null && processName.length() > 0) {
            this.startProcess(processName);
        }
        this.fireAllRules();


        return inputObject;
    }

    @Override
    public Object fireAllRulesAndStartProcessWithParam(Object inputObject, String processName) throws DroolsChtijbugException {
        commands.add(commandsFactory.newFireAllRules(maxNumberRuleToExecute));
        return null;
    }

    @Override
    public void startProcess(String processName) {
        commands.add(commandsFactory.newStartProcess(processName));
    }

    @Override
    public void dispose() {

    }

    @Override
    public HistoryContainer getHistoryContainer() {
        return null;
    }

    @Override
    public String getHistoryContainerXML() {
        return null;
    }

    @Override
    public Collection<DroolsFactObject> listLastVersionObjects() {
        return null;
    }

    @Override
    public String listLastVersionObjectsXML() {
        return null;
    }

    @Override
    public Collection<DroolsRuleObject> listRules() {
        return null;
    }

    @Override
    public int getNumberRulesExecuted() {
        return 0;
    }

    @Override
    public Long getSessionId() {
        return null;
    }

    @Override
    public Long getRuleBaseID() {
        return null;
    }

    @Override
    public Collection<? extends Object> getObjects(ObjectFilter objectFilter) {
        return null;
    }

    @Override
    public void completeWorkItem(long processId, Map<String, Object> vars) {
        commands.add(commandsFactory.newCompleteWorkItem(processId, vars));
    }

    @Override
    public void abortWorkItem(long processId) {
        commands.add(commandsFactory.newAbortWorkItem(processId));
    }

    @Override
    public void registerWorkItemHandler(String workItemName, WorkItemHandler workItemHandler) {
        commands.add(commandsFactory.newRegisterWorkItemHandlerCommand(workItemHandler, workItemName));
    }

    @Override
    public ProcessInstance startProcess(String processName, Map<String, Object> vars) {
        commands.add(commandsFactory.newStartProcess(processName, vars));
        return null;
    }

    public List<Command<?>> getCommands() {
        return commands;
    }
}
