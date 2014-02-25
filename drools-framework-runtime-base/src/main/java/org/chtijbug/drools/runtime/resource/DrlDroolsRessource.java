/**
 *
 */
package org.chtijbug.drools.runtime.resource;

import org.chtijbug.drools.common.file.FileHelper;
import org.drools.builder.ResourceType;
import org.drools.io.Resource;
import org.drools.io.ResourceFactory;

import java.io.InputStream;

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

    public DrlDroolsRessource(Resource resource, String fileName, String fileContent) {
        this.fileContent = fileContent;
        this.fileName = fileName;
        this.resource = resource;
    }

    public static DrlDroolsRessource createClassPathResource(String path) {
        InputStream inputStream = DrlDroolsRessource.class.getResourceAsStream("/"+path);
        String fileContent = FileHelper.getFileContent(inputStream) ;
        return new DrlDroolsRessource(ResourceFactory.newClassPathResource(path), path, fileContent);
     }
    @Override
    public Resource getResource() throws Exception {

        return resource;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DrlDroolsRessource that = (DrlDroolsRessource) o;

        if (!fileName.equals(that.fileName)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return fileName.hashCode();
    }
}
