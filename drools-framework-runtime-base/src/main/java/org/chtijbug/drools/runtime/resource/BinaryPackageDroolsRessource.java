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

import org.drools.builder.ResourceType;
import org.drools.io.Resource;
import org.drools.io.ResourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class BinaryPackageDroolsRessource implements DroolsResource {
    static final Logger LOGGER = LoggerFactory.getLogger(BinaryPackageDroolsRessource.class);
    private Resource resource;
    private String fileName;

    public BinaryPackageDroolsRessource(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public Resource getResource() {
        if (resource != null) {
            return resource;
        }
        resource = ResourceFactory.newClassPathResource(fileName);
        return resource;
    }

    @Override
    public ResourceType getResourceType() {
        return ResourceType.PKG;
    }
}
