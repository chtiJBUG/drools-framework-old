package org.chtijbug.drools.runtime;


import org.chtijbug.drools.runtime.Neo4jLogger.Neo4jListener;
import org.chtijbug.drools.runtime.impl.RuleBaseSingleton;
import org.chtijbug.drools.runtime.resource.Bpmn2DroolsRessource;
import org.chtijbug.drools.runtime.resource.DrlDroolsRessource;
import org.chtijbug.drools.runtime.resource.DroolsResource;
import org.chtijbug.drools.runtime.resource.GuvnorDroolsResource;
import org.neo4j.graphdb.GraphDatabaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by IntelliJ IDEA.
 * Date: 03/06/13
 * Time: 15:37
 * To change this template use File | Settings | File Templates.
 */
public class NeojLoggerRuleBaseBuilder {
    /**
         * Class Logger
         */
        private static Logger logger = LoggerFactory.getLogger(NeojLoggerRuleBaseBuilder.class);

        /**
         * @param guvnor_url
         * @param guvnor_appName
         * @param guvnor_packageName
         * @param guvnor_packageVersion
         * @param guvnor_username
         * @param guvnor_password
         * @return
         */
        public static RuleBasePackage createGuvnorRuleBasePackage(GraphDatabaseService graphDb,String guvnor_url, String guvnor_appName, String guvnor_packageName, String guvnor_packageVersion,
                                                                  String guvnor_username, String guvnor_password) throws DroolsChtijbugException {
            logger.debug(">> Neo4j createGuvnorRuleBasePackage(guvnor_url={}, guvnor_appName={},guvnor_packageName={},guvnor_packageVersion={},guvnor_username={}, guvnor_password={})", guvnor_url, guvnor_appName, guvnor_packageName, guvnor_packageVersion, guvnor_username, guvnor_password);
            Neo4jListener neo4jListener = new Neo4jListener(graphDb);
            RuleBasePackage newRuleBasePackage = new RuleBaseSingleton(RuleBaseSingleton.DEFAULT_RULE_THRESHOLD,neo4jListener);
            try {
                GuvnorDroolsResource gdr = new GuvnorDroolsResource(guvnor_url, guvnor_appName, guvnor_packageName, guvnor_packageVersion, guvnor_username, guvnor_password);
                newRuleBasePackage.addDroolsResouce(gdr);
                newRuleBasePackage.createKBase();
                //_____ Returning the result
                return newRuleBasePackage;
            } finally {
                logger.debug("<< Neo4j createGuvnorRuleBasePackage", newRuleBasePackage);
            }
        }

        public static RuleBasePackage createPackageBasePackage( GraphDatabaseService graphDb,String... filenames) throws DroolsChtijbugException {
            logger.debug("createPackageBasePackage");
            Neo4jListener neo4jListener = new Neo4jListener(graphDb);
            RuleBasePackage ruleBasePackage = new RuleBaseSingleton(RuleBaseSingleton.DEFAULT_RULE_THRESHOLD,neo4jListener);
            try {
                for (String filename : filenames) {
                    String extensionName = getFileExtension(filename);
                    DroolsResource resource=null;
                    if ("DRL".equals(extensionName)){
                        resource= DrlDroolsRessource.createClassPathResource(filename);
                    }else if ("BPMN2".equals(extensionName)){
                        resource= Bpmn2DroolsRessource.createClassPathResource(filename);
                    }
                    if (resource!= null){
                        ruleBasePackage.addDroolsResouce(resource);
                    }else {
                        throw new DroolsChtijbugException(DroolsChtijbugException.UnknowFileExtension,filename,null) ;
                    }
                }
                ruleBasePackage.createKBase();
                //_____ Returning the result
                return ruleBasePackage;
            } finally {
                logger.debug("createPackageBasePackage", ruleBasePackage);
            }
        }

        private static String getFileExtension(String ressourceName) {
            int mid = ressourceName.lastIndexOf(".");
            String fileName = ressourceName.substring(0, mid);
            String ext = ressourceName.substring(mid + 1, ressourceName.length()).toUpperCase();
            return ext;
        }
}
