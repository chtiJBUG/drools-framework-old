package org.chtijbug.drools.runtime.resource;

import org.chtijbug.drools.common.file.FileHelper;
import org.drools.builder.ResourceType;
import org.drools.io.Resource;
import org.drools.io.ResourceFactory;

import java.io.InputStream;

/**
 * Created by IntelliJ IDEA.
 * Date: 23/11/12
 * Time: 16:15
 * To change this template use File | Settings | File Templates.
 */
public class Bpmn2DroolsResource implements DroolsResource {

    private final Resource resource;
    private String fileName;
    private String fileContent;

    public Bpmn2DroolsResource(Resource resource, String fileName, String fileContent) {
        this.resource = resource;
        this.fileName = fileName;
        this.fileContent = fileContent;
    }


    public static Bpmn2DroolsResource createClassPathResource(String path) {
        InputStream inputStream = DrlDroolsResource.class.getResourceAsStream("/"+path);
        String fileContent = FileHelper.getFileContent(inputStream) ;
        return new Bpmn2DroolsResource(ResourceFactory.newClassPathResource(path), path, fileContent);

    }

    public String getFileName() {
        return fileName;
    }

    public String getFileContent() {
        return fileContent;
    }

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
