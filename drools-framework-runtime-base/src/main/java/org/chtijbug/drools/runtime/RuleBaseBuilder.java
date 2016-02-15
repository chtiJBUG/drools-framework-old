/*
 * Copyright 2014 Pymma Software
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.chtijbug.drools.runtime;

import org.chtijbug.drools.runtime.impl.RuleBaseSingleton;
import org.chtijbug.drools.runtime.listener.HistoryListener;
import org.chtijbug.drools.runtime.resource.Bpmn2DroolsResource;
import org.chtijbug.drools.runtime.resource.DrlDroolsResource;
import org.chtijbug.drools.runtime.resource.DroolsResource;
import org.chtijbug.drools.runtime.resource.GuvnorDroolsResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;


public class RuleBaseBuilder {

    /**
     * Class Logger
     */
    private static Logger logger = LoggerFactory.getLogger(RuleBaseBuilder.class);


    public static RuleBasePackage createGuvnorRuleBasePackage(String guvnor_url, String guvnor_appName, String guvnor_packageName, String guvnor_packageVersion,
            String guvnor_username, String guvnor_password) throws DroolsChtijbugException {
        return RuleBaseBuilder.createGuvnorRuleBasePackageWithListener(null, guvnor_url, guvnor_appName, guvnor_packageName, guvnor_packageVersion,
                guvnor_username, guvnor_password);
    }

    public static RuleBasePackage createGuvnorRuleBasePackageWithListener(HistoryListener historyListener, String guvnor_url, String guvnor_appName, String guvnor_packageName, String guvnor_packageVersion,
            String guvnor_username, String guvnor_password) throws DroolsChtijbugException {
        logger.debug(">>createGuvnorRuleBasePackage", guvnor_url, guvnor_appName, guvnor_packageName, guvnor_packageVersion, guvnor_username, guvnor_password);
        RuleBasePackage newRuleBasePackage = new RuleBaseSingleton(RuleBaseSingleton.DEFAULT_RULE_THRESHOLD, historyListener);
        try {
            GuvnorDroolsResource gdr = new GuvnorDroolsResource(guvnor_url, guvnor_appName, guvnor_packageName, guvnor_packageVersion, guvnor_username, guvnor_password);
            newRuleBasePackage.createKBase(gdr);
            //_____ Returning the result
            return newRuleBasePackage;
        } finally {
            logger.debug("<<createGuvnorRuleBasePackage", newRuleBasePackage);
        }
    }

    public static RuleBasePackage createPackageBasePackage(String... filenames) throws DroolsChtijbugException {
        return RuleBaseBuilder.createPackageBasePackageWithListener(null, filenames);
    }

    public static RuleBasePackage createPackageBasePackageWithListener(HistoryListener historyListener, String... filenames) throws DroolsChtijbugException {
        return RuleBaseBuilder.createPackageBasePackageWithListenerandRootPath(null, historyListener, filenames);
    }

    public static RuleBasePackage createPackageBasePackageWithListenerandRootPath(String rootPath, HistoryListener historyListener, String... filenames) throws DroolsChtijbugException {

        logger.debug(">>createPackageBasePackage");
        RuleBasePackage ruleBasePackage = new RuleBaseSingleton(RuleBaseSingleton.DEFAULT_RULE_THRESHOLD, historyListener);
        try {
            List<DroolsResource> droolsResources = new ArrayList<DroolsResource>();
            for (String filename : filenames) {
                String extensionName = getFileExtension(filename);
                DroolsResource resource = null;
                if ("DRL".equals(extensionName)) {
                    if (rootPath == null) {
                        resource = DrlDroolsResource.createClassPathResource(filename);
                    } else {
                        resource = DrlDroolsResource.createFileSystemPathResource(rootPath + "/" + filename);
                    }
                } else if ("BPMN2".equals(extensionName)) {
                    if (rootPath == null) {
                        resource = Bpmn2DroolsResource.createClassPathResource(filename);
                    } else {
                        resource = Bpmn2DroolsResource.createFileSystemPathResource(rootPath + "/" + filename);
                    }
                }
                if (resource != null) {
                    droolsResources.add(resource);
                } else {
                    throw new DroolsChtijbugException(DroolsChtijbugException.UnknowFileExtension, filename, null);
                }
            }

            ruleBasePackage.createKBase(droolsResources);
            //_____ Returning the result
            return ruleBasePackage;
        } finally {
            logger.debug("<<createPackageBasePackage", ruleBasePackage);
        }
    }

    private static String getFileExtension(String ressourceName) {
        int mid = ressourceName.lastIndexOf(".");
        String fileName = ressourceName.substring(0, mid);
        String ext = ressourceName.substring(mid + 1, ressourceName.length()).toUpperCase();
        return ext;
    }

}
