/**
 *
 */
package org.chtijbug.drools.runtime.resource;

import org.apache.commons.io.IOUtils;
import org.drools.builder.ResourceType;
import org.drools.io.Resource;
import org.drools.io.ResourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Bertrand Gressier
 * @date 9 déc. 2011
 */
public class GuvnorDroolsResource implements DroolsResource {
    /** Class Logger */
    private static Logger logger = LoggerFactory.getLogger(GuvnorDroolsResource.class);
    /** Constant used for building the web resource URL */
    private static final String PKG_PARTIAL_URL = "org.drools.guvnor.Guvnor/package";

    /** the URL base part */
    private final String baseUrl;
    /** application name to connect */
    private String webappName = "drools-guvnor";
    /** package name */
    private final String packageName;
    /** The package version to fetch from remote application */
    private String packageVersion = "LATEST";
    /** the username used for connecting to Guvnor remote application */
    private final String username;
    /** the password used for connecting to Guvnor remote application */
    private final String password;
    /** The wrapepd Drools Resource */
    private Resource resource;


    public static GuvnorDroolsResource createGuvnorRessource(String guvnor_url, String guvnor_appName, String guvnor_packageName, String guvnor_packageVersion, String guvnor_username, String guvnor_password) {
        return new GuvnorDroolsResource(guvnor_url, guvnor_appName, guvnor_packageName, guvnor_packageVersion, guvnor_username, guvnor_password);
    }

    public GuvnorDroolsResource(String baseUrl, String webappName, String packageName, String packageVersion, String username, String password) {
        this.baseUrl = baseUrl;
        this.packageName = packageName;
        this.username = username;
        this.password = password;
        this.webappName = webappName;
        this.packageVersion = packageVersion;
    }

    /*
      * (non-Javadoc)
      *
      * @see org.chtijbug.drools.runtime.resource.DroolsResource#getResource()
      */
    @Override
    public Resource getResource() throws Exception {
        logger.debug(">>getResource");
        try {
            if (resource != null) {
                return resource;
            }
            String changeset = IOUtils.toString(this.getClass().getResourceAsStream("/changeset-template.xml"));
            changeset = String.format(changeset, getWebResourceUrl(), this.username, this.password);
            resource = ResourceFactory.newByteArrayResource(changeset.getBytes());
            return resource;
        } finally {
            logger.debug("<<getResource", resource);
        }
    }

    /*
      * (non-Javadoc)
      *
      * @see
      * org.chtijbug.drools.runtime.resource.DroolsResource#getResourceType()
      */
    @Override
    public ResourceType getResourceType() {
        return ResourceType.CHANGE_SET;
    }

    protected String getWebResourceUrl() {
        logger.debug(">>getWebResourceUrl");
        StringBuffer stringBuffer = new StringBuffer();
        try {
            stringBuffer.append(removeTrailingSlash(baseUrl)).append("/");
            stringBuffer.append(removeTrailingSlash(webappName)).append("/");
            stringBuffer.append(removeTrailingSlash(PKG_PARTIAL_URL)).append("/");
            stringBuffer.append(removeTrailingSlash(packageName)).append("/");
            stringBuffer.append(packageVersion);
            return stringBuffer.toString();
        } finally {
            logger.debug("<<getWebResourceUrl", stringBuffer);
        }
    }

    protected static String removeTrailingSlash(String toCheck) {
        if (toCheck.endsWith("/")) {
            return toCheck.substring(0, toCheck.length()-1);
        }
        return toCheck;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getWebappName() {
        return webappName;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getPackageVersion() {
        return packageVersion;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GuvnorDroolsResource)) return false;

        GuvnorDroolsResource that = (GuvnorDroolsResource) o;

        if (!baseUrl.equals(that.baseUrl)) return false;
        if (!packageName.equals(that.packageName)) return false;
        if (!packageVersion.equals(that.packageVersion)) return false;
        if (!webappName.equals(that.webappName)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = baseUrl.hashCode();
        result = 31 * result + webappName.hashCode();
        result = 31 * result + packageName.hashCode();
        result = 31 * result + packageVersion.hashCode();
        return result;
    }
}
