package org.chtijbug.drools.guvnor.rest;

import org.chtijbug.drools.guvnor.rest.dt.DecisionTable;
import org.chtijbug.drools.guvnor.rest.model.AssetPropertyType;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Created by IntelliJ IDEA.
 * Date: 26/04/12
 * Time: 10:31
 * To change this template use File | Settings | File Templates.
 */
public class GuvnorRepositoryConnectorLocalTestCase {
    GuvnorRepositoryConnector guvnorRepositoryConnector;

    @Before
    public void setUp() throws Exception {
        guvnorRepositoryConnector = new GuvnorRepositoryConnector("http://localhost:8080/", "drools-guvnor/", "mortgages", "tomcat", "tomcat");
    }

    @Test
    @Ignore
    public void testName() throws Exception {
        DecisionTable toto = guvnorRepositoryConnector.getGuidedDecisionTable("test");
        System.out.println(toto.toString());
    }

    @Test
    @Ignore
    public void testGetModel() throws Exception {
        InputStream inputStream = guvnorRepositoryConnector.getPojoModel();

        FileOutputStream outputStream = new FileOutputStream("/tmp/chtijbug/model.jar");
        int ch;
        while ((ch = inputStream.read()) != -1) {
            outputStream.write(ch);
        }
        outputStream.close();
        inputStream.close();
    }


    @Test
    @Ignore
    public void testUpdateAsset() throws Exception {
        guvnorRepositoryConnector.changeAssetPropertyValue("Underage", AssetPropertyType.STATE, "ESSAI");

    }
}
