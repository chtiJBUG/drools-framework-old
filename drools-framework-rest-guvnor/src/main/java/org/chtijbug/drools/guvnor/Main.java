package org.chtijbug.drools.guvnor;

import org.chtijbug.drools.guvnor.rest.GuvnorRepositoryConnector;
import org.drools.guvnor.server.jaxrs.jaxb.Asset;

import java.util.List;

/**
 * Created by nheron on 02/02/15.
 */
public class Main {
    public static void main(String args[]) throws Exception {

        //http://localhost:8080/rest/packages/demo/guvnorng-playground/mortgages/assets/ApplicantDsl.dsl
        GuvnorConnexionConfiguration configuration = new GuvnorConnexionConfiguration("http://localhost:8080", "/","demo","guvnorng-playground", "mortgages", "admin", "admin");
        GuvnorRepositoryConnector guvnorRepositoryConnector = new GuvnorRepositoryConnector(configuration);
        List<Asset> toto = guvnorRepositoryConnector.getAllBusinessAssets();
        System.out.println("done");
    }
}
