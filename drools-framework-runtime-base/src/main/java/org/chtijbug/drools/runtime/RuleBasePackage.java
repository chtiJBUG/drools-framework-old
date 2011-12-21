/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chtijbug.drools.runtime;

import org.chtijbug.drools.runtime.resource.DroolsResource;

/**
 *
 * @author nheron
 */
public interface RuleBasePackage {
    
	
	/**
	 * 
	 * @return 
	 */
    public RuleBaseSession createRuleBaseSession()  throws Exception;

    /**
     * 
     * Add a drools resource for generate kbase
     * 
     * @param res Drools resource
     */
	public void addDroolsResouce(DroolsResource res);
    
	/**
	 * Create KnowledgeBase and load all drools resources 
	 */
	public void createKBase();
    
}
