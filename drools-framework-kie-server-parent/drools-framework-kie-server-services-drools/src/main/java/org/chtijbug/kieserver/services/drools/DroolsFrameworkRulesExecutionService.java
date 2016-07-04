/*
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/

package org.chtijbug.kieserver.services.drools;

import org.chtijbug.drools.runtime.DroolsChtijbugException;
import org.chtijbug.drools.runtime.RuleBasePackage;
import org.chtijbug.drools.runtime.RuleBaseSession;
import org.chtijbug.drools.runtime.impl.RuleBaseSingleton;
import org.kie.api.runtime.KieContainer;
import org.kie.server.services.api.KieContainerInstance;
import org.kie.server.services.api.KieServerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Direct rules execution service that allow use of typed objects instead of string only
 */
public class DroolsFrameworkRulesExecutionService {
    private static final Logger logger = LoggerFactory.getLogger(DroolsFrameworkRulesExecutionService.class);

    private KieServerRegistry context;
    private RuleBasePackage ruleBasePackage = null;

    public DroolsFrameworkRulesExecutionService(KieServerRegistry context) {
        this.context = context;
    }


    public KieServerRegistry getContext() {
        return context;
    }

    public Object FireAllRulesAndStartProcess(KieContainerInstance kci, Object object, String processID) {
        Object result = null;
        try {

            if (kci != null && kci.getKieContainer() != null) {

                KieContainer kieContainer = kci.getKieContainer();
                ruleBasePackage = new RuleBaseSingleton(kieContainer, 20000);
            }
            RuleBaseSession session = ruleBasePackage.createRuleBaseSession();
            result = session.fireAllRulesAndStartProcess(object, processID);
            logger.debug("Returning OK response with content '{}'", object);
            return result;

        } catch (DroolsChtijbugException e) {
            e.printStackTrace();
        }


        throw new IllegalStateException("Unable to execute command " + object);
    }
}
