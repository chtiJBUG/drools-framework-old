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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author nheron
 */
public abstract class RuleBaseBuilder {
    /**
     * Class Logger
     */
    private static Logger logger = LoggerFactory.getLogger(RuleBaseBuilder.class);


    public static RuleBasePackage createWorkbenchRuleBasePackage(HistoryListener historyListener, String modulePackage, String moduleName, String version, String workbenchUrl, String username, String password) throws DroolsChtijbugException {
        logger.debug(">> createWorkbenchRuleBasePackage()");
        RuleBaseSingleton newRuleBasePackage = new RuleBaseSingleton(RuleBaseSingleton.DEFAULT_RULE_THRESHOLD, historyListener, modulePackage, moduleName);
        try {
           newRuleBasePackage.createKBase(version, workbenchUrl, username, password);
            //_____ Returning the result
            return newRuleBasePackage;
        } finally {
            logger.debug("<< createWorkbenchRuleBasePackage", newRuleBasePackage);
        }
    }

    public static RuleBasePackage createPackageBasePackage(String modulePackage, String moduleName, List<String> fileNames) throws DroolsChtijbugException {
        return RuleBaseBuilder.newRuleBasePackage(null, modulePackage, moduleName, fileNames);
    }

    public static RuleBasePackage newRuleBasePackage(HistoryListener historyListener, String modulePackage, String moduleName, List<String> filenames) throws DroolsChtijbugException {
        logger.debug(">>createPackageBasePackage");
        RuleBaseSingleton ruleBasePackage = new RuleBaseSingleton(RuleBaseSingleton.DEFAULT_RULE_THRESHOLD, historyListener, modulePackage, moduleName);
        try {
            ruleBasePackage.createKBase(filenames);
            //_____ Returning the result
            return ruleBasePackage;
        } finally {
            logger.debug("<<createPackageBasePackage", ruleBasePackage);
        }
    }

}
