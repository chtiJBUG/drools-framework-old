/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chtijbug.drools.runtime.mbeans;

import org.chtijbug.drools.runtime.DroolsChtijbugException;
import org.chtijbug.drools.runtime.impl.RuleBaseSingleton;
import org.chtijbug.drools.runtime.resource.DroolsResource;

import javax.management.NotificationBroadcasterSupport;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nheron
 */

public class RuleBaseSupervision extends NotificationBroadcasterSupport implements RuleBaseSupervisionMBean{

    private RuleBaseSingleton ruleBaseSingleton;

    public RuleBaseSupervision(RuleBaseSingleton ruleBaseSingleton) {
        super();
        this.ruleBaseSingleton = ruleBaseSingleton;
        
    }
    
    
    @Override
    public List<String> getDroolsRessource() {
        List<String> ll = new ArrayList<String>();
        for (DroolsResource d : ruleBaseSingleton.getListDroolsRessources() ){
            ll.add(d.toString());
        }
        return ll;
    }

    @Override
    public boolean isKbaseLoaded() {
        return ruleBaseSingleton.isKbaseLoaded();
    }

    @Override
    public void reLoadRuleBase() throws DroolsChtijbugException{
        ruleBaseSingleton.createKBase();
    }

    @Override
    public int getRuleBaseID() {
        return this.ruleBaseSingleton.getRuleBaseID();
    }


}
