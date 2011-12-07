/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chtijbug.drools.runtime.impl;

import java.io.*;
import java.util.Properties;
import org.chtijbug.drools.runtime.RuleBasePackage;
import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.Resource;
import org.drools.io.ResourceFactory;

/**
 *
 * @author nheron
 */
public class RuleBaseSingleton implements RuleBasePackage{
     private KnowledgeBase kbase = null;
     
     private void loadKAgent(){
                 StringBuffer changesetxml = null;
        try {
            Properties properties = new Properties();
            try {
                InputStream ff = this.getClass().getClassLoader().getResourceAsStream("guvnor-connector.properties");
                properties.load(ff);
            } catch (IOException e) {
                e.printStackTrace();
            }
            StringBuilder buff = new StringBuilder();
            buff.append(properties.getProperty("guvnor.url"));
            buff.append(properties.getProperty("guvnor.guvnorAppName"));
            buff.append("/org.drools.guvnor.Guvnor/package/");
            buff.append(properties.getProperty("guvnor.packageName"));
            buff.append("/LATEST");


            changesetxml = new StringBuffer();
            changesetxml.append("<change-set xmlns='http://drools.org/drools-5.0/change-set' \n xmlns:xs='http://www.w3.org/2001/XMLSchema-instance'> \n  \n <add> \n");//xs:schemaLocation='http://drools.org/drools-5.0/change-set http://anonsvn.jboss.org/repos/labs/labs/jbossrules/trunk/drools-api/src/main/resources/change-set-1.0.0.xsd' 
            changesetxml.append("<resource source='");
            changesetxml.append(buff.toString());
            changesetxml.append("' type='PKG' basicAuthentication=\"enabled\" username=\"");
            changesetxml.append(properties.getProperty("guvnor.userName"));
            changesetxml.append("\" password=\"");
            changesetxml.append(properties.getProperty("guvnor.password"));
            changesetxml.append("\" /> \n </add> \n </change-set>\n");
            File fxml = null;
            try {

                fxml = new File("ChangeSet.xml");
                BufferedWriter output = new BufferedWriter(new FileWriter(fxml));
                output.write(changesetxml.toString());
                output.close();
            } catch (Exception e) {
            }

            KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
            //Resource res = ResourceFactory.newClassPathResource("ChangeSet.xml");
            Resource res = ResourceFactory.newFileResource(fxml);
            kbuilder.add(res, ResourceType.CHANGE_SET);
            kbase = KnowledgeBaseFactory.newKnowledgeBase();
            kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
            // kagent.applyChangeSet(rr);
        } catch (Exception e) {
            // e.printStackTrace();
            LOGGER.error("URL incorrect", changesetxml, e);
        }
     }
    
}
