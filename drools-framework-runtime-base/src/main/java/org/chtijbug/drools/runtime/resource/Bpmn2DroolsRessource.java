package org.chtijbug.drools.runtime.resource;

import org.drools.builder.ResourceType;
import org.drools.io.Resource;
import org.drools.io.ResourceFactory;

import java.io.File;

/**
 * Created by IntelliJ IDEA.
 * Date: 23/11/12
 * Time: 16:15
 * To change this template use File | Settings | File Templates.
 */
public class Bpmn2DroolsRessource  implements DroolsResource {

    private final Resource resource;

        public Bpmn2DroolsRessource(Resource resource) {
            this.resource = resource;
        }

        public static Bpmn2DroolsRessource createClassPathResource(String path) {

            return new Bpmn2DroolsRessource(ResourceFactory.newClassPathResource(path));

        }

        public static Bpmn2DroolsRessource createFileResource(File f) {
            return new Bpmn2DroolsRessource(ResourceFactory.newFileResource(f));
        }

        public static Bpmn2DroolsRessource createUrlResource(String url) {
            return new Bpmn2DroolsRessource(ResourceFactory.newUrlResource(url));
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
            return ResourceType.BPMN2;
        }
}
