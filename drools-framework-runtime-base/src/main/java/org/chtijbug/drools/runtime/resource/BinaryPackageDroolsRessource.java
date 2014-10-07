/*
 * Copyright 2014 Pymma Software
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.chtijbug.drools.runtime.resource;

import org.drools.builder.ResourceType;
import org.drools.io.Resource;
import org.drools.io.ResourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


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
        /*
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
        */
        resource = ResourceFactory.newClassPathResource(fileName);
        return resource;
    }

    @Override
    public ResourceType getResourceType() {
        return ResourceType.PKG;
    }
}
