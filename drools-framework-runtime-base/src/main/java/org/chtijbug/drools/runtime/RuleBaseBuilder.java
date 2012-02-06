/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chtijbug.drools.runtime;

import org.chtijbug.drools.runtime.impl.RuleBaseSingleton;
import org.chtijbug.drools.runtime.resource.GuvnorDroolsResource;

/**
 * @author nheron
 */
public class RuleBaseBuilder {


    /**
     * @param guvnor_url
     * @param guvnor_appName
     * @param guvnor_packageName
     * @param guvnor_packageVersion
     * @param guvnor_username
     * @param guvnor_password
     * @return
     */
    public static RuleBasePackage createGuvnorRuleBasePackage(String guvnor_url, String guvnor_appName, String guvnor_packageName, String guvnor_packageVersion,
                                                              String guvnor_username, String guvnor_password) {
        RuleBasePackage newRuleBasePackage = new RuleBaseSingleton();
        GuvnorDroolsResource gdr = new GuvnorDroolsResource(guvnor_url, guvnor_appName, guvnor_packageName, guvnor_packageVersion, guvnor_username, guvnor_password);
        newRuleBasePackage.addDroolsResouce(gdr);
        newRuleBasePackage.createKBase();

        return newRuleBasePackage;
    }
}
