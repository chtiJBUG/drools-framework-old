package org.chtijbug.drools.guvnor.rest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.chtijbug.drools.guvnor.rest.dt.DecisionTable;
import org.chtijbug.drools.guvnor.rest.model.*;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.InputStream;
import java.util.List;

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
        guvnorRepositoryConnector = new GuvnorRepositoryConnector("http://localhost:8080/", "/guvnor-5.5.2.chtijbug-tomcat-6.0", "mortgages", "tomcat", "tomcat");
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

        File tmp = File.createTempFile("chtijbug-model", "jar");

        List<String> readLines = IOUtils.readLines(inputStream);
        FileUtils.writeLines(tmp, readLines);
    }


    @Test
    @Ignore
    public void testUpdateAsset() throws Exception {
        guvnorRepositoryConnector.changeAssetPropertyValue("Underage", AssetPropertyType.STATE, "ESSAI");
    }

    @Test
    @Ignore
    public void testcreateAsset() throws Exception {
        Asset newAsset = new Asset();
        newAsset.setName("FirstRule2");
        newAsset.setSumary("First Rule via Rest");
        newAsset.setStatus("DEV_1");
        newAsset.setType(AssetType.GuidedRule.getId());
        AssetCategory newCategoryOne = new AssetCategory("amag");
        newAsset.getCategories().add(newCategoryOne) ;
        AssetCategory newCategorytwo = new AssetCategory("amag");
        newAsset.getCategories().add(newCategorytwo);

        guvnorRepositoryConnector.createAsset(newAsset, AssetType.GuidedRule,"no source");

    }

    @Test
    @Ignore
    public void testListSnapshots() throws Exception {


        List<Snapshot> theList = guvnorRepositoryConnector.getListSnapshots();
        System.out.println(theList.toString());

    }


}
