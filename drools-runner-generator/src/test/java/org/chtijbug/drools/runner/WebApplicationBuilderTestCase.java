package org.chtijbug.drools.runner;

import org.apache.commons.io.DirectoryWalker;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: samuel
 * Date: 03/10/12
 * Time: 18:13
 */
public class WebApplicationBuilderTestCase {

    @Test
    public void runBuildProjectSkeleton() throws IOException {
        File workspaceFolder = new File(RunnerConfiguration.WORKSPACE_FOLDER);
        assertNotNull(workspaceFolder);

        try {
            RunnerConfiguration runnerConfiguration = new RunnerConfiguration("guvnorAppUrl", "JUNIT", "inputClassName", "outputClassName");
            WebApplicationBuilder toTest = new WebApplicationBuilder(runnerConfiguration);
            toTest.buildProjectSkeleton();
            //_____ If the method is correctly executed, then it is correct.
            // TODO Add some file system searches to be sure directories are corretly created
        } catch (Throwable e) {
            e.printStackTrace();
            fail();
        } finally {
            FileUtils.deleteDirectory(workspaceFolder);
        }
    }

}
