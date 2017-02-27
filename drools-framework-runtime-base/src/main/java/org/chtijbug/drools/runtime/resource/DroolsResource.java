package org.chtijbug.drools.runtime.resource;

import org.drools.builder.ResourceType;
import org.drools.io.Resource;


/**
 * @author Bertrand Gressier
 * 9 déc. 2011
 */
public interface DroolsResource {


    /**
     * @return resource to load in KnowledgeBuilder
     */
    public Resource getResource() throws Exception;

    /**
     * @return ResourceType for KnowledgeBuilder
     */
    public ResourceType getResourceType();


}
