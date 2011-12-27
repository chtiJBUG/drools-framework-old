/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chtijbug.drools.runtime.mbeans;

import java.util.List;

/**
 *
 * @author nheron
 */
public interface RuleBaseSupervisionMBean {
 
    public List<String> getDroolsRessource();
    public boolean isKbaseLoaded();
    public void reLoadRuleBase();
    
}
