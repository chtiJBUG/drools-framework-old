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
package org.chtijbug.drools.entity.history;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * Date: 23/01/14
 * Time: 15:40
 * To change this template use File | Settings | File Templates.
 */
public class GuvnorResourceFile implements Serializable, ResourceFile {

    private String guvnor_url;
    private String guvnor_appName;
    private String guvnor_packageName;
    private String guvnor_packageVersion;
    private String guvnor_userName;
    private String guvnor_password;

    public GuvnorResourceFile() {
    }

    public GuvnorResourceFile(String guvnor_url, String guvnor_appName, String guvnor_packageName, String guvnor_packageVersion, String guvnor_userName, String guvnor_password) {
        this.guvnor_url = guvnor_url;
        this.guvnor_appName = guvnor_appName;
        this.guvnor_packageName = guvnor_packageName;
        this.guvnor_packageVersion = guvnor_packageVersion;
        this.guvnor_userName = guvnor_userName;
        this.guvnor_password = guvnor_password;
    }

    public String getGuvnor_url() {
        return guvnor_url;
    }

    public void setGuvnor_url(String guvnor_url) {
        this.guvnor_url = guvnor_url;
    }

    public String getGuvnor_appName() {
        return guvnor_appName;
    }

    public void setGuvnor_appName(String guvnor_appName) {
        this.guvnor_appName = guvnor_appName;
    }

    public String getGuvnor_packageName() {
        return guvnor_packageName;
    }

    public void setGuvnor_packageName(String guvnor_packageName) {
        this.guvnor_packageName = guvnor_packageName;
    }

    public String getGuvnor_packageVersion() {
        return guvnor_packageVersion;
    }

    public void setGuvnor_packageVersion(String guvnor_packageVersion) {
        this.guvnor_packageVersion = guvnor_packageVersion;
    }

    public String getGuvnor_userName() {
        return guvnor_userName;
    }

    public void setGuvnor_userName(String guvnor_userName) {
        this.guvnor_userName = guvnor_userName;
    }

    public String getGuvnor_password() {
        return guvnor_password;
    }

    public void setGuvnor_password(String guvnor_password) {
        this.guvnor_password = guvnor_password;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("GuvnorRessourceFile{");
        sb.append("guvnor_url='").append(guvnor_url).append('\'');
        sb.append(", guvnor_appName='").append(guvnor_appName).append('\'');
        sb.append(", guvnor_packageName='").append(guvnor_packageName).append('\'');
        sb.append(", guvnor_packageVersion='").append(guvnor_packageVersion).append('\'');
        sb.append(", guvnor_userName='").append(guvnor_userName).append('\'');
        sb.append(", guvnor_password='").append(guvnor_password).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
