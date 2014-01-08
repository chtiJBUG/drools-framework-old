package org.chtijbug.drools.entity.history.knowledge;

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


    public KnowledgeBaseAddRessourceEvent(int eventID, Date dateEvent, int ruleBaseID,String baseUrl, String webappName,
                                          String packageName, String packageVersion) {
          super(eventID, dateEvent, ruleBaseID);
        this.setGuvnor_url(baseUrl);
        this.setGuvnor_appName(webappName);
        this.setGuvnor_packageName(packageName);
        this.setGuvnor_packageVersion(packageVersion);
      }
    public KnowledgeBaseAddRessourceEvent() {
    }
}
