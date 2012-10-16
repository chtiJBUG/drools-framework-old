package org.chtijbug.drools.guvnor.rest;

import org.apache.cxf.jaxrs.client.ClientConfiguration;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.chtijbug.drools.common.log.Logger;
import org.chtijbug.drools.common.log.LoggerFactory;
import org.chtijbug.drools.guvnor.GuvnorConnexionConfiguration;
import org.chtijbug.drools.guvnor.rest.dt.DecisionTable;
import org.drools.ide.common.client.modeldriven.dt52.GuidedDecisionTable52;
import org.drools.ide.common.server.util.GuidedDTXMLPersistence;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;

import static java.lang.String.format;

/**
 * Created by IntelliJ IDEA.
 * Date: 25/04/12
 * Time: 07:58
 * To change this template use File | Settings | File Templates.
 */
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
        WebClient client = WebClient.create(this.configuration.getHostname());
        client.header("Authorization", this.configuration.createAuthenticationHeader());
        String path =  getRESTPathForDecisionTable(dtName);
        String content = client.path(getRESTPathForDecisionTable(dtName)).accept("text/plain").get(String.class);
        GuidedDecisionTable52 guidedDecisionTable52 = GuidedDTXMLPersistence.getInstance().unmarshal(content);
        return new DecisionTable(guidedDecisionTable52);
    }

    @Override
    public void commitChanges(DecisionTable guidedDecisionTable52) {
        String dtName = guidedDecisionTable52.getGuidedDecisionTable52().getTableName();
        String newContent = GuidedDTXMLPersistence.getInstance().marshal(guidedDecisionTable52.getGuidedDecisionTable52());
        WebClient client = WebClient.create(this.configuration.getHostname());
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
        client.header("Authorization", this.configuration.createAuthenticationHeader());
        client.path(getRESTPathForDecisionTable(dtName)).accept("application/xml").put(newContent);
    }

    @Override
    public InputStream getPojoModel() {
        String path = this.configuration.getWebappName() + "/org.drools.guvnor.Guvnor/package/" +this.configuration.getPackageName() +"/LATEST/MODEL";
        WebClient client = WebClient.create(this.configuration.getHostname());
        client.header("Authorization", this.configuration.createAuthenticationHeader());
        Response stream = client.path(path).accept(MediaType.APPLICATION_OCTET_STREAM_TYPE).get();
        InputStream inputStream = (InputStream) stream.getEntity();
        return inputStream;
    }

    private String getRESTPathForDecisionTable(String dtName) {
        return this.configuration.getWebappName() + "/rest/packages/" + this.configuration.getPackageName() + "/assets/" + dtName + "/source";
    }
}
