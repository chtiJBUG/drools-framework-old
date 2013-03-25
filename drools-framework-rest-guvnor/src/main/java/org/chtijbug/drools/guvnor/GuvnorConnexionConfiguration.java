package org.chtijbug.drools.guvnor;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.cxf.common.util.Base64Utility;
import org.apache.cxf.jaxrs.client.ClientConfiguration;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

import static java.lang.String.format;

/**
 * Created with IntelliJ IDEA.
 * User: samuel
 * Date: 09/10/12
 * Time: 09:05
 */
public class GuvnorConnexionConfiguration {
    /** Hostname and port */
    private String hostname;
    /** Web application Name */
    private String webappName;
    /** The package name which contains all business assets */
    private String packageName;
    /** username for login*/
    private String username;
    /** password for login */
    private String password;

    public GuvnorConnexionConfiguration(String hostname, String webappName, String packageName, String username, String password) {
        this.hostname = hostname;
        this.webappName = webappName;
        this.packageName = packageName;
        this.username = username;
        this.password = password;
    }

    public String getHostname() {
        return hostname;
    }

    public String getWebappName() {
        return webappName;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    /**
     * This method will create authentication header frame containing Base 64 encoded username and password
     * @return the authentication header frame
     */
    public String createAuthenticationHeader() {
        return "Basic " + Base64Utility.encode((username + ":" + password).getBytes());
    }

    public WebClient webClient() {
        WebClient client = WebClient.create(this.getHostname());
        client.header("Authorization", this.createAuthenticationHeader());
        return client;
    }

    public String assetBinaryPath(String ruleName) {
         return getPathFor(ruleName, "source");
     }

     public String getPathFor(String assetName, String pathType) {
         return format("%s/rest/packages/%s/assets/%s/%s", this.getWebappName(), this.getPackageName(), assetName, pathType);
     }

     public String assetVersionPath(String assertName) {
         return getPathFor(assertName, "versions");
     }

    public void noTimeout(WebClient client) {
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


    @Override
    public String toString() {
        return new ToStringBuilder(this).
                append("hostname", hostname).
                append("webappName", webappName).
                append("username", username).
                append("password", password).
                toString();
    }


}
