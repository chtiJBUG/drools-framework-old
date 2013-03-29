package org.chtijbug.drools.guvnor.rest;

import org.apache.cxf.jaxrs.client.WebClient;
import org.chtijbug.drools.common.jaxb.JAXBContextUtils;
import org.chtijbug.drools.common.log.Logger;
import org.chtijbug.drools.common.log.LoggerFactory;
import org.chtijbug.drools.guvnor.GuvnorConnexionConfiguration;
import org.chtijbug.drools.guvnor.rest.dt.DecisionTable;
import org.chtijbug.drools.guvnor.rest.model.Asset;
import org.chtijbug.drools.guvnor.rest.model.AssetPropertyType;
import org.chtijbug.drools.guvnor.rest.model.AssetType;
import org.drools.guvnor.server.jaxrs.jaxb.SnapshotCreationData;

import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBException;
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
    private final GuvnorConnexionConfiguration configuration;

    private AssetManager assetManager = null;

    private RuleTemplateManager ruleTemplateManager = null;

    private DecisionTableManager decisionTableManager = null;

    public GuvnorRepositoryConnector(GuvnorConnexionConfiguration configuration) {
        logger.debug(format("Creating new GuvnorRepositoryConnector with args : %s", configuration.toString()));
        this.configuration = configuration;
        this.assetManager = new AssetManager(configuration);
        this.ruleTemplateManager = new RuleTemplateManager(configuration, this.assetManager);
        this.decisionTableManager = new DecisionTableManager(configuration, this.assetManager);
    }

    public GuvnorRepositoryConnector(String guvnorUrl, String guvnorAppName, String packageName, String guvnorUserName, String guvnorPassword) {
        this(new GuvnorConnexionConfiguration(guvnorUrl, guvnorAppName, packageName, guvnorUserName, guvnorPassword));
    }

    @Override
    public DecisionTable getGuidedDecisionTable(String dtName) throws ChtijbugDroolsRestException {
        return this.decisionTableManager.getGuidedDecisionTable(dtName);
    }

    @Override
    public void commitChanges(DecisionTable guidedDecisionTable52) throws ChtijbugDroolsRestException {
        this.decisionTableManager.commitChanges(guidedDecisionTable52);
    }


    @Override
    public InputStream getPojoModel() {
        return this.assetManager.getPojoModel();
    }

    @Override
    public Map<String, List<String>> getTemplateTable(String templateRuleName) throws ChtijbugDroolsRestException {
        return this.ruleTemplateManager.getTemplateTable(templateRuleName);
    }

    @Override
    public void putTemplateTable(String templateRuleName, Map<String, List<String>> newTable) throws ChtijbugDroolsRestException {
        this.ruleTemplateManager.putTemplateTable(templateRuleName, newTable);
    }

    @Override
    public List<Asset> getAllBusinessAssets() {
        return this.assetManager.getAllBusinessAssets();
    }

    @Override
    public void changeAssetPropertyValue(String assetName, AssetPropertyType assetPropertyType, String propertyValue) {
        this.assetManager.changeAssetPropertyValue(assetName, assetPropertyType, propertyValue);

    }

    @Override
    public void createAsset(Asset asset, AssetType assetType, String assetSource) throws ChtijbugDroolsRestException {
        org.drools.guvnor.server.jaxrs.providers.atom.Entry output = this.assetManager.createAsset(asset, assetType);
        this.assetManager.updateAssetCodeFromXML(output.getTitle(), assetSource);
    }

    public Integer getAssetVersion(String assetName) {
        return this.assetManager.getAssetVersion(assetName);
    }

    @Override
    public void buildRulePackageByStatus(String snapshotName, String filter) throws ChtijbugDroolsRestException {
        SnapshotCreationData snapshotCreationData = new SnapshotCreationData();
        snapshotCreationData.setBuildMode("BuiltInSelector");
        snapshotCreationData.setEnableStatusSelector(true);
        snapshotCreationData.setStatusOperator("=");
        snapshotCreationData.setStatusDescriptionValue(filter);
        try {
            String path = format("%s/rest/packages/%s/snapshot/%s", this.configuration.getWebappName(), this.configuration.getPackageName(), snapshotName);

            String xmlObject = JAXBContextUtils.marshallObjectAsString(SnapshotCreationData.class, snapshotCreationData);
            WebClient webClient = this.configuration.webClient();
            this.configuration.noTimeout(webClient);
            webClient.path(path)
                    .type(MediaType.APPLICATION_XML_TYPE)
                    .post(xmlObject);
        } catch (JAXBException e) {
            throw new ChtijbugDroolsRestException(e);
        }


    }
}
