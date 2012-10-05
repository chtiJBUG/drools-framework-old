package org.chtijbug.drools.runner;


import org.chtijbug.drools.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import static org.chtijbug.drools.utils.FileUtils.*;

/**
 * Created with IntelliJ IDEA.
 * User: samuel
 * Date: 03/10/12
 * Time: 17:38
 */
public class WebApplicationBuilder {
    /** POM file template */
    private static final String POM_FILE = "/templates/pom-template";
    /** log4j file template */
    private static final String LOG4J_FILE = "/templates/log4j-template";
    /** spring file template */
    private static final String SPRING_FILE = "/templates/spring-template";
    /** spring CXF file template */
    private static final String SPRING_CXF_FILE = "/templates/spring-cxf-template";
    /** web service package  */
    private static final String WS_INTERFACE_PACKAGE = "org/chtijbug/drools/ws";
    /** web service implementation package */
    private static final String WS_IMPLEMENTATION_PACKAGE = WS_INTERFACE_PACKAGE.concat("/impl");
    public static final String SRC_MAIN_JAVA = "src/main/java";
    public static final String SRC_MAIN_RESOURCES = "src/main/resources";
    public static final String SRC_MAIN_RESOURCES_SPRING = "src/main/resources/spring";
    public static final String SRC_MAIN_WEBAPP_WEB_INF = "src/main/webapp/WEB-INF";
    public static final String SRC_MAIN_WEBAPP_WEB_INF_SPRING = "src/main/webapp/WEB-INF/spring";
    /** Factory helper instance to provide some file resources */
    private DroolsRunnerFactory factory = DroolsRunnerFactory.getInstance();

    private final RunnerConfiguration runnerConfiguration;
    private final String webAppName;
    private final File projectFolder;


    public WebApplicationBuilder(RunnerConfiguration runnerConfiguration) throws DroolsRunnerGenerationException {
        this.runnerConfiguration = runnerConfiguration;
        File workspaceFolder = new File(RunnerConfiguration.WORKSPACE_FOLDER);
        if (!workspaceFolder.exists()) {
            if (!workspaceFolder.mkdirs()) {
                throw new DroolsRunnerGenerationException(String.format("Error while creating the folder %s", RunnerConfiguration.WORKSPACE_FOLDER));
            }
        }
        //_____ Create the root folder
        this.webAppName = runnerConfiguration.getPackageName().concat("-rules-service");
        projectFolder = new File(workspaceFolder, webAppName);
        if (!projectFolder.exists() && !projectFolder.mkdir()) {
            throw new DroolsRunnerGenerationException(String.format("Error while creating the folder %s", projectFolder.getName()));
        }
    }

    public void buildWebApplication() throws DroolsRunnerGenerationException, IOException {
        //______ Build the project file skeleton
        buildProjectSkeleton();
        //______ Copy all files from templates
        //_____ Copy log4j.xml file into resources folder
        File resourcesFolder = getFolder(projectFolder, SRC_MAIN_RESOURCES);
        createFileFromTemplate(resourcesFolder, "log4j.xml", LOG4J_FILE);
        File springFolder = getFolder(projectFolder, SRC_MAIN_RESOURCES_SPRING);
        createFileFromTemplate(springFolder, "chtijbug-spring.xml", SPRING_FILE);
        File springCxfFolder = getFolder(projectFolder, SRC_MAIN_WEBAPP_WEB_INF_SPRING);
        createFileFromTemplate(springCxfFolder, "rule-engine-servlet.xml", SPRING_CXF_FILE);
        //_____ Copy file expected to be updated according to configuration
        File pomXml = createFileFromTemplate(projectFolder, "pom.xml", POM_FILE);
        FileUtils.replaceTokenInFile(pomXml, "#WAR_NAME#", this.webAppName);
        //_____ Copy property file containing Guvnor repository connexion info
        File guvnorPropsFile = createFileFromTemplate(resourcesFolder, "rules-runner.properties", "guvnor-properties-template");
        //TODO Remplacer le contenu du fichier properties


        //______ Create the service interface





    }

    protected void buildProjectSkeleton() throws DroolsRunnerGenerationException, IOException {
        //_____ Create all standard Maven web application project folders
        createFolder(SRC_MAIN_JAVA, projectFolder);
        createFolder(SRC_MAIN_RESOURCES, projectFolder);
        createFolder(SRC_MAIN_WEBAPP_WEB_INF, projectFolder);
        //_____ Copy pom.xml file base on template
        File resourcesFolder = getFolder(projectFolder, SRC_MAIN_RESOURCES);
        //______ Copy spring context configuration file
        createFolder("spring", resourcesFolder);
        //______ Copy spring web context configuration file
        File webInfFolder = getFolder(projectFolder, SRC_MAIN_WEBAPP_WEB_INF);
        createFolder("spring", webInfFolder);
        //______ Create folder for containing java classes to be generated
        File srcFolder = getFolder(projectFolder, SRC_MAIN_JAVA);
        createFolder(WS_IMPLEMENTATION_PACKAGE,srcFolder );
    }

}
