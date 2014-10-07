/*
 * Copyright 2014 Pymma Software
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.chtijbug.drools.supervision;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import org.chtijbug.drools.entity.history.HistoryContainer;
import org.chtijbug.drools.runtime.mbeans.ResultStructure;
import org.chtijbug.drools.runtime.mbeans.RuleBaseSupervisionMBean;
import org.chtijbug.drools.runtime.mbeans.StatefulSessionSupervisionMBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.*;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.util.HashMap;
import java.util.Map;

/**
 * @author nheron
 */
public class JMXClient implements NotificationListener {

    private static final String CONNECTOR_ADDRESS =
            "com.sun.management.jmxremote.localConnectorAddress";
    private MBeanServerConnection mbsc = null;
    private static Logger LOGGER = LoggerFactory.getLogger(JMXClient.class);
    private int port;
    private String serverName;
    private String user;
    private String password;
    private RuleBaseSupervisionMBean mbeanRuleProxy = null;
    private StatefulSessionSupervisionMBean mbeanSessionProxy = null;
    private ObjectName nameRuleBase = null;
    private ObjectName nameSession = null;
    private FireAllRulesListener listener = null;
    private XStream xstream = new XStream(new JettisonMappedXmlDriver());
    private JMXConnector jmxc;

    private JMXConnector getRemoteConnection(String host, int port, String user, String password) throws Exception {
        JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://" + host + ":" + port + "/jmxrmi");
        final Map<String, String[]> environment = new HashMap<String, String[]>();
        environment.put(JMXConnector.CREDENTIALS, new String[]{user, password});
        return JMXConnectorFactory.connect(url, environment);
    }

    public JMXClient(String host, int port, String user, String password) {
        this.port = port;
        this.serverName = host;
        this.user = user;
        this.password = password;
        jmxc = null;
        try {

            JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://" + this.serverName + ":" + this.port + "/jmxrmi");
            if (user == null) {
                jmxc = JMXConnectorFactory.connect(url, null);
            } else {
                final Map<String, String[]> environment = new HashMap<String, String[]>();
                environment.put(JMXConnector.CREDENTIALS, new String[]{user, password});
                jmxc = JMXConnectorFactory.connect(url, environment);
            }
            this.mbsc = jmxc.getMBeanServerConnection();
            this.nameRuleBase = new ObjectName(HistoryContainer.nameRuleBaseObjectName);
            this.nameSession = new ObjectName(HistoryContainer.nameSessionObjectName);
            this.mbeanRuleProxy = JMX.newMBeanProxy(mbsc, nameRuleBase, RuleBaseSupervisionMBean.class, true);
            this.mbeanSessionProxy = JMX.newMBeanProxy(mbsc, nameSession, StatefulSessionSupervisionMBean.class, true);

        } catch (Exception e) {
            LOGGER.error("Erreur Connection JMX", e);
        }
    }

    public RuleBaseSupervisionMBean getMbeanRuleProxy() {
        return mbeanRuleProxy;
    }

    public StatefulSessionSupervisionMBean getMbeanSessionProxy() {
        return mbeanSessionProxy;
    }

    public void close() throws Exception {
        this.jmxc.close();
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
        JMXClient client = new JMXClient("localhost", 8999, "admin", "admin");
        client.registerListener(new FireAllRulesListener() {

            @Override
            public void fireAllRules(ResultStructure container) {
                System.out.println(container.toString());
            }
        });
        for (int i = 0; i < 10000; i++) {
            Thread.sleep(200);
        }

        System.out.println("OK");
    }

    @Override
    public void handleNotification(Notification notification, Object handback) {
        if (notification instanceof AttributeChangeNotification) {
            AttributeChangeNotification nn = (AttributeChangeNotification) notification;
            Object userObject = nn.getUserData();
            if (userObject != null && userObject instanceof String && this.listener != null) {
                String userString = (String) userObject;
                ResultStructure historyContainer = (ResultStructure) xstream.fromXML(userString);
                this.listener.fireAllRules(historyContainer);
            }
        }
    }
}
