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

import org.apache.commons.io.IOUtils;
import org.kie.api.io.Resource;
import org.kie.api.io.ResourceType;
import org.kie.internal.io.ResourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Bertrand Gressier
 * @since 9 déc. 2011
 */
public class WorkbenchResource extends AbstractDroolsResource {
    /** Class Logger */
    private static Logger logger = LoggerFactory.getLogger(WorkbenchResource.class);
    /** the URL base part */
    private final String baseUrl;
    /** the username used for connecting to Guvnor remote application */
    private final String username;
    /** the password used for connecting to Guvnor remote application */
    private final String password;
    /** The wrapped Drools Resource */
    private Resource resource;


    public static WorkbenchResource createGuvnorResource(String guvnor_url, Artifact artifact, String guvnor_username, String guvnor_password) {
        return new WorkbenchResource(guvnor_url, artifact, guvnor_username, guvnor_password);
    }

    public WorkbenchResource(String baseUrl, Artifact artifact, String username, String password) {
        super(artifact);
        this.baseUrl = baseUrl;
        this.username = username;
        this.password = password;
    }

    /*
      * (non-Javadoc)
      *
      * @see org.chtijbug.drools.runtime.resource.DroolsResource#getResource()
      */
    @Override
    public Resource getResource() throws Exception {
        logger.debug(">> getResource");
        try {
            if (resource != null) {
                return resource;
            }
            String changeset = IOUtils.toString(this.getClass().getResourceAsStream("/changeset-template.xml"));
            //changeset = String.format(changeset, getWebResourceUrl(), this.username, this.password);
            resource = ResourceFactory.newByteArrayResource(changeset.getBytes());
            return resource;
        } finally {
            logger.debug("<< getResource", resource);
        }
    }

    /*
      * (non-Javadoc)
      *
      * @see
      * org.chtijbug.drools.runtime.resource.DroolsResource#getResourceType()
      */
    @Override
    public org.kie.api.io.ResourceType getResourceType() {
        return ResourceType.CHANGE_SET;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WorkbenchResource)) return false;

        WorkbenchResource that = (WorkbenchResource) o;

        if (!baseUrl.equals(that.baseUrl)) return false;
        if (!password.equals(that.password)) return false;
        if (!username.equals(that.username)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = baseUrl.hashCode();
        result = 31 * result + username.hashCode();
        result = 31 * result + password.hashCode();
        return result;
    }
}
