/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chtijbug.drools.runtime.mbeans;

import java.util.List;
import javax.management.NotificationBroadcasterSupport;
import org.chtijbug.drools.runtime.impl.RuleBaseSingleton;
import org.chtijbug.drools.runtime.resource.DroolsResource;

/**
 *
 * @author nheron
 */

public class RuleBaseSupervision extends NotificationBroadcasterSupport implements RuleBaseSupervisionMBean{

    private RuleBaseSingleton ruleBaseSession;

    public RuleBaseSupervision(RuleBaseSingleton ruleBaseSession) {
        super();
        this.ruleBaseSession = ruleBaseSession;
        
    }
    
    
    @Override
    public List<DroolsResource> getDroolsRessource() {
        return ruleBaseSession.getListDroolsRessources();
    }

    @Override
    public boolean isKbaseLoaded() {
        return ruleBaseSession.isKbaseLoaded();
    }

    @Override
    public void reLoadRuleBase() {
        ruleBaseSession.createKBase();
    }

    

    
}
