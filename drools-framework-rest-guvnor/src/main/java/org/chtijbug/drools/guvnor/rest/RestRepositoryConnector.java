package org.chtijbug.drools.guvnor.rest;


import org.chtijbug.drools.guvnor.rest.dt.DecisionTable;
import org.chtijbug.drools.guvnor.rest.model.Asset;
import org.chtijbug.drools.guvnor.rest.model.AssetPropertyType;
import org.chtijbug.drools.guvnor.rest.model.AssetType;
import org.chtijbug.drools.guvnor.rest.model.Snapshot;
import org.drools.ide.common.client.modeldriven.dt52.GuidedDecisionTable52;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public interface RestRepositoryConnector {


    /**
     * This method retrieves the decision table from Guvnor assets repository according to the name argument.
     *
     * @param dtName {@link String} the decision table name into the Guvnor Repository
     * @return {@link GuidedDecisionTable52} the
     * @throws GuvnorConnexionFailedException - if the connexion to the Guvnor repository failed.
     */
    DecisionTable getGuidedDecisionTable(String dtName) throws GuvnorConnexionFailedException, ChtijbugDroolsRestException;

    DecisionTable getGuidedDecisionTable(String packageName, String dtName) throws GuvnorConnexionFailedException, ChtijbugDroolsRestException;

    void commitChanges(DecisionTable guidedDecisionTable52) throws GuvnorConnexionFailedException,ChtijbugDroolsRestException;

    void commitChanges(String packageName,DecisionTable guidedDecisionTable52) throws GuvnorConnexionFailedException,ChtijbugDroolsRestException;

    InputStream getPojoModel() throws ChtijbugDroolsRestException;
    InputStream getPojoModel(String packageName) throws ChtijbugDroolsRestException;

    /**
     * Loads the table from a template rule
     * @return Key : the column name.
     */
    Map<String,List<String>> getTemplateTable(String templateRuleName) throws ChtijbugDroolsRestException;

    Map<String,List<String>> getTemplateTable(String packageName,String templateRuleName) throws ChtijbugDroolsRestException;

    /**
     * Overrides the template rule table
     * Map size must match attended columns from the template table.
     * Every Map entry must have the same size (number of rows).
     */
    void putTemplateTable(String templateRuleName, Map<String, List<String>> table) throws ChtijbugDroolsRestException;

    void putTemplateTable(String packageName,String templateRuleName, Map<String, List<String>> table) throws ChtijbugDroolsRestException;


    /**
     * This method returns all business Assets from the Guvnor Repository
     */
    List<Asset> getAllBusinessAssets() throws ChtijbugDroolsRestException;
    List<Asset> getAllBusinessAssets(String packageName) throws ChtijbugDroolsRestException;

    /**
     *  returns all Package in a Guvnor Repository
     * @return
     */
    List<Asset> getAllPackagesInGuvnorRepo();


    void changeAssetPropertyValue(String assetName, AssetPropertyType assetPropertyType, String propertyValue) throws ChtijbugDroolsRestException;

    void changeAssetPropertyValue(String packageName,String assetName, AssetPropertyType assetPropertyType, String propertyValue);

    void createAsset(Asset asset, AssetType assetType,String assetSource) throws ChtijbugDroolsRestException;

    void createAsset(String packageName,Asset asset, AssetType assetType,String assetSource) throws ChtijbugDroolsRestException;

    void buildRulePackageByStatus(String snapshotName, String filter) throws ChtijbugDroolsRestException;

    void buildRulePackageByStatus(String packageName,String snapshotName, String filter) throws ChtijbugDroolsRestException;

    void deletePackageSnapshot(String snapshotName) throws ChtijbugDroolsRestException;

    void deletePackageSnapshot(String packageName,String snapshotName) throws ChtijbugDroolsRestException;


    List<Snapshot> getListSnapshots() throws ChtijbugDroolsRestException;

    List<Snapshot> getListSnapshots(String packageName) throws ChtijbugDroolsRestException;
}

