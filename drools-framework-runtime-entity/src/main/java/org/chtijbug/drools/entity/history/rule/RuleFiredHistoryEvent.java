/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chtijbug.drools.entity.history.rule;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.chtijbug.drools.entity.history.FactObject;

/**
 *
 * @author nheron
 */
public class RuleFiredHistoryEvent extends RuleHistoryEvent {

    private final ArrayList<FactObject> whenObjects;

    public RuleFiredHistoryEvent(String ruleName) {
        super(ruleName);
        this.whenObjects = new ArrayList<FactObject>();
    }

    public ArrayList<FactObject> getWhenObjects() {
        return whenObjects;
    }
    
}
