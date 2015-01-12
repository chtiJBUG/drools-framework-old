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
import org.chtijbug.drools.runtime.resource.Artifact;
import org.chtijbug.drools.runtime.resource.WorkbenchResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author nheron
 */
public class RuleBaseBuilder {
    /**
     * Class Logger
     */
    private static Logger logger = LoggerFactory.getLogger(RuleBaseBuilder.class);


    public static RuleBasePackage createGuvnorRuleBasePackageWithListener(HistoryListener historyListener, String guvnor_url, Artifact artifact, String guvnor_username, String guvnor_password) throws DroolsChtijbugException {
        logger.debug(">>createGuvnorRuleBasePackage", guvnor_url, artifact, guvnor_username, guvnor_password);
        RuleBasePackage newRuleBasePackage = new RuleBaseSingleton(RuleBaseSingleton.DEFAULT_RULE_THRESHOLD, historyListener);
        try {
            WorkbenchResource gdr = new WorkbenchResource(guvnor_url, artifact, guvnor_username, guvnor_password);
           // TODO
           // newRuleBasePackage.createKBase(gdr);
            //_____ Returning the result
            return newRuleBasePackage;
        } finally {
            logger.debug("<<createGuvnorRuleBasePackage", newRuleBasePackage);
        }
    }

    public static RuleBasePackage createPackageBasePackage(String... fileNames) throws DroolsChtijbugException {
        return RuleBaseBuilder.newRuleBasePackage(null, fileNames);
    }

    public static RuleBasePackage newRuleBasePackage(HistoryListener historyListener, String... filenames) throws DroolsChtijbugException {
        logger.debug(">>createPackageBasePackage");
        RuleBasePackage ruleBasePackage = new RuleBaseSingleton(RuleBaseSingleton.DEFAULT_RULE_THRESHOLD, historyListener);

        try {
            ruleBasePackage.createKBase(filenames);
            //_____ Returning the result
            return ruleBasePackage;
        } finally {
            logger.debug("<<createPackageBasePackage", ruleBasePackage);
        }
    }

}
