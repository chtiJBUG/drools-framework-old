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

    public void insertObject(Object newObject);

    public void updateObject(Object updatedObject);

    public void retractObject(Object oldObject);

    public void fireAllRules();

    public void startProcess(String processName);

    public void dispose();

    public HistoryContainer getHistoryContainer();

    public String getHistoryContainerXML();

    public Collection<DroolsFactObject> listLastVersionObjects();

    public String listLastVersionObjectsXML();

    public Collection<DroolsRuleObject> listRules();

}
