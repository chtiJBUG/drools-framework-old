/**
 *
 */
package org.chtijbug.drools.runtime.resource;

import org.drools.builder.ResourceType;
import org.drools.io.Resource;
import org.drools.io.ResourceFactory;

import java.io.File;

/**
 * @author Bertrand Gressier
 * 9 déc. 2011
 */
public class DrlDroolsRessource implements DroolsResource {

    private final Resource resource;

    public DrlDroolsRessource(Resource resource) {
        this.resource = resource;
    }

    public static DrlDroolsRessource createClassPathResource(String path) {
        return new DrlDroolsRessource(ResourceFactory.newClassPathResource(path));

    }

    public static DrlDroolsRessource createFileResource(File f) {
        return new DrlDroolsRessource(ResourceFactory.newFileResource(f));
    }

    public static DrlDroolsRessource createUrlResource(String url) {
        return new DrlDroolsRessource(ResourceFactory.newUrlResource(url));
    }

    /*
      * (non-Javadoc)
      *
      * @see org.chtijbug.drools.runtime.resource.DroolsResource#getResource()
      */
    @Override
    public Resource getResource() throws Exception {

        return resource;
    }

    /*
      * (non-Javadoc)
      *
      * @see
      * org.chtijbug.drools.runtime.resource.DroolsResource#getResourceType()
      */
    @Override
    public ResourceType getResourceType() {
        return ResourceType.DRL;
    }
}
