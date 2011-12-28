/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chtijbug.drools.runtime.mbeans;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
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
    private long minTimeExecution = 1000000;
    private long maxTimeExecution = 0;
    private long totalTimeExecution;
    private long totalNumberRulesExecuted;
    private long averageRulesExecuted;
    private long minRulesExecuted = 10000000;
    private long maxRulesExecuted = 0;
    private long numberFireAllRulesExecuted;
    private double averageRuleThroughout;
    private double minRuleThroughout = 1000000;
    private double maxRuleThroughout = 0;
    //private HistoryContainer historyContainer;
    private MBeanNotificationInfo[] notificationInfo = null;
    private XStream xstream = new XStream(new JettisonMappedXmlDriver());

    public synchronized void fireAllRulesExecuted(long executionTime, long numberRulesExecuted, HistoryContainer historyContainer) {
        numberFireAllRulesExecuted++;
        if (numberRulesExecuted < minRulesExecuted) {
            minRulesExecuted = numberRulesExecuted;
        }
        if (numberRulesExecuted > maxRulesExecuted) {
            maxRulesExecuted = numberRulesExecuted;
        }
        totalNumberRulesExecuted = totalNumberRulesExecuted + numberRulesExecuted;
        averageRulesExecuted = totalNumberRulesExecuted / numberFireAllRulesExecuted;
        totalTimeExecution = totalTimeExecution + executionTime;
        double throughput = numberRulesExecuted / (executionTime / 1000.0);
        if (throughput < minRuleThroughout) {
            minRuleThroughout = throughput;
        }
        if (throughput > maxRuleThroughout) {
            maxRuleThroughout = throughput;
        }
        averageRuleThroughout = totalNumberRulesExecuted / (totalTimeExecution / 1000.0);
        if (executionTime < minTimeExecution) {
            minTimeExecution = executionTime;
        }
        if (executionTime > maxTimeExecution) {
            maxTimeExecution = executionTime;
        }
        averageTimeExecution = totalTimeExecution / numberFireAllRulesExecuted;
        // this.historyContainer = historyContainer;
        Notification n =
                new AttributeChangeNotification(this,
                numberFireAllRulesExecuted,
                System.currentTimeMillis(),
                "number of FireAllRules ",
                "HistoryContainer",
                String.class.getName(),
                "No",
                historyContainer.toString());
        xstream.setMode(XStream.NO_REFERENCES);
        String xml = xstream.toXML(historyContainer);
        n.setUserData(xml);
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

    // public HistoryContainer getHistoryContainer() {
    //     return historyContainer;
    //}
    public long getNumberFireAllRulesExecuted() {
        return numberFireAllRulesExecuted;
    }

    public long getTotalNumberRulesExecuted() {
        return totalNumberRulesExecuted;
    }

    public long getTotalTimeExecution() {
        return totalTimeExecution;
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

        notificationInfo = new MBeanNotificationInfo[]{
            new MBeanNotificationInfo(new String[]{"service.user.start"},
            Notification.class.getName(),
            "DateTime service start."),
            new MBeanNotificationInfo(new String[]{
                AttributeChangeNotification.ATTRIBUTE_CHANGE},
            AttributeChangeNotification.class.getName(),
            "Fire All rules attribute changes.")
        };



        return notificationInfo;
    }

    @Override
    public void resetStatistics() {
        this.minTimeExecution = 1000000;
        this.maxTimeExecution = 0;
        this.totalTimeExecution = 0;
        this.totalNumberRulesExecuted = 0;
        this.averageRulesExecuted = 0;
        this.minRulesExecuted = 10000000;
        this.maxRulesExecuted = 0;
        this.numberFireAllRulesExecuted = 0;
        this.averageRuleThroughout = 0;
        this.minRuleThroughout = 1000000;
        this.maxRuleThroughout = 0;
    }
}
