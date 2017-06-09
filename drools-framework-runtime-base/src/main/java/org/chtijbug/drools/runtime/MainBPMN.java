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

public class MainBPMN {

    public static void main(String args[]) throws Exception {
        RuleBasePackage ruleBasePackage = RuleBaseBuilder.createPackageBasePackage("ruleflow2.drl", "RuleFlowProcess2.bpmn2");
        try {
            for (int i = 0; i < 100000; i++) {
                RuleBaseSession session1 = ruleBasePackage.createRuleBaseSession();
                Fibonacci fibonacci = new Fibonacci(0);
                // session1.insertObject(fibonacci);
                session1.fireAllRulesAndStartProcess(fibonacci, "P1");
                session1.dispose();
                Thread.sleep(2000);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}