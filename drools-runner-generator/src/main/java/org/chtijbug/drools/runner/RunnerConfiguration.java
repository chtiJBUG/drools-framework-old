package org.chtijbug.drools.runner;

/**
 * Created with IntelliJ IDEA.
 * User: samuel
 * Date: 03/10/12
 * Time: 17:26
 */
public class RunnerConfiguration {
    protected static final String WORKSPACE_FOLDER = "/tmp/chtijbug";
    /** Guvnor connexion information */

    /** basic guvnor web app URL */
    private String guvnorAppUrl;
    /** The business package name */
    private String packageName;
    /** Class name to be used as an argument */
    private String inputClassName;
    /** Class name to be used as a result */
    private String outputClassName;

    public RunnerConfiguration(String guvnorAppUrl, String packageName, String inputClassName, String outputClassName) {
        this.guvnorAppUrl = guvnorAppUrl;
        this.packageName = packageName;
        this.inputClassName = inputClassName;
        this.outputClassName = outputClassName;
    }

    public String getGuvnorAppUrl() {
        return guvnorAppUrl;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getInputClassName() {
        return inputClassName;
    }

    public String getOutputClassName() {
        return outputClassName;
    }
}
