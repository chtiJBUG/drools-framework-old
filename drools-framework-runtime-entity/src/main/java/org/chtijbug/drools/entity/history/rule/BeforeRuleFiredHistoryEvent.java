package org.chtijbug.drools.entity.history.rule;

import org.chtijbug.drools.entity.DroolsFactObject;
import org.chtijbug.drools.entity.DroolsRuleObject;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * Date: 21/02/12
 * Time: 20:29
 * To change this template use File | Settings | File Templates.
 */
public class BeforeRuleFiredHistoryEvent extends RuleHistoryEvent {
    protected ArrayList<DroolsFactObject> whenObjects;

    /**
     *
     */
    public BeforeRuleFiredHistoryEvent() {
    }

    public BeforeRuleFiredHistoryEvent(int eventID,int ruleInstanceID,DroolsRuleObject rule) {

        super(eventID,ruleInstanceID,rule);
        this.whenObjects = new ArrayList<DroolsFactObject>();
    }

    public ArrayList<DroolsFactObject> getWhenObjects() {
        return whenObjects;
    }

    @Override
    public String toString() {

        StringBuilder str = new StringBuilder();
        str.append(super.toString() + "\n");

        str.append("When objects :\n");
        for (DroolsFactObject fact : whenObjects) {
            if (fact != null) {
                str.append("**" + fact.toString() + "\n");
            }
        }
        return str.toString();
    }
}
