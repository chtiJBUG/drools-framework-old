package org.chtijbug.drools.entity.history.session;

import org.chtijbug.drools.entity.DroolsFactObject;

/**
 * Created by IntelliJ IDEA.
 * Date: 03/07/14
 * Time: 10:09
 * To change this template use File | Settings | File Templates.
 */
public class SessionFireAllRulesAndStartProcess extends SessionEvent {


    protected DroolsFactObject inputObject;
    protected DroolsFactObject outputObject;

    public SessionFireAllRulesAndStartProcess(int eventID, int ruleBaseId, int sessionId, DroolsFactObject inputObject, DroolsFactObject outputObject) {
        super(eventID, ruleBaseId, sessionId);
        this.inputObject = inputObject;
        this.outputObject = outputObject;
    }

    public SessionFireAllRulesAndStartProcess() {
    }
}
