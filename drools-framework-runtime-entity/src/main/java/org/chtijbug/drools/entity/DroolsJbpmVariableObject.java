package org.chtijbug.drools.entity;

import java.io.Serializable;


public class DroolsJbpmVariableObject implements Serializable{
    private String variableId;
    private String variableInstanceId;
    private final transient Object variableValue;

    public DroolsJbpmVariableObject() {
        this.variableValue=null;
    }

    public DroolsJbpmVariableObject(String variableId, String variableInstanceId, Object variableValue) {
        this.variableId = variableId;
        this.variableInstanceId = variableInstanceId;
        this.variableValue = variableValue;
    }

    public String getVariableInstanceId() {
        return variableInstanceId;
    }

    public void setVariableInstanceId(String variableInstanceId) {
        this.variableInstanceId = variableInstanceId;
    }

    public String getVariableId() {
        return variableId;
    }

    public void setVariableId(String variableId) {
        this.variableId = variableId;
    }

    public Object getVariableValue() {
        return variableValue;
    }



    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("DroolsJbpmVariableObject{");
        sb.append("variableId='").append(variableId).append('\'');
        sb.append(", variableValue=").append(variableValue);
        sb.append('}');
        return sb.toString();
    }
}
