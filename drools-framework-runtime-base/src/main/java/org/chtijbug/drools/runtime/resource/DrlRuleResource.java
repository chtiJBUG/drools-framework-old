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


import com.google.common.base.Throwables;
import org.apache.commons.io.IOUtils;
import org.chtijbug.drools.entity.history.RuleResource;
import org.kie.api.io.Resource;
import org.kie.internal.io.ResourceFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by IntelliJ IDEA.
 * Date: 23/01/14
 * Time: 15:40
 * To change this template use File | Settings | File Templates.
 */
public class DrlRuleResource implements Serializable, RuleResource {
    private String content;
    private String path;
    private boolean bpmn2;
    private Resource resource;

    public DrlRuleResource(String content, String path, boolean bpmn2, Resource resource) {
        this.content = content;
        this.path = path;
        this.bpmn2 = bpmn2;
        this.resource = resource;
    }

    public DrlRuleResource(Resource resource, String path, String fileContent) {
        this.content = fileContent;
        this.path = path;
        this.resource = resource;
        this.bpmn2=true;
    }

    public DrlRuleResource() {
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isBpmn2() {
        return bpmn2;
    }

    public void setBpmn2(boolean bpmn2) {
        this.bpmn2 = bpmn2;
    }

    public Resource getResource() {
        return resource;
    }

    public static DrlRuleResource createFileSystemPathResource(String path) {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(path);
            String fileContent = IOUtils.toString(inputStream);
            return new DrlRuleResource(ResourceFactory.newFileResource(path), path, fileContent);
        } catch (IOException ex) {
            Logger.getLogger(DrlRuleResource.class.getName()).log(Level.SEVERE, null, ex);
            throw Throwables.propagate(ex);
        }
    }

    public static DrlRuleResource createClassPathResource(String path) {
        ClassLoader classLoader = Thread.currentThread()
                .getContextClassLoader();
        InputStream inputStream;
        if (classLoader==null) {
            inputStream = DrlRuleResource.class.getResourceAsStream("/" + path);
        } else {
            inputStream = classLoader.getResourceAsStream(path);
        }
        String fileContent = null;
        try {
            fileContent = IOUtils.toString(inputStream);
        } catch (IOException ex) {
            Logger.getLogger(DrlRuleResource.class.getName()).log(Level.SEVERE, null, ex);
            throw Throwables.propagate(ex);
        }
        return new DrlRuleResource(ResourceFactory.newClassPathResource(path), path, fileContent);
    }
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("DrlRessourceFile{");
        sb.append("fileName='").append(path).append('\'');
        sb.append(", content='").append(content).append('\'');
        sb.append('}');
        return sb.toString();
    }

}
