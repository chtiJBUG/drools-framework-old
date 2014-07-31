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
