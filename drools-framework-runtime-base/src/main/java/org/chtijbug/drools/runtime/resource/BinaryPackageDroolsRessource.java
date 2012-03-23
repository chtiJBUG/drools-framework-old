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
 * Created by IntelliJ IDEA.
 * Date: 23/03/12
 * Time: 09:53
 * To change this template use File | Settings | File Templates.
 */
public class BinaryPackageDroolsRessource implements DroolsResource {
    static final Logger LOGGER = LoggerFactory.getLogger(BinaryPackageDroolsRessource.class);
    private Resource resource;
    private String fileName;

    public BinaryPackageDroolsRessource(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public Resource getResource() {
        if (resource != null) {
            return resource;
        }
        StringBuffer changesetxml = null;

        StringBuilder buff = new StringBuilder();
        buff.append(fileName);

        changesetxml = new StringBuffer();
        changesetxml.append("<change-set xmlns='http://drools.org/drools-5.0/change-set' \n xmlns:xs='http://www.w3.org/2001/XMLSchema-instance'> \n  \n <add> \n");// xs:schemaLocation='http://drools.org/drools-5.0/change-set
        // http://anonsvn.jboss.org/repos/labs/labs/jbossrules/trunk/drools-api/src/main/resources/change-set-1.0.0.xsd'
        changesetxml.append("<resource source='");
        changesetxml.append(buff.toString());
        changesetxml.append("' type='PKG' ");
        changesetxml.append("/> \n </add> \n </change-set>\n");
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

        resource = ResourceFactory.newFileResource(fxml);
        return resource;
    }

    @Override
    public ResourceType getResourceType() {
        return ResourceType.CHANGE_SET;
    }
}
