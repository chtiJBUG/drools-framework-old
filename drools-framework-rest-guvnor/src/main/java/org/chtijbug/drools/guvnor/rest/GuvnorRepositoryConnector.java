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
    /** the guvnor business assets package name **/
    private final String packageName;


    public GuvnorRepositoryConnector(GuvnorConnexionConfiguration configuration, String packageName) {
        logger.debug(format("Creating new GuvnorRepositoryConnector with args : %s, %s", configuration, packageName));
        this.configuration = configuration;
        this.packageName = packageName;
    }

    public GuvnorRepositoryConnector(String guvnorUrl,String guvnorAppName,String packageName,String guvnorUserName,String guvnorPassword) {
        this(new GuvnorConnexionConfiguration(guvnorUrl, guvnorAppName, guvnorUserName, guvnorPassword), packageName);
    }

    @Override
    public DecisionTable getGuidedDecisionTable(String dtName) throws ChtijbugDroolsRestException{
        WebClient client = WebClient.create(this.configuration.getHostname());
        client.header("Authorization", this.configuration.createAuthenticationHeader());
        String content = client.path(this.configuration.getRestAPIPathForPackage(this.packageName) + dtName + "/source").accept("text/plain").get(String.class);
        GuidedDecisionTable52 guidedDecisionTable52 = GuidedDTXMLPersistence.getInstance().unmarshal(content);
        return new DecisionTable(guidedDecisionTable52);
    }

    @Override
    public void commitChanges(DecisionTable guidedDecisionTable52) {
        String dtName = guidedDecisionTable52.getGuidedDecisionTable52().getTableName();
        String newContent = GuidedDTXMLPersistence.getInstance().marshal(guidedDecisionTable52.getGuidedDecisionTable52());
        WebClient client2 = WebClient.create(this.configuration.getHostname());
        ClientConfiguration config = WebClient.getConfig(client2);
        HTTPConduit http = (HTTPConduit)config.getConduit();
        HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
        /* connection timeout for requesting the rule package binaries */
        long connectionTimeout = 0L;
        httpClientPolicy.setConnectionTimeout(connectionTimeout);
        /* Reception timeout */
        long receivedTimeout = 0L;
        httpClientPolicy.setReceiveTimeout(receivedTimeout);

        http.setClient(httpClientPolicy);
        client2.header("Authorization", this.configuration.createAuthenticationHeader());
        client2.path(this.configuration.getRestAPIPathForPackage(this.packageName) + dtName + "/source").accept("application/xml").put(newContent);

    }

}
