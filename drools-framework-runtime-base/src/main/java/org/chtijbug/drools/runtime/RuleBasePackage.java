/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chtijbug.drools.runtime;

import org.chtijbug.drools.runtime.listener.HistoryListener;
import org.chtijbug.drools.runtime.resource.DroolsResource;

/**
 *
 * @author nheron
 */
public interface RuleBasePackage {
    
	
    RuleBaseSession createRuleBaseSession()  throws DroolsChtijbugException;

    RuleBaseSession createRuleBaseSession(int maxNumberRulesToExecute)  throws DroolsChtijbugException;
    /**
     * 
     * Add a drools resource for generate kbase
     * 
     * @param res Drools resource
     */
	void addDroolsResouce(DroolsResource res);
    
	/**
	 * Create KnowledgeBase and load all drools resources 
	 */
	public void createKBase() throws DroolsChtijbugException;

    public HistoryListener getHistoryListener();

    void cleanup();

}
