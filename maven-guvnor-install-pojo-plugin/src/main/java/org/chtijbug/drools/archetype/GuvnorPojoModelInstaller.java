package org.chtijbug.drools.archetype;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.chtijbug.drools.common.log.Logger;
import org.chtijbug.drools.common.log.LoggerFactory;
import org.chtijbug.drools.guvnor.rest.GuvnorRepositoryConnector;
import org.chtijbug.drools.guvnor.rest.RestRepositoryConnector;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * User: Samuel
 * Date: 20/12/12
 * Time: 15:28
 * To change this template use File | Settings | File Templates.
 */
public class GuvnorPojoModelInstaller extends AbstractMojo {
    /** class Logger */
    private static Logger logger = LoggerFactory.getLogger(GuvnorPojoModelInstaller.class);
    /**
     * @parameter
     */
    private String host;
    /**
     * @parameter
     */
    private String app;
    /**
     * @parameter
     */
    private String pkg;
    /**
     * @parameter
     */
    private String version;
    /**
     * @parameter
     */
    private String username;
    /**
     * @parameter
     */
    private String password;
    /**
     * @parameter
     */
    private String artifactGroupId;
    /**
     * @parameter
     */
    private String artifactId;

    /**
     * @execute goal="install-file"
     * @throws MojoExecutionException
     * @throws MojoFailureException
     */
    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        logger.entry("execute");
        try {
            RestRepositoryConnector repositoryConnector = new GuvnorRepositoryConnector(host, app, pkg, username, password);
            //____ Check Guvnor instance (Is it reachable ? Does the package exists ? )
            // TODO
            //____ Download the POJO Model from Guvnor instance
            InputStream inputStream = repositoryConnector.getPojoModel();
            //____ First create TEMP file with the downloaded content
            File tempFile = File.createTempFile(artifactId, ".jar");
            FileOutputStream outputStream = FileUtils.openOutputStream(tempFile);
            IOUtils.copy(inputStream, outputStream);
            IOUtils.closeQuietly(outputStream);
            //____ Install the pojo model from the local repository as a Maven artifact

            // TODO
        } catch (IOException e) {
            logger.error("Error occurred while creating he file from the Pojo Model", e);
        } finally {
            logger.exit("execute");
        }
    }

}
