package org.chtijbug.drools.guvnor.rest;

import ch.lambdaj.function.convert.Converter;
import com.google.common.collect.Iterables;
import org.apache.abdera.Abdera;
import org.apache.abdera.model.Document;
import org.apache.abdera.model.Element;
import org.apache.abdera.model.Entry;
import org.apache.abdera.model.Feed;
import org.apache.abdera.parser.stax.FOMExtensibleElement;
import org.apache.commons.io.IOUtils;
import org.apache.cxf.jaxrs.client.ClientConfiguration;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.chtijbug.drools.common.log.Logger;
import org.chtijbug.drools.common.log.LoggerFactory;
import org.chtijbug.drools.guvnor.GuvnorConnexionConfiguration;
import org.chtijbug.drools.guvnor.rest.dt.DecisionTable;
import org.chtijbug.drools.guvnor.rest.model.Asset;
import org.chtijbug.drools.guvnor.rest.model.AssetPropertyType;
import org.drools.ide.common.client.modeldriven.brl.RuleModel;
import org.drools.ide.common.client.modeldriven.brl.templates.InterpolationVariable;
import org.drools.ide.common.client.modeldriven.brl.templates.TemplateModel;
import org.drools.ide.common.client.modeldriven.dt52.GuidedDecisionTable52;
import org.drools.ide.common.server.util.BRXMLPersistence;
import org.drools.ide.common.server.util.GuidedDTXMLPersistence;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.*;

import static ch.lambdaj.Lambda.*;
import static com.google.common.collect.Lists.newArrayList;
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


    public GuvnorRepositoryConnector(GuvnorConnexionConfiguration configuration) {
        logger.debug(format("Creating new GuvnorRepositoryConnector with args : %s", configuration.toString()));
        this.configuration = configuration;
    }

    public GuvnorRepositoryConnector(String guvnorUrl, String guvnorAppName, String packageName, String guvnorUserName, String guvnorPassword) {
        this(new GuvnorConnexionConfiguration(guvnorUrl, guvnorAppName, packageName, guvnorUserName, guvnorPassword));
    }

    @Override
    public DecisionTable getGuidedDecisionTable(String dtName) throws ChtijbugDroolsRestException {
        String content = webClient().path(assetBinaryPath(dtName)).accept("text/plain").get(String.class);
        GuidedDecisionTable52 guidedDecisionTable52 = GuidedDTXMLPersistence.getInstance().unmarshal(content);
        return new DecisionTable(guidedDecisionTable52);
    }

    @Override
    public void commitChanges(DecisionTable guidedDecisionTable52) {
        String dtName = guidedDecisionTable52.getGuidedDecisionTable52().getTableName();
        String newContent = GuidedDTXMLPersistence.getInstance().marshal(guidedDecisionTable52.getGuidedDecisionTable52());
        WebClient client = webClient();
        noTimeout(client);
        client.path(assetBinaryPath(dtName)).accept("application/xml").put(newContent);
    }


    @Override
    public InputStream getPojoModel() {
        String path = this.configuration.getWebappName() + "/org.drools.guvnor.Guvnor/package/" + this.configuration.getPackageName() + "/LATEST/MODEL";
        WebClient client = webClient();
        Response stream = client.path(path).accept(MediaType.APPLICATION_OCTET_STREAM_TYPE).get();
        InputStream inputStream = (InputStream) stream.getEntity();
        return inputStream;
    }

    @Override
    public Map<String, List<String>> getTemplateTable(String templateRuleName) {
        TemplateModel templateModel = getTemplateModel(templateRuleName);
        Map<String, List<String>> table = templateModel.getTable();
        table.remove(TemplateModel.ID_COLUMN_NAME);
        return table;
    }

    @Override
    public void putTemplateTable(String templateRuleName, Map<String, List<String>> newTable) throws ChtijbugDroolsRestException {
        TemplateModel templateModel = getTemplateModel(templateRuleName);
        updateTableContent(newTable, templateModel);
        String xmlContent = BRXMLPersistence.getInstance().marshal(templateModel);
        webClient()
                .path(assetBinaryPath(templateRuleName))
                .type("application/xml")
                .put(xmlContent);

    }

    @Override
    public List<Asset> getAllBusinessAssets() {
        List<Asset> result;
        InputStream inputStream = webClient()
                .path(format("%s/rest/packages/%s/assets", configuration.getWebappName(), configuration.getPackageName()))
                .accept(MediaType.APPLICATION_ATOM_XML)
                .get(InputStream.class);
        Document<Element> atomDocument = Abdera.getInstance().getParser().parse(inputStream);
        Feed feed = (Feed) atomDocument.getRoot();
        final XPath xPath = XPathFactory.newInstance().newXPath();
        result = convert(feed.getEntries(), new Converter<Entry, Asset>() {
            @Override
            public Asset convert(Entry entry) {
                String assetName = entry.getTitle();
                Element metadata = entry.getExtension(QName.valueOf("metadata"));

                String metadataContent = ((FOMExtensibleElement) metadata).toFormattedString();
                String format = null;
                String status = null;
                try {
                    format = xPath.evaluate("/metadata/format/value", new InputSource(new StringReader(metadataContent)));
                    status = xPath.evaluate("/metadata/state/value", new InputSource(new StringReader(metadataContent))) ;
                } catch (XPathExpressionException e) {
                    //____ Let the null value by default
                }
                return new Asset(configuration.getPackageName(), assetName, status, format);
            }
        });
        return result;
    }

    @Override
    public void changeAssetPropertyValue(String assetName, AssetPropertyType assetPropertyType, String propertyValue) {
        String assetPath = format("%s/rest/packages/%s/assets/%s", configuration.getWebappName(), configuration.getPackageName(), assetName);
        //_____ Extract the current version of the asset
        InputStream inputStream = webClient()
                .path(assetPath)
                .accept(MediaType.APPLICATION_ATOM_XML)
                .get(InputStream.class);
        //_____ Replace the property value
        String newAssetVersion =  replacePropertyValueFromAtomXml(inputStream, assetPropertyType.name().toLowerCase(), propertyValue);
        if (newAssetVersion == null) {
            return;
        }
        //____ Put the new version of the Asset
        webClient()
                .path(assetPath)
                .type(MediaType.APPLICATION_ATOM_XML)
                .put(newAssetVersion);

    }

    private String replacePropertyValueFromAtomXml(InputStream inputStream, String propertyName, String propertyValue) {
        try {
            String xmlContent = IOUtils.toString(inputStream);
            org.w3c.dom.Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(xmlContent)));

            XPath xpath = XPathFactory.newInstance().newXPath();
            Node statusNode = (Node) xpath.evaluate("//metadata/"+propertyName+"/value[text()]", doc, XPathConstants.NODE);
            if (statusNode != null)
                statusNode.setTextContent(propertyValue);
            //___ Avoid updating categories --> setting it to blank (REST API does not support embedded categories)
            Node categoriesNode = (Node) xpath.evaluate("//metadata/categories", doc, XPathConstants.NODE);
            if(categoriesNode != null)
                categoriesNode.getParentNode().removeChild(categoriesNode);
            StringWriter writer = new StringWriter();
            Transformer xformer = TransformerFactory.newInstance().newTransformer();
            xformer.transform(new DOMSource(doc), new StreamResult(writer));
            return writer.toString();
        } catch (Exception e) {
            //____ if an exception occurs, return an empty String (No support at the moment)
            return null;
        }
    }

    private void updateTableContent(Map<String, List<String>> newTable, TemplateModel templateModel) throws ChtijbugDroolsRestException {
        templateModel.clearRows();
        int rowCount = Iterables.get(newTable.values(), 1).size();
        List<String> columnNames = orderedColumnNames(templateModel);
        checkColumnNames(columnNames, newTable);
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            List<String> row = newArrayList();
            for (String columnName : columnNames) {
                row.add(newTable.get(columnName).get(rowIndex));
            }
            templateModel.addRow(row.toArray(new String[row.size()]));
        }
    }

    private void checkColumnNames(List<String> columnNames, Map<String, List<String>> newTable) throws ChtijbugDroolsRestException {
        Set<String> newColumnNames = newTable.keySet();
        Set<String> columnNamesSet = new HashSet<String>(columnNames);
        if (!newColumnNames.equals(columnNamesSet)) {
            throw new ChtijbugDroolsRestException("Expected columns names : " + columnNames);
        }
    }

    private List<String> orderedColumnNames(TemplateModel templateModel) {
        List<String> columnNames = new ArrayList<String>();
        InterpolationVariable[] variablesList = templateModel.getInterpolationVariablesList();
        for (int i = 0; i < variablesList.length; i++) {
            columnNames.add(variablesList[i].getVarName());
        }
        return columnNames;
    }

    public Integer getAssetVersion(String assetName) {
        InputStream inputStream = webClient()
                .path(assetVersionPath(assetName))
                .accept(MediaType.APPLICATION_ATOM_XML)
                .get(InputStream.class);

        Document<Element> atomDocument = Abdera.getInstance().getParser().parse(inputStream);
        Feed feed = (Feed) atomDocument.getRoot();
        List<Integer> allVersions = convert(feed.getEntries(), new Converter<Entry, Integer>() {
            public Integer convert(Entry from) {
                return Integer.valueOf(from.getTitle());
            }
        });
        return selectMax(allVersions, on(Integer.class).intValue());
    }

    private TemplateModel getTemplateModel(String templateRuleName) {
        String content = webClient()
                .path(assetBinaryPath(templateRuleName))
                .accept("text/plain")
                .get(String.class);
        RuleModel ruleModel = BRXMLPersistence.getInstance().unmarshal(content);
        return (TemplateModel) ruleModel;
    }

    private String assetBinaryPath(String ruleName) {
        return getPathFor(ruleName, "source");
    }

    private String getPathFor(String assetName, String pathType) {
        return format("%s/rest/packages/%s/assets/%s/%s", configuration.getWebappName(), configuration.getPackageName(), assetName, pathType);
    }

    private String assetVersionPath(String assertName) {
        return getPathFor(assertName, "versions");
    }

    private WebClient webClient() {
        WebClient client = WebClient.create(configuration.getHostname());
        client.header("Authorization", configuration.createAuthenticationHeader());
        return client;
    }

    private void noTimeout(WebClient client) {
        ClientConfiguration config = WebClient.getConfig(client);
        HTTPConduit http = (HTTPConduit) config.getConduit();
        HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
        /* connection timeout for requesting the rule package binaries */
        long connectionTimeout = 0L;
        httpClientPolicy.setConnectionTimeout(connectionTimeout);
        /* Reception timeout */
        long receivedTimeout = 0L;
        httpClientPolicy.setReceiveTimeout(receivedTimeout);

        http.setClient(httpClientPolicy);
    }
}
