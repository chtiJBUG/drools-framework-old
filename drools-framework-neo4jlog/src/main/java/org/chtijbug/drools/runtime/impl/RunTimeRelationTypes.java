package org.chtijbug.drools.runtime.impl;

import org.neo4j.graphdb.RelationshipType;

/**
 * Created by IntelliJ IDEA.
 * Date: 04/06/13
 * Time: 15:14
 * To change this template use File | Settings | File Templates.
 */
public enum RunTimeRelationTypes implements RelationshipType {

    INSERTED_OBJECT,
    OBJECT_OLD_VALUE,
    OBJECT_NEW_VALUE,
    DELETED_OBJECT,
    IS_FACT_ATTRIBUTE;
}

