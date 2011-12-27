/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chtijbug.drools.runtime.mbeans;

import org.chtijbug.drools.entity.history.HistoryContainer;

/**
 *
 * @author nheron
 */
public interface StatefullSessionSupervisionMBean {

    public long getAverageTimeExecution();

    public long getMinTimeExecution();

    public long getMaxTimeExecution();

    public long getAverageRulesExecuted();

    public long getMinRulesExecuted();

    public long getMaxRulesExecuted();

    public double getAverageRuleThroughout();

    public double getMinRuleThroughout();

    public double getMaxRuleThroughout();

    public void resetStatistics();
}
