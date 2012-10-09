package org.chtijbug.drools.guvnor;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: samuel
 * Date: 09/10/12
 * Time: 09:32
 */
public class GuvnorConnexionConfigurationTestCase {

    private GuvnorConnexionConfiguration toTest;

    @Before
    public void setUp() throws Exception {
        String hostname = "http://localhost:8080";
        String webappName = "drools-guvnor";
        String username = "tomcat";
        String password = "tomcat";
        this.toTest = new GuvnorConnexionConfiguration(hostname, webappName, username, password);
    }

    @Test
    public void testGetRestAPIPathForPackage() throws Exception {
        String restApiPath = toTest.getRestAPIPathForPackage("packageName");
        String expectedPath = "http://localhost:8080/rest/packages/packageName/assets/";

        assertEquals("The expected calculated path did not match.", expectedPath, restApiPath);
    }

    @Test
    public void testCreateAuthenticationHeader() throws Exception {
        String base64EncodedHeader = toTest.createAuthenticationHeader();

        String expectedEncodedHeader = "Basic dG9tY2F0OnRvbWNhdA==";

        assertEquals("The expected encoded header did not match.", expectedEncodedHeader, base64EncodedHeader);

    }
}
