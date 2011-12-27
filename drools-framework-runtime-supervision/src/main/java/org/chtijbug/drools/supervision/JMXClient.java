/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chtijbug.drools.supervision;

import com.thoughtworks.xstream.XStream;
import javax.management.*;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import org.chtijbug.drools.entity.history.HistoryContainer;
import org.chtijbug.drools.runtime.mbeans.RuleBaseSupervisionMBean;
import org.chtijbug.drools.runtime.mbeans.StatefullSessionSupervisionMBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author nheron
 */
public class JMXClient implements NotificationListener {

    private static final String CONNECTOR_ADDRESS =
            "com.sun.management.jmxremote.localConnectorAddress";
    private MBeanServerConnection mbsc = null;
    private static Logger LOGGER = LoggerFactory.getLogger(JMXClient.class);
    private int port;
    private String serverName;
    private RuleBaseSupervisionMBean mbeanRuleProxy = null;
    private StatefullSessionSupervisionMBean mbeanSessionProxy = null;
    private ObjectName nameRuleBase=null;
    private ObjectName nameSession=null;
    private FireAllRulesListener listener = null;
    private XStream xstream = new XStream();

    public JMXClient(String serverName, int port) {
        this.port = port;
        this.serverName = serverName;
        try {

            //JMXServiceURL url = new   JMXServiceURL("rmi", serverName, port);
            JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://" + this.serverName + ":" + this.port + "/jmxrmi");
            JMXConnector jmxc = JMXConnectorFactory.connect(url, null);
            this.mbsc = jmxc.getMBeanServerConnection();
            this.nameRuleBase = new ObjectName(HistoryContainer.nameRuleBaseObjectName);
            this.nameSession = new ObjectName(HistoryContainer.nameSessionObjectName);
            this.mbeanRuleProxy = JMX.newMBeanProxy(mbsc, nameRuleBase, RuleBaseSupervisionMBean.class, true);
            this.mbeanSessionProxy = JMX.newMBeanProxy(mbsc, nameSession, StatefullSessionSupervisionMBean.class, true);

        } catch (Exception e) {
            LOGGER.error("Erreur Connection JMX", e);
        }
    }

    public void registerListener(FireAllRulesListener newListener) {
        
        try {
             mbsc.addNotificationListener(nameSession, this, null, null);
             this.listener = newListener;
        } catch (Exception e) {
            LOGGER.error("Cannot connect to notification", e);
        }
       
    }

    public static void main(String args[]) throws Exception {
        JMXClient client = new JMXClient("localhost", 20000);
        client.registerListener(new FireAllRulesListener() {

            @Override
            public void fireAllRules(HistoryContainer container) {
                System.out.println(container.toString());
            }
        });
        Thread.sleep(200000);
        System.out.println("OK");
    }

    @Override
    public void handleNotification(Notification notification, Object handback) {
        if (notification instanceof AttributeChangeNotification) {
            AttributeChangeNotification nn = (AttributeChangeNotification) notification;
            Object userObject = nn.getUserData();
            if (userObject != null && userObject instanceof String && this.listener != null) {
                String userString = (String) userObject;
                HistoryContainer historyContainer = (HistoryContainer) xstream.fromXML(userString);
                this.listener.fireAllRules(historyContainer);
            }
        }
    }
}
