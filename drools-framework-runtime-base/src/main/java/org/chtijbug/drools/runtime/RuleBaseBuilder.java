/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chtijbug.drools.runtime;

import org.chtijbug.drools.common.log.Logger;
import org.chtijbug.drools.common.log.LoggerFactory;
import org.chtijbug.drools.runtime.impl.RuleBaseSingleton;
import org.chtijbug.drools.runtime.resource.BinaryPackageDroolsRessource;
import org.chtijbug.drools.runtime.resource.GuvnorDroolsResource;

/**
 * @author nheron
 */
public class RuleBaseBuilder {
    /** Class Logger */
    private static Logger logger = LoggerFactory.getLogger(RuleBaseBuilder.class);

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
        logger.entry("createGuvnorRuleBasePackage", guvnor_url, guvnor_appName, guvnor_packageName,guvnor_packageVersion, guvnor_username, guvnor_password);
        RuleBasePackage newRuleBasePackage = new RuleBaseSingleton();
        try {
            GuvnorDroolsResource gdr = new GuvnorDroolsResource(guvnor_url, guvnor_appName, guvnor_packageName, guvnor_packageVersion, guvnor_username, guvnor_password);
            newRuleBasePackage.addDroolsResouce(gdr);
            newRuleBasePackage.createKBase();
            //_____ Returning the result
            return newRuleBasePackage;
        } finally {
            logger.exit("createGuvnorRuleBasePackage", newRuleBasePackage);
        }
    }

    public static RuleBasePackage createBinaryPackageBasePackage(String filename) {
        logger.entry("createBinaryPackageBasePackage");
        RuleBasePackage newRuleBasePackage = new RuleBaseSingleton();
        try {
            BinaryPackageDroolsRessource gdr = new BinaryPackageDroolsRessource(filename);
            newRuleBasePackage.addDroolsResouce(gdr);
            newRuleBasePackage.createKBase();
            //_____ Returning the result
            return newRuleBasePackage;
        } finally {
            logger.exit("createBinaryPackageBasePackage", newRuleBasePackage);
        }
    }
}
