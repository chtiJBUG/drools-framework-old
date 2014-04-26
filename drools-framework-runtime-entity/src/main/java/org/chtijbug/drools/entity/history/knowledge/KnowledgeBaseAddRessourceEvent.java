package org.chtijbug.drools.entity.history.knowledge;

import org.chtijbug.drools.entity.history.DrlResourceFile;
import org.chtijbug.drools.entity.history.GuvnorResourceFile;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * Date: 08/01/14
 * Time: 15:42
 * To change this template use File | Settings | File Templates.
 */
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
