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

import org.chtijbug.drools.common.file.FileHelper;
import org.kie.api.io.Resource;
import org.kie.api.io.ResourceType;
import org.kie.internal.io.ResourceFactory;

import java.io.InputStream;


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
        InputStream inputStream = DrlDroolsResource.class.getResourceAsStream("/" + path);
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

    @Override
    public InputStream getInputStream() {
        return null;
    }
}
