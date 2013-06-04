package org.chtijbug.drools.runtime.mapper;

import org.chtijbug.drools.entity.DroolsFactObject;
import org.chtijbug.drools.entity.DroolsFactObjectAttribute;
import org.chtijbug.drools.runtime.impl.RunTimeRelationTypes;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;

/**
 * Created by IntelliJ IDEA.
 * Date: 04/06/13
 * Time: 15:37
 * To change this template use File | Settings | File Templates.
 */
public class DroolsFactObjectMapper {

    public static Node map(DroolsFactObject droolsFactObject, GraphDatabaseService graphDb) {
        Node fact = graphDb.createNode();
        fact.setProperty("type", "DroolsFactObjet");
        fact.setProperty("fullClassName", droolsFactObject.getFullClassName());
        fact.setProperty("version", droolsFactObject.getObjectVersion());
        fact.setProperty("hashcode", droolsFactObject.getHashCode());
        fact.setProperty("realObject", droolsFactObject.getRealObject().toString());
        for (DroolsFactObjectAttribute droolsFactObjectAttribute : droolsFactObject.getListfactObjectAttributes()) {
            Node factAttribute = graphDb.createNode();
            factAttribute.setProperty("attributeName", droolsFactObjectAttribute.getAttributeName());
            factAttribute.setProperty("attributeValue", droolsFactObjectAttribute.getAttributeValue());
            factAttribute.setProperty("attributeType", droolsFactObjectAttribute.getAttributeType());
            fact.createRelationshipTo(factAttribute, RunTimeRelationTypes.IS_FACT_ATTRIBUTE);
        }

        return fact;
    }
}
