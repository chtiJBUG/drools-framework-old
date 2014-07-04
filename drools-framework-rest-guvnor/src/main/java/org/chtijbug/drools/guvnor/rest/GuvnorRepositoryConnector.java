package org.chtijbug.drools.guvnor.rest;

import org.chtijbug.drools.guvnor.GuvnorConnexionConfiguration;
import org.chtijbug.drools.guvnor.rest.dt.DecisionTable;
import org.chtijbug.drools.guvnor.rest.model.Asset;
import org.chtijbug.drools.guvnor.rest.model.AssetPropertyType;
import org.chtijbug.drools.guvnor.rest.model.AssetType;
import org.chtijbug.drools.guvnor.rest.model.Snapshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;

public class GuvnorRepositoryConnector implements RestRepositoryConnector {
    /**
     * Class logger
     */
    private static Logger logger = LoggerFactory.getLogger(GuvnorRepositoryConnector.class);

    /**
     * Guvnor Web application connexion data
     */
    private GuvnorConnexionConfiguration configuration;

    private AssetManager assetManager = null;

    private RuleTemplateManager ruleTemplateManager = null;

    private DecisionTableManager decisionTableManager = null;

    private RulePackageManager rulePackageManager = null;

    public GuvnorRepositoryConnector(GuvnorConnexionConfiguration configuration) {
        logger.debug(format("Creating new GuvnorRepositoryConnector with args : %s", configuration.toString()));
        this.configuration = configuration;
        this.assetManager = new AssetManager(configuration);
        this.ruleTemplateManager = new RuleTemplateManager(configuration, this.assetManager);
        this.decisionTableManager = new DecisionTableManager(configuration, this.assetManager);
        this.rulePackageManager = new RulePackageManager(configuration);
    }

    public GuvnorRepositoryConnector(String guvnorUrl, String guvnorAppName, String packageName, String guvnorUserName, String guvnorPassword) {
        this(new GuvnorConnexionConfiguration(guvnorUrl, guvnorAppName, packageName, guvnorUserName, guvnorPassword));
    }

    public GuvnorRepositoryConnector(String guvnorUrl, String guvnorAppName, String guvnorUserName, String guvnorPassword) {
        this(new GuvnorConnexionConfiguration(guvnorUrl, guvnorAppName, null, guvnorUserName, guvnorPassword));
    }


    @Override
    public DecisionTable getGuidedDecisionTable(String dtName) throws ChtijbugDroolsRestException {
        if (configuration.getDefaultPackageName() == null || configuration.getDefaultPackageName().length() == 0) {
            ChtijbugDroolsRestException chtijbugDroolsRestException = new ChtijbugDroolsRestException("No Default Package Name defined");
            throw chtijbugDroolsRestException;
        }
        return this.decisionTableManager.getGuidedDecisionTable(configuration.getDefaultPackageName(), dtName);
    }

    @Override
    public DecisionTable getGuidedDecisionTable(String packageName, String dtName) throws GuvnorConnexionFailedException, ChtijbugDroolsRestException {
        return this.decisionTableManager.getGuidedDecisionTable(packageName, dtName);
    }

    @Override
    public void commitChanges(DecisionTable guidedDecisionTable52) throws ChtijbugDroolsRestException {
        if (configuration.getDefaultPackageName() == null || configuration.getDefaultPackageName().length() == 0) {
            ChtijbugDroolsRestException chtijbugDroolsRestException = new ChtijbugDroolsRestException("No Default Package Name defined");
            throw chtijbugDroolsRestException;
        }
        this.decisionTableManager.commitChanges(configuration.getDefaultPackageName(), guidedDecisionTable52);
    }

    @Override
    public void commitChanges(String packageName, DecisionTable guidedDecisionTable52) throws GuvnorConnexionFailedException, ChtijbugDroolsRestException {
        this.decisionTableManager.commitChanges(packageName, guidedDecisionTable52);
    }


    @Override
    public InputStream getPojoModel() throws ChtijbugDroolsRestException {
        if (configuration.getDefaultPackageName() == null || configuration.getDefaultPackageName().length() == 0) {
            ChtijbugDroolsRestException chtijbugDroolsRestException = new ChtijbugDroolsRestException("No Default Package Name defined");
            throw chtijbugDroolsRestException;
        }
        return this.assetManager.getPojoModel(configuration.getDefaultPackageName());
    }

    @Override
    public InputStream getPojoModel(String packageName) throws ChtijbugDroolsRestException {

        return this.assetManager.getPojoModel(packageName);
    }

    @Override
    public Map<String, List<String>> getTemplateTable(String templateRuleName) throws ChtijbugDroolsRestException {
        if (configuration.getDefaultPackageName() == null || configuration.getDefaultPackageName().length() == 0) {
            ChtijbugDroolsRestException chtijbugDroolsRestException = new ChtijbugDroolsRestException("No Default Package Name defined");
            throw chtijbugDroolsRestException;
        }
        return this.ruleTemplateManager.getTemplateTable(configuration.getDefaultPackageName(), templateRuleName);
    }

    @Override
    public Map<String, List<String>> getTemplateTable(String packageName, String templateRuleName) throws ChtijbugDroolsRestException {
        return this.ruleTemplateManager.getTemplateTable(packageName, templateRuleName);
    }

    @Override
    public void putTemplateTable(String templateRuleName, Map<String, List<String>> newTable) throws ChtijbugDroolsRestException {
        if (configuration.getDefaultPackageName() == null || configuration.getDefaultPackageName().length() == 0) {
            ChtijbugDroolsRestException chtijbugDroolsRestException = new ChtijbugDroolsRestException("No Default Package Name defined");
            throw chtijbugDroolsRestException;
        }
        this.ruleTemplateManager.putTemplateTable(configuration.getDefaultPackageName(), templateRuleName, newTable);
    }

    @Override
    public void putTemplateTable(String packageName, String templateRuleName, Map<String, List<String>> table) throws ChtijbugDroolsRestException {
        this.ruleTemplateManager.putTemplateTable(packageName, templateRuleName, table);
    }

    @Override
    public List<Asset> getAllBusinessAssets() throws ChtijbugDroolsRestException {
        if (configuration.getDefaultPackageName() == null || configuration.getDefaultPackageName().length() == 0) {
            ChtijbugDroolsRestException chtijbugDroolsRestException = new ChtijbugDroolsRestException("No Default Package Name defined");
            throw chtijbugDroolsRestException;
        }
        return this.assetManager.getAllBusinessAssets(configuration.getDefaultPackageName());
    }

    @Override
    public List<Asset> getAllBusinessAssets(String packageName) throws ChtijbugDroolsRestException {
        return this.assetManager.getAllBusinessAssets(packageName);
    }

    @Override
    public List<Asset> getAllPackagesInGuvnorRepo() {
        return this.assetManager.getAllPackagesInGuvnorRepo();
    }


    @Override
    public void changeAssetPropertyValue(String assetName, AssetPropertyType assetPropertyType, String propertyValue) throws ChtijbugDroolsRestException {
        if (configuration.getDefaultPackageName() == null || configuration.getDefaultPackageName().length() == 0) {
            ChtijbugDroolsRestException chtijbugDroolsRestException = new ChtijbugDroolsRestException("No Default Package Name defined");
            throw chtijbugDroolsRestException;
        }
        this.assetManager.changeAssetPropertyValue(configuration.getDefaultPackageName(), assetName, assetPropertyType, propertyValue);

    }

    @Override
    public void changeAssetPropertyValue(String packageName, String assetName, AssetPropertyType assetPropertyType, String propertyValue) {
        this.assetManager.changeAssetPropertyValue(packageName, assetName, assetPropertyType, propertyValue);
    }

    @Override
    public void createAsset(Asset asset, AssetType assetType, String assetSource) throws ChtijbugDroolsRestException {
        if (configuration.getDefaultPackageName() == null || configuration.getDefaultPackageName().length() == 0) {
            ChtijbugDroolsRestException chtijbugDroolsRestException = new ChtijbugDroolsRestException("No Default Package Name defined");
            throw chtijbugDroolsRestException;
        }
        org.drools.guvnor.server.jaxrs.providers.atom.Entry output = this.assetManager.createAsset(configuration.getDefaultPackageName(), asset, assetType);
        this.assetManager.updateAssetCodeFromXML(configuration.getDefaultPackageName(), output.getTitle(), assetSource);
    }

    @Override
    public void createAsset(String packageName, Asset asset, AssetType assetType, String assetSource) throws ChtijbugDroolsRestException {
        org.drools.guvnor.server.jaxrs.providers.atom.Entry output = this.assetManager.createAsset(packageName, asset, assetType);
        this.assetManager.updateAssetCodeFromXML(packageName, output.getTitle(), assetSource);
    }

    public Integer getAssetVersion(String assetName) throws ChtijbugDroolsRestException {

        return this.getAssetVersion(configuration.getDefaultPackageName(), assetName);
    }

    public Integer getAssetVersion(String packageName, String assetName) throws ChtijbugDroolsRestException {
        if (configuration.getDefaultPackageName() == null || configuration.getDefaultPackageName().length() == 0) {
            ChtijbugDroolsRestException chtijbugDroolsRestException = new ChtijbugDroolsRestException("No Default Package Name defined");
            throw chtijbugDroolsRestException;
        }
        Asset asset = this.assetManager.getAsset(packageName, assetName);
        Integer assetVersion = null;
        if (asset != null && asset.getVersionNumber() != null) {
            assetVersion = new Integer(asset.getVersionNumber());
        }
        return assetVersion;
    }

    @Override
    public void buildRulePackageByStatus(String snapshotName, String filter) throws ChtijbugDroolsRestException {
        if (configuration.getDefaultPackageName() == null || configuration.getDefaultPackageName().length() == 0) {
            ChtijbugDroolsRestException chtijbugDroolsRestException = new ChtijbugDroolsRestException("No Default Package Name defined");
            throw chtijbugDroolsRestException;
        }
        this.rulePackageManager.buildRulePackageByStatus(configuration.getDefaultPackageName(), snapshotName, filter);
    }

    @Override
    public void buildRulePackageByStatus(String packageName, String snapshotName, String filter) throws ChtijbugDroolsRestException {
        this.rulePackageManager.buildRulePackageByStatus(packageName, snapshotName, filter);
    }

    @Override
    public void deletePackageSnapshot(String snapshotName) throws ChtijbugDroolsRestException {
        if (configuration.getDefaultPackageName() == null || configuration.getDefaultPackageName().length() == 0) {
            ChtijbugDroolsRestException chtijbugDroolsRestException = new ChtijbugDroolsRestException("No Default Package Name defined");
            throw chtijbugDroolsRestException;
        }
        this.rulePackageManager.deletePackageSnapshot(configuration.getDefaultPackageName(), snapshotName);
    }

    @Override
    public void deletePackageSnapshot(String packageName, String snapshotName) throws ChtijbugDroolsRestException {
        this.rulePackageManager.deletePackageSnapshot(packageName, snapshotName);
    }

    @Override
    public List<Snapshot> getListSnapshots() throws ChtijbugDroolsRestException {
        if (configuration.getDefaultPackageName() == null || configuration.getDefaultPackageName().length() == 0) {
            ChtijbugDroolsRestException chtijbugDroolsRestException = new ChtijbugDroolsRestException("No Default Package Name defined");
            throw chtijbugDroolsRestException;
        }
        return this.rulePackageManager.getListSnaphots(configuration.getDefaultPackageName());
    }

    @Override
    public List<Snapshot> getListSnapshots(String packageName) throws ChtijbugDroolsRestException {
        return this.rulePackageManager.getListSnaphots(packageName);
    }

    public GuvnorConnexionConfiguration getConfiguration() {
        return configuration;
    }
}
