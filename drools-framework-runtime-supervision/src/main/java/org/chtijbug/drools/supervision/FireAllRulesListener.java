/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chtijbug.drools.supervision;

import org.chtijbug.drools.entity.history.HistoryContainer;

/**
 *
 * @author nheron
 */
public interface FireAllRulesListener {
    
    public void fireAllRules (HistoryContainer container);
    
}
