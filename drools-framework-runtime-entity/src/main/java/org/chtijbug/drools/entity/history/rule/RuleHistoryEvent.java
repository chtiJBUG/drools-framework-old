/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chtijbug.drools.entity.history.rule;

import java.util.Date;
import org.chtijbug.drools.entity.history.HistoryEvent;

/**
 *
 * @author nheron
 */
public class RuleHistoryEvent extends HistoryEvent {
    private String ruleName;

    public RuleHistoryEvent(String ruleName) {

        super(new Date(), HistoryEvent.TypeEvent.Rule);
        this.ruleName = ruleName;
    }
}
