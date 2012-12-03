/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chtijbug.drools.runtime;

import org.chtijbug.drools.common.log.Logger;
import org.chtijbug.drools.common.log.LoggerFactory;
import org.chtijbug.drools.runtime.impl.RuleBaseSingleton;
import org.chtijbug.drools.runtime.resource.Bpmn2DroolsRessource;
import org.chtijbug.drools.runtime.resource.DrlDroolsRessource;
import org.chtijbug.drools.runtime.resource.DroolsResource;
import org.chtijbug.drools.runtime.resource.GuvnorDroolsResource;

/**
 * @author nheron
 */
public class RuleBaseBuilder {
    /**
     * Class Logger
     */
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
                                                              String guvnor_username, String guvnor_password) throws DroolsChtijbugException {
        logger.entry("createGuvnorRuleBasePackage", guvnor_url, guvnor_appName, guvnor_packageName, guvnor_packageVersion, guvnor_username, guvnor_password);
        RuleBasePackage newRuleBasePackage = new RuleBaseSingleton(RuleBaseSingleton.DEFAULT_RULE_THRESHOLD);
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

    public static RuleBasePackage createPackageBasePackage(String... filenames) throws DroolsChtijbugException {
        logger.entry("createPackageBasePackage");
        RuleBasePackage ruleBasePackage = new RuleBaseSingleton(RuleBaseSingleton.DEFAULT_RULE_THRESHOLD);
        try {
            for (String filename : filenames) {
                String extensionName = getFileExtension(filename);
                DroolsResource resource=null;
                if ("DRL".equals(extensionName)){
                    resource= DrlDroolsRessource.createClassPathResource(filename);
                }else if ("BPMN2".equals(extensionName)){
                    resource= Bpmn2DroolsRessource.createClassPathResource(filename);
                }
                if (resource!= null){
                    ruleBasePackage.addDroolsResouce(resource);
                }else {
                    throw new DroolsChtijbugException(DroolsChtijbugException.UnknowFileExtension,filename,null) ;
                }
            }
            ruleBasePackage.createKBase();
            //_____ Returning the result
            return ruleBasePackage;
        } finally {
            logger.exit("createPackageBasePackage", ruleBasePackage);
        }
    }

    private static String getFileExtension(String ressourceName) {
        int mid = ressourceName.lastIndexOf(".");
        String fileName = ressourceName.substring(0, mid);
        String ext = ressourceName.substring(mid + 1, ressourceName.length()).toUpperCase();
        return ext;
    }

}
