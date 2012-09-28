/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chtijbug.drools.supervision;

import org.chtijbug.drools.runtime.mbeans.ResultStructure;

/**
 *
 * @author nheron
 */
public interface FireAllRulesListener {
    
    public void fireAllRules (ResultStructure container);
    
}
