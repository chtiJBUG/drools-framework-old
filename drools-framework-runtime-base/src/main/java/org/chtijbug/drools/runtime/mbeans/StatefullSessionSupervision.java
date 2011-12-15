/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chtijbug.drools.runtime.mbeans;

import javax.management.AttributeChangeNotification;
import javax.management.MBeanNotificationInfo;
import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;
import org.chtijbug.drools.entity.history.HistoryContainer;

/**
 *
 * @author nheron
 */
public class StatefullSessionSupervision extends NotificationBroadcasterSupport implements StatefullSessionSupervisionMBean {

    private long averageTimeExecution;
    private long minTimeExecution=1000000;
    private long maxTimeExecution=0;
    private long totalTimeExecution;
    private long totalNumberRulesExecuted;
    private long averageRulesExecuted;
    private long minRulesExecuted=10000000;
    private long maxRulesExecuted=0;
    private long numberFireAllRulesExecuted;
    private double averageRuleThroughout;
    private double minRuleThroughout=1000000;
    private double maxRuleThroughout=0;

    public synchronized void fireAllRulesExecuted(long executionTime, long numberRulesExecuted, HistoryContainer historyContainer) {
        numberFireAllRulesExecuted++;
        if (numberRulesExecuted < minRulesExecuted) {
            minRulesExecuted = numberRulesExecuted;
        }
        if (numberRulesExecuted > maxRulesExecuted) {
            maxRulesExecuted = numberRulesExecuted;
        }
        totalNumberRulesExecuted = totalNumberRulesExecuted + numberRulesExecuted;
        averageRulesExecuted = totalNumberRulesExecuted/numberFireAllRulesExecuted;
        totalTimeExecution = totalTimeExecution + executionTime;
        double throughput = numberRulesExecuted / (executionTime/1000.0);
        if (throughput < minRuleThroughout) {
            minRuleThroughout = throughput;
        }
        if (throughput > maxRuleThroughout) {
            maxRuleThroughout = throughput;
        }
        averageRuleThroughout = totalNumberRulesExecuted / (totalTimeExecution/1000.0);
        if (executionTime < minTimeExecution){
            minTimeExecution = executionTime;
        }
        if (executionTime> maxTimeExecution){
            maxTimeExecution = executionTime;
        }
        averageTimeExecution = totalTimeExecution/numberFireAllRulesExecuted;
        Notification n =
                new AttributeChangeNotification(this,
                numberFireAllRulesExecuted,
                System.currentTimeMillis(),
                "FireAllRules",
                "History Event",
                "HistoryEvent",
                historyContainer,
                historyContainer);

        
        sendNotification(n);
    }

    @Override
    public long getAverageTimeExecution() {
        return averageTimeExecution;
    }

    @Override
    public long getMinTimeExecution() {
        return minTimeExecution;
    }

    @Override
    public long getMaxTimeExecution() {
        return maxTimeExecution;
    }

    @Override
    public long getAverageRulesExecuted() {
        return averageRulesExecuted;
    }

    @Override
    public long getMinRulesExecuted() {
        return minRulesExecuted;
    }

    @Override
    public long getMaxRulesExecuted() {
        return maxRulesExecuted;
    }

    @Override
    public double getAverageRuleThroughout() {
        return averageRuleThroughout;
    }

    @Override
    public double getMinRuleThroughout() {
        return minRuleThroughout;
    }

    @Override
    public double getMaxRuleThroughout() {
        return maxRuleThroughout;
    }

    @Override
    public MBeanNotificationInfo[] getNotificationInfo() {
        String[] types = new String[]{
            AttributeChangeNotification.ATTRIBUTE_CHANGE
        };
        String name = AttributeChangeNotification.class.getName();
        String description = "Fire All rules ";
        MBeanNotificationInfo info =
                new MBeanNotificationInfo(types, name, description);
        MBeanNotificationInfo info1 =
                new MBeanNotificationInfo(types, "toto", description);
        MBeanNotificationInfo info2 =
                new MBeanNotificationInfo(types, "zizi", description);
        return new MBeanNotificationInfo[]{info,info1,info2};
    }
}
