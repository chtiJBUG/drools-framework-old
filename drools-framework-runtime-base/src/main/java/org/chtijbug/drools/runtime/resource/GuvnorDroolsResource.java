/**
 *
 */
package org.chtijbug.drools.runtime.resource;

import org.drools.builder.ResourceType;
import org.drools.io.Resource;
import org.drools.io.ResourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/**
 * @author Bertrand Gressier
 * @date 9 déc. 2011
 */
public class GuvnorDroolsResource implements DroolsResource {

    static final Logger LOGGER = LoggerFactory.getLogger(GuvnorDroolsResource.class);

    private final String guvnor_url;
    private String guvnor_appName = "drools-guvnor";
    private final String guvnor_packageName;
    private String guvnor_packageVersion = "/LATEST";
    private final String guvnor_username;
    private final String guvnor_password;

    private Resource changesetResource;


    private GuvnorDroolsResource(String guvnor_url, String guvnor_packageName, String guvnor_username, String guvnor_password) {
        this.guvnor_url = guvnor_url;
        this.guvnor_packageName = guvnor_packageName;
        this.guvnor_username = guvnor_username;
        this.guvnor_password = guvnor_password;

    }


    private GuvnorDroolsResource(String guvnor_url, String guvnor_appName, String guvnor_packageName, String guvnor_username, String guvnor_password) {
        this(guvnor_url, guvnor_packageName, guvnor_username, guvnor_password);
        this.guvnor_appName = guvnor_appName;
    }

    public static GuvnorDroolsResource createGuvnorRessource(String guvnor_url, String guvnor_appName, String guvnor_packageName, String guvnor_packageVersion, String guvnor_username, String guvnor_password) {
        return new GuvnorDroolsResource(guvnor_url, guvnor_appName, guvnor_packageName, guvnor_packageVersion, guvnor_username, guvnor_password);
    }

    public GuvnorDroolsResource(String guvnor_url, String guvnor_appName, String guvnor_packageName, String guvnor_packageVersion, String guvnor_username, String guvnor_password) {
        this(guvnor_url, guvnor_appName, guvnor_packageVersion, guvnor_username, guvnor_password);

        this.guvnor_packageVersion = guvnor_packageVersion;
    }

    /*
      * (non-Javadoc)
      *
      * @see org.chtijbug.drools.runtime.resource.DroolsResource#getResource()
      */
    @Override
    public Resource getResource() {
        if (changesetResource != null) {
            return changesetResource;
        }

        StringBuffer changesetxml = null;

        StringBuilder buff = new StringBuilder();
        buff.append(guvnor_url);
        buff.append(guvnor_appName);
        buff.append("/org.drools.guvnor.Guvnor/package/");
        buff.append(guvnor_packageName);
        buff.append(guvnor_packageVersion);

        changesetxml = new StringBuffer();
        changesetxml.append("<change-set xmlns='http://drools.org/drools-5.0/change-set' \n xmlns:xs='http://www.w3.org/2001/XMLSchema-instance'> \n  \n <add> \n");// xs:schemaLocation='http://drools.org/drools-5.0/change-set
        // http://anonsvn.jboss.org/repos/labs/labs/jbossrules/trunk/drools-api/src/main/resources/change-set-1.0.0.xsd'
        changesetxml.append("<resource source='");
        changesetxml.append(buff.toString());
        changesetxml.append("' type='PKG' basicAuthentication=\"enabled\" username=\"");
        changesetxml.append(guvnor_username);
        changesetxml.append("\" password=\"");
        changesetxml.append(guvnor_password);
        changesetxml.append("\" /> \n </add> \n </change-set>\n");
        File fxml = null;
        try {

            fxml = File.createTempFile("ChangeSet", ".xml");
            fxml.deleteOnExit();

            BufferedWriter output = new BufferedWriter(new FileWriter(fxml));
            output.write(changesetxml.toString());
            output.close();
        } catch (Exception e) {
            LOGGER.error("Error creating file {}", fxml);
        }

        changesetResource = ResourceFactory.newFileResource(fxml);
        return changesetResource;
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
}
