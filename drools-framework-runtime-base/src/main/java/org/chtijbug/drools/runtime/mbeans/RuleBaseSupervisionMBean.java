/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chtijbug.drools.runtime.mbeans;

import java.util.List;
import org.chtijbug.drools.runtime.resource.DroolsResource;

/**
 *
 * @author nheron
 */
public interface RuleBaseSupervisionMBean {
 
    public List<DroolsResource> getDroolsRessource();
    public boolean isKbaseLoaded();
    public void reLoadRuleBase();
    
}
