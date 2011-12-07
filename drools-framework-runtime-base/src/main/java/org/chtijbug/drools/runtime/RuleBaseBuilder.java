/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chtijbug.drools.runtime;

import org.chtijbug.drools.runtime.impl.RuleBaseSingleton;

/**
 *
 * @author nheron
 */
public class RuleBaseBuilder {

    public static RuleBasePackage createRuleBasePackage(String guvnor_url, String guvnor_packageName, String guvnor_username, String guvnor_password) {
        RuleBasePackage newRuleBasePackage = new RuleBaseSingleton(guvnor_url, guvnor_packageName, guvnor_username, guvnor_password);
        return newRuleBasePackage;
    }

    public static RuleBasePackage createRuleBasePackage(String guvnor_url, String guvnor_appName, String guvnor_packageName, String guvnor_packageVersion, String guvnor_username, String guvnor_password) {
        RuleBasePackage newRuleBasePackage = new RuleBaseSingleton(guvnor_url, guvnor_appName, guvnor_packageName, guvnor_packageVersion, guvnor_username, guvnor_password);
        return newRuleBasePackage;
    }

    public static RuleBasePackage createRuleBasePackage(String guvnor_url, String guvnor_appName, String guvnor_packageName, String guvnor_username, String guvnor_password) {
        RuleBasePackage newRuleBasePackage = new RuleBaseSingleton(guvnor_url, guvnor_appName, guvnor_packageName, guvnor_username, guvnor_password);
        return newRuleBasePackage;

    }
}
