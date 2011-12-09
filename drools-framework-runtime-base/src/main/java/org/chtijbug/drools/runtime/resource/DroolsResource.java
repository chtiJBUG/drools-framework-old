package org.chtijbug.drools.runtime.resource;

import org.drools.builder.ResourceType;
import org.drools.io.Resource;


/**
 * @author Bertrand Gressier
 * @date 9 déc. 2011
 * 
 *
 */
public interface DroolsResource {

	
	
	/**
	 * @return resource to load in KnowledgeBuilder
	 */
	public Resource getResource();
	
	/**
	 * 
	 * @return ResourceType for KnowledgeBuilder 
	 */
	public ResourceType getResourceType();
	
	
}
