package org.chtijbug.drools.guvnor.rest;

import com.google.common.collect.Iterables;
import org.apache.cxf.jaxrs.client.ClientConfiguration;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.chtijbug.drools.common.log.Logger;
import org.chtijbug.drools.common.log.LoggerFactory;
import org.chtijbug.drools.guvnor.GuvnorConnexionConfiguration;
import org.chtijbug.drools.guvnor.rest.dt.DecisionTable;
import org.drools.ide.common.client.modeldriven.brl.RuleModel;
import org.drools.ide.common.client.modeldriven.brl.templates.InterpolationVariable;
import org.drools.ide.common.client.modeldriven.brl.templates.TemplateModel;
import org.drools.ide.common.client.modeldriven.dt52.GuidedDecisionTable52;
import org.drools.ide.common.server.util.BRXMLPersistence;
import org.drools.ide.common.server.util.GuidedDTXMLPersistence;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.google.common.collect.Lists.newArrayList;
import static java.lang.String.format;

public class GuvnorRepositoryConnector implements RestRepositoryConnector{
    /** Class logger */
    private static Logger logger = LoggerFactory.getLogger(GuvnorRepositoryConnector.class);
    /** Guvnor Web application connexion data */
    private final GuvnorConnexionConfiguration configuration;


    public GuvnorRepositoryConnector(GuvnorConnexionConfiguration configuration) {
        logger.debug(format("Creating new GuvnorRepositoryConnector with args : %s", configuration.toString()));
        this.configuration = configuration;
    }

    public GuvnorRepositoryConnector(String guvnorUrl,String guvnorAppName,String packageName,String guvnorUserName,String guvnorPassword) {
        this(new GuvnorConnexionConfiguration(guvnorUrl, guvnorAppName,packageName, guvnorUserName, guvnorPassword));
    }

    @Override
    public DecisionTable getGuidedDecisionTable(String dtName) throws ChtijbugDroolsRestException{
        String content = webClient().path(assetPath(dtName)).accept("text/plain").get(String.class);
        GuidedDecisionTable52 guidedDecisionTable52 = GuidedDTXMLPersistence.getInstance().unmarshal(content);
        return new DecisionTable(guidedDecisionTable52);
    }

    @Override
    public void commitChanges(DecisionTable guidedDecisionTable52) {
        String dtName = guidedDecisionTable52.getGuidedDecisionTable52().getTableName();
        String newContent = GuidedDTXMLPersistence.getInstance().marshal(guidedDecisionTable52.getGuidedDecisionTable52());
        WebClient client = webClient();
        noTimeout(client);
        client.path(assetPath(dtName)).accept("application/xml").put(newContent);
    }


    @Override
    public InputStream getPojoModel() {
        String path = this.configuration.getWebappName() + "/org.drools.guvnor.Guvnor/package/" +this.configuration.getPackageName() +"/LATEST/MODEL";
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
                .path(assetPath(templateRuleName))
                .type("application/xml")
                .put(xmlContent);

    }

    private void updateTableContent(Map<String, List<String>> newTable, TemplateModel templateModel) throws ChtijbugDroolsRestException {
        templateModel.clearRows();
        int rowCount = Iterables.get(newTable.values(), 1).size();
        List<String> columnNames = orderedColumnNames(templateModel);
        checkColumnNames(columnNames, newTable);
        for (int rowIndex=0; rowIndex < rowCount; rowIndex++) {
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

    private TemplateModel getTemplateModel(String templateRuleName) {
        String content = webClient()
                .path(assetPath(templateRuleName))
                .accept("text/plain")
                .get(String.class);
        RuleModel ruleModel = BRXMLPersistence.getInstance().unmarshal(content);
        return (TemplateModel) ruleModel;
    }

    private String assetPath(String ruleName) {
        return format("%s/rest/packages/%s/assets/%s/source", configuration.getWebappName(), configuration.getPackageName(), ruleName);
    }

    private WebClient webClient() {
        WebClient client = WebClient.create(configuration.getHostname());
        client.header("Authorization", configuration.createAuthenticationHeader());
        return client;
    }

    private void noTimeout(WebClient client) {
        ClientConfiguration config = WebClient.getConfig(client);
        HTTPConduit http = (HTTPConduit)config.getConduit();
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
