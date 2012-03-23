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
 * @date 9 déc. 2011
 */
public class DrlDroolsResource implements DroolsResource {

    private final Resource resource;

    public DrlDroolsResource(Resource resource) {
        this.resource = resource;
    }

    public static DrlDroolsResource createClassPathResource(String path) {

        return new DrlDroolsResource(ResourceFactory.newClassPathResource(path));

    }

    public static DrlDroolsResource createFileResource(File f) {
        return new DrlDroolsResource(ResourceFactory.newFileResource(f));
    }

    public static DrlDroolsResource createUrlResource(String url) {
        return new DrlDroolsResource(ResourceFactory.newUrlResource(url));
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
