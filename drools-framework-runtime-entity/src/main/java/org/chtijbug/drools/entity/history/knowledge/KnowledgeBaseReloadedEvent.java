package org.chtijbug.drools.entity.history.knowledge;

import org.chtijbug.drools.entity.history.GuvnorResourceFile;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * Date: 05/06/13
 * Time: 12:06
 * To change this template use File | Settings | File Templates.
 */
public class KnowledgeBaseReloadedEvent extends KnowledgeBaseEvent {
    public KnowledgeBaseReloadedEvent(int eventID, Date dateEvent, int ruleBaseID) {
        super(eventID, dateEvent, ruleBaseID);
    }

    public KnowledgeBaseReloadedEvent(int eventID, Date dateEvent, int ruleBaseID, String baseUrl, String webappName,
                                      String packageName, String packageVersion) {
        super(eventID, dateEvent, ruleBaseID);
        GuvnorResourceFile guvnorResourceFile = new GuvnorResourceFile(baseUrl, webappName, packageName, packageVersion, null, null);
            this.getResourceFiles().add(guvnorResourceFile);

    }


    public KnowledgeBaseReloadedEvent() {
    }
}
