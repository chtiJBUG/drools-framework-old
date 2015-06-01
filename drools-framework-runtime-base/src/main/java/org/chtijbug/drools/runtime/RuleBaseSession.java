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
package org.chtijbug.drools.runtime;

import org.chtijbug.drools.entity.DroolsFactObject;
import org.chtijbug.drools.entity.DroolsRuleObject;
import org.chtijbug.drools.entity.history.HistoryContainer;
import org.drools.ObjectFilter;
import org.drools.runtime.process.ProcessInstance;
import org.drools.runtime.process.WorkItemHandler;

import java.util.Collection;
import java.util.Map;

/**
 * @author nheron
 */
public interface RuleBaseSession {
    /**
     * This method injects the newObject parameter into this session.
     * No deep insertion(using relfection) is done
     *
     * @param newObject
     */
    public void insertObject(Object newObject);

    /**
     * This method injects the newObject parameter into this session.
     * No deep insertion(using relfection) is done
     *
     * @param newObject
     */
    public void insertByReflection(Object newObject) throws DroolsChtijbugException;

    /**
     * This method helps for introducing a global object into the RuleBaseSession
     *
     * @param identifier
     * @param value
     */
    public void setGlobal(String identifier, Object value);

    public void updateObject(Object updatedObject);

    public void retractObject(Object oldObject);

    public void fireAllRules() throws DroolsChtijbugException;

    public Object fireAllRulesAndStartProcess(Object inputObject, String processName) throws DroolsChtijbugException;

    public Object fireAllRulesAndStartProcessWithParam(Object inputObject, String processName) throws DroolsChtijbugException;

    public void startProcess(String processName);

    public void dispose();

    public HistoryContainer getHistoryContainer();

    public String getHistoryContainerXML();

    public Collection<DroolsFactObject> listLastVersionObjects();

    public String listLastVersionObjectsXML();

    public Collection<DroolsRuleObject> listRules();

    public int getNumberRulesExecuted();

    public int getSessionId();

    public int getRuleBaseID();

    public Collection<Object> getObjects(ObjectFilter objectFilter);

    void completeWorkItem(long processId, Map<String, Object> vars);

    void abortWorkItem(long processId);

    void registerWorkItemHandler(String workItemName, WorkItemHandler workItemHandler);

    public ProcessInstance startProcess(String processName, Map<String, Object> vars);
    public boolean isDisableJsonObjecttext() ;

    public void setDisableJsonObjecttext(boolean disableJsonObjecttext);


    public boolean isDisableFactHandlerListener();

    public void setDisableFactHandlerListener(boolean disableFactHandlerListener) ;

}
