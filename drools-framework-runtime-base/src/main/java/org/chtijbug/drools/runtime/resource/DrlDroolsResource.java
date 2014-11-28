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
import org.drools.builder.ResourceType;
import org.drools.io.Resource;
import org.drools.io.ResourceFactory;

import java.io.InputStream;


public class DrlDroolsResource implements DroolsResource {

    private final Resource resource;
    private String fileName;
    private String fileContent;

    public DrlDroolsResource(Resource resource) {
        this.resource = resource;
    }

    public DrlDroolsResource(Resource resource, String fileName, String fileContent) {
        this.fileContent = fileContent;
        this.fileName = fileName;
        this.resource = resource;
    }

    public static DrlDroolsResource createClassPathResource(String path) {
        InputStream inputStream = DrlDroolsResource.class.getResourceAsStream("/" + path);
        String fileContent = FileHelper.getFileContent(inputStream);
        return new DrlDroolsResource(ResourceFactory.newClassPathResource(path), path, fileContent);
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

        DrlDroolsResource that = (DrlDroolsResource) o;

        if (!fileName.equals(that.fileName)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return fileName.hashCode();
    }
}