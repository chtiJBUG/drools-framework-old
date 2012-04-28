package org.chtijbug.drools.guvnor.rest;

import org.chtijbug.drools.guvnor.rest.dt.DecisionTable;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by IntelliJ IDEA.
 * Date: 26/04/12
 * Time: 10:31
 * To change this template use File | Settings | File Templates.
 */
public class GuvnorRepositoryConnectorTest {
    GuvnorRepositoryConnector guvnorRepositoryConnector;
    @Before
    public void setUp() throws Exception {
        guvnorRepositoryConnector = new GuvnorRepositoryConnector("http://localhost:8080/","drools-guvnor/","test","tomcat","tomcat");
    }

    @Test
    public void testName() throws Exception {
        DecisionTable toto = guvnorRepositoryConnector.getGuidedDecisionTable("test");
        System.out.println(toto.toString());
    }
}
