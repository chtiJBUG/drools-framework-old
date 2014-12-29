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
package org.chtijbug.drools.entity.history.knowledge;

import org.chtijbug.drools.entity.history.DrlResourceFile;
import org.chtijbug.drools.entity.history.GuvnorResourceFile;

import java.util.Date;

public class KnowledgeBaseAddRessourceEvent extends KnowledgeBaseEvent {

    public KnowledgeBaseAddRessourceEvent(int eventID, Date dateEvent, int ruleBaseID) {
        super(eventID, dateEvent, ruleBaseID);
    }


    public KnowledgeBaseAddRessourceEvent(int eventID, Date dateEvent, int ruleBaseID, String baseUrl, String webappName,
                                          String packageName, String packageVersion) {
        super(eventID, dateEvent, ruleBaseID);
        GuvnorResourceFile guvnorResourceFile = new GuvnorResourceFile(baseUrl, webappName, packageName, packageVersion, null, null);
        this.getResourceFiles().add(guvnorResourceFile);
    }

    public KnowledgeBaseAddRessourceEvent(int eventID, Date dateEvent, int ruleBaseID, String fileName, String content) {
        super(eventID, dateEvent, ruleBaseID);
        DrlResourceFile drlRessourceFile = new DrlResourceFile();
        drlRessourceFile.setFileName(fileName);
        drlRessourceFile.setContent(content);
        this.getResourceFiles().add(drlRessourceFile);

    }

    public KnowledgeBaseAddRessourceEvent() {
    }
}