/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chtijbug.drools.runtime;

import org.chtijbug.drools.runtime.impl.RuleBaseSingleton;
import org.chtijbug.drools.runtime.resource.DrlDroolsResource;

/**
 * @author nheron
 */
public class Main {

    public static void main(String args[]) {
        DrlDroolsResource resource = DrlDroolsResource.createClassPathResource("fibonacci.drl");
        RuleBasePackage ruleBasePackage = new RuleBaseSingleton();
        //RuleBasePackage ruleBasePackage = RuleBaseBuilder.createBinaryPackageBasePackage("classpath:risk.pkg");
        ruleBasePackage.addDroolsResouce(resource);
        ruleBasePackage.createKBase();
        //RuleBasePackage ruleBasePackage = RuleBaseBuilder.createGuvnorRuleBasePackage("http://localhost:8080/", "drools-guvnor/", "amag/", "LATEST", "", "")     ;
        try {
            for (int i = 0; i < 1000; i++) {
                RuleBaseSession session1 = ruleBasePackage.createRuleBaseSession();
                Fibonacci fibonacci = new Fibonacci(5);
                session1.insertObject(fibonacci);
                session1.fireAllRules();
                session1.dispose();
                Thread.sleep(2000);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
