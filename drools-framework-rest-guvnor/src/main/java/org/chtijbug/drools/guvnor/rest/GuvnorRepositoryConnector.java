package org.chtijbug.drools.guvnor.rest;

import static java.lang.String.*;
import org.apache.cxf.common.util.Base64Utility;
import org.apache.cxf.jaxrs.client.ClientConfiguration;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.chtijbug.drools.guvnor.rest.dt.DecisionTable;
import org.drools.ide.common.client.modeldriven.dt52.GuidedDecisionTable52;
import org.drools.ide.common.server.util.GuidedDTXMLPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by IntelliJ IDEA.
 * Date: 25/04/12
 * Time: 07:58
 * To change this template use File | Settings | File Templates.
 */
public class GuvnorRepositoryConnector implements RestRepositoryConnector{
    /** Class logger */
    private static Logger logger = LoggerFactory.getLogger(GuvnorRepositoryConnector.class);
    /** The URL used for retrieving the Guvnor rule Package */
    private String url;
    /** Guvnor application name used while connecting to rules repository */
    private String guvnorAppName;
    /** Rule package name */
    private String packageName;
    private String clientPath;
    /** Authorisation header containing user and password for authentication to Guvnor */
    private String authorizationHeader;
    /** connection timeout for requesting the rule package binaries */
    private long connectionTimeout=0L;
    /** Reception timeout */
    private long receivedTimeout=0L;

    public GuvnorRepositoryConnector(String guvnorUrl,String guvnorAppName,String packageName,String guvnorUserName,String guvnorPassword) {
        if(logger.isDebugEnabled()) {
            logger.debug(format("Creating new GuvnorRepositoryConnector with args : %s, %s, %s", guvnorUrl, guvnorAppName,packageName));
        }
        this.url = guvnorUrl;
        this.guvnorAppName = guvnorAppName;
        this.packageName = packageName;
        this.clientPath = this.guvnorAppName + "/rest/packages/" + this.packageName + "/assets/";
        this.authorizationHeader = "Basic " + Base64Utility.encode((guvnorUserName + ":" + guvnorPassword).getBytes());
    }

    public long getReceivedTimeout() {
        return receivedTimeout;
    }

    public void setReceivedTimeout(long receivedTimeout) {
        this.receivedTimeout = receivedTimeout;
    }

    public long getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(long connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    @Override
    public DecisionTable getGuidedDecisionTable(String dtName) throws ChtijbugDroolsRestException{
        WebClient client = WebClient.create(this.url);
        client.header("Authorization", this.authorizationHeader);
        String content = client.path(this.clientPath + dtName + "/source").accept("text/plain").get(String.class);
        GuidedDecisionTable52 guidedDecisionTable52 = GuidedDTXMLPersistence.getInstance().unmarshal(content);
        return new DecisionTable(guidedDecisionTable52);
    }

    @Override
    public void commitChanges(DecisionTable guidedDecisionTable52) {
        String dtName = guidedDecisionTable52.getGuidedDecisionTable52().getTableName();
        String newContent = GuidedDTXMLPersistence.getInstance().marshal(guidedDecisionTable52.getGuidedDecisionTable52());
        WebClient client2 = WebClient.create(this.url);
        ClientConfiguration config = WebClient.getConfig(client2);
        HTTPConduit http = (HTTPConduit)config.getConduit();
        HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
        httpClientPolicy.setConnectionTimeout(connectionTimeout);
        httpClientPolicy.setReceiveTimeout(receivedTimeout);

        http.setClient(httpClientPolicy);

        client2.header("Authorization", this.authorizationHeader);
        client2.path(this.clientPath + dtName + "/source").accept("application/xml").put(newContent);

    }

}
