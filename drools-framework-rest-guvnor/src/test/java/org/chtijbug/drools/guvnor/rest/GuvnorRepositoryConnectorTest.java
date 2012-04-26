package org.chtijbug.drools.guvnor.rest;

import org.drools.ide.common.client.modeldriven.dt52.GuidedDecisionTable52;
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
        guvnorRepositoryConnector = new GuvnorRepositoryConnector("http://localhost:8080/","drools-guvnor/","amag","tomcat","tomcat");
    }

    @Test
    public void testName() throws Exception {
        GuidedDecisionTable52 toto = guvnorRepositoryConnector.getGuidedDecisionTable("common_cargroup_cat");;
        System.out.println(toto.toString());
    }
}
