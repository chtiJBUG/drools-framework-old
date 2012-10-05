package org.chtijbug.drools.runner;

import java.io.ByteArrayOutputStream;

/**
 * Created with IntelliJ IDEA.
 * User: samuel
 * Date: 03/10/12
 * Time: 17:32
 */
class DroolsRunnerFactory {
    /** Singleton isntance */
    private static DroolsRunnerFactory singleton = new DroolsRunnerFactory();

    /**
     * Private constructor
     */
    private DroolsRunnerFactory() {
        // nop
    }

    /**
     * Singleton Get access method
     * @return singleton instance
     */
    public static DroolsRunnerFactory getInstance() {
        return singleton;
    }

    public ByteArrayOutputStream createServiceInterface(RunnerConfiguration runnerConfiguration) {
        // TODO
        return null;
    }

    public ByteArrayOutputStream createServiceImplementation(RunnerConfiguration runnerConfiguration) {
        // TODO
        return null;
    }

    public ByteArrayOutputStream createPropertyFile(RunnerConfiguration runnerConfiguration) {
        // TODO
        return null;
    }


}
