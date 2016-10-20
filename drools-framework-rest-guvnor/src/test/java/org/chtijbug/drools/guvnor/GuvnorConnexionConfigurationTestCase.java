package org.chtijbug.drools.guvnor;

import org.junit.Before;

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
        String hostname = "http://192.168.255.60:8080";
        String webappName = "drools-guvnor";
        String packageName = "amag";
        String username = "tomcat";
        String password = "tomcat";
        this.toTest = new GuvnorConnexionConfiguration(hostname, webappName, packageName, username, password);
    }

//    @Test
//    public void testGetRestAPIPathForPackage() throws Exception {
//        String restApiPath = toTest.getRestAPIPathForPackage();
//        String expectedPath = "drools-guvnor/rest/packages/packageName/assets/";
//
//        assertEquals("The expected calculated path did not match.", expectedPath, restApiPath);
//    }
}
