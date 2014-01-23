/**
 *
 */
package org.chtijbug.drools.runtime.resource;

import com.google.common.base.Throwables;
import org.apache.commons.io.FileUtils;
import org.drools.builder.ResourceType;
import org.drools.io.Resource;
import org.drools.io.ResourceFactory;

import java.io.File;
import java.io.IOException;

/**
 * @author Bertrand Gressier
 * @date 9 déc. 2011
 */
public class DrlDroolsRessource implements DroolsResource {

    private final Resource resource;
    private String fileName;
    private String fileContent;

    public DrlDroolsRessource(Resource resource) {
        this.resource = resource;
    }
    public DrlDroolsRessource(Resource resource,String fileName,String fileContent) {
           this.fileContent = fileContent;
           this.fileName = fileName;
           this.resource = resource;
       }

    public static DrlDroolsRessource createClassPathResource(String path) {

        return new DrlDroolsRessource(ResourceFactory.newClassPathResource(path),path,null);

    }

    public static DrlDroolsRessource createFileResource(File f) {
        String fileContent=null;
        String fileName = null;
        try {
           fileContent = FileUtils.readFileToString(f) ;
           fileName = f.getAbsolutePath() ;
        } catch (IOException e) {
            throw  Throwables.propagate(e);
        }
        return new DrlDroolsRessource(ResourceFactory.newFileResource(f),fileName,fileContent);
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

    public String getFileName() {
        return fileName;
    }

    public String getFileContent() {
        return fileContent;
    }
}
