package org.chtijbug.drools.entity;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * Date: 23/11/12
 * Time: 15:13
 * To change this template use File | Settings | File Templates.
 */
public class DroolsRuleFlowGroupObject implements Serializable {
    private int ruleFlowInstanceID;
    private String name;

    public DroolsRuleFlowGroupObject() {
    }

    public DroolsRuleFlowGroupObject(int ruleFlowInstanceID, String name) {
        this.ruleFlowInstanceID = ruleFlowInstanceID;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("DroolsRuleFlowGroupObject");
        sb.append("{ruleFlowInstanceID=").append(ruleFlowInstanceID);
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
