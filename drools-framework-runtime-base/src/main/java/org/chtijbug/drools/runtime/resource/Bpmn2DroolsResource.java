/*
 * Copyright 2014 Pymma Software
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.chtijbug.drools.runtime.resource;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.chtijbug.drools.common.file.FileHelper;
import org.drools.builder.ResourceType;
import org.drools.io.Resource;
import org.drools.io.ResourceFactory;

import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Bpmn2DroolsResource implements DroolsResource {

    private final Resource resource;
    private String fileName;
    private String fileContent;

    public Bpmn2DroolsResource(Resource resource, String fileName, String fileContent) {
        this.resource = resource;
        this.fileName = fileName;
        this.fileContent = fileContent;
    }

    public static Bpmn2DroolsResource createFileSystemPathResource(String path) {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(path);
            String fileContent = FileHelper.getFileContent(inputStream);
            return new Bpmn2DroolsResource(ResourceFactory.newInputStreamResource(inputStream), path, fileContent);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DrlDroolsResource.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
           
        }
        return null;
    }

    public static Bpmn2DroolsResource createClassPathResource(String path) {
        ClassLoader classLoader = Thread.currentThread()
                .getContextClassLoader();
        InputStream inputStream;
        if (classLoader == null) {
            inputStream = DrlDroolsResource.class.getResourceAsStream("/" + path);
        } else {
            inputStream = classLoader.getResourceAsStream(path);
        }
        String fileContent = FileHelper.getFileContent(inputStream);
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
