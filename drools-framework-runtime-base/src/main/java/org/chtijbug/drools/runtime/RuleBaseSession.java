/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chtijbug.drools.runtime;

import org.chtijbug.drools.entity.DroolsFactObject;
import org.chtijbug.drools.entity.DroolsRuleObject;
import org.chtijbug.drools.entity.history.HistoryContainer;

import java.util.Collection;

/**
 * @author nheron
 */
public interface RuleBaseSession {
    /**
     * This method injects the newObject parameter into this session.
     * No deep insertion(using relfection) is done
     * @param newObject
     */
    public void insertObject(Object newObject);

    /**
     * This method injects the newObject parameter into this session.
     * No deep insertion(using relfection) is done
     * @param newObject
     */
    public void insertByReflection(Object newObject) throws DroolsChtijbugException;

    /**
     * This method helps for introducing a global object into the RuleBaseSession
     * @param identifier
     * @param value
     */
    public void setGlobal(String identifier, Object value);

    public void updateObject(Object updatedObject);

    public void retractObject(Object oldObject);

    public void fireAllRules() throws DroolsChtijbugException;

    public void startProcess(String processName);

    public void dispose();

    public HistoryContainer getHistoryContainer();

    public String getHistoryContainerXML();

    public Collection<DroolsFactObject> listLastVersionObjects();

    public String listLastVersionObjectsXML();

    public Collection<DroolsRuleObject> listRules();

    public int getNumberRulesExecuted();

    public int getSessionId();

    public int getRuleBaseID() ;

}
