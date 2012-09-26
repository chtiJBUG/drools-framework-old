/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chtijbug.drools.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author nheron
 */
public class DroolsProcessInstanceObject implements Serializable {

    private static final long serialVersionUID = 5436434746711988139L;
    protected String id;
    private String name;
    private String packageName;
    private String type;
    private String version;
    protected DroolsProcessObject process;
    protected Map<String, DroolsNodeInstanceObject> nodeInstances;

    /**
     *
     */
    public DroolsProcessInstanceObject() {
    }

    protected DroolsProcessInstanceObject(String id, DroolsProcessObject process) {
        this.id = id;
        this.name = process.getName();
        this.packageName = process.getPackageName();
        this.type = process.getType();
        this.version = process.getVersion();
        this.process = process;
        nodeInstances = new HashMap<String, DroolsNodeInstanceObject>();
    }

    public String getId() {
        return id;
    }

    public DroolsProcessObject getProcess() {
        return process;
    }

    public void addDroolsNodeInstanceObject(DroolsNodeInstanceObject droolsNodeInstanceObject) {
        nodeInstances.put(droolsNodeInstanceObject.getId(), droolsNodeInstanceObject);
    }

    public DroolsNodeInstanceObject getDroolsNodeInstanceObjet(String id) {
        return nodeInstances.get(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DroolsProcessInstanceObject other = (DroolsProcessInstanceObject) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        if (this.process != other.process && (this.process == null || !this.process.equals(other.process))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 17 * hash + (this.process != null ? this.process.hashCode() : 0);
        return hash;
    }

    public static DroolsProcessInstanceObject createDroolsProcessInstanceObject(String processInstanceID, DroolsProcessObject process) {

        return new DroolsProcessInstanceObject(processInstanceID, process);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("DroolsProcessInstanceObject");
        sb.append("{id='").append(id).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", packageName='").append(packageName).append('\'');
        sb.append(", type='").append(type).append('\'');
        sb.append(", version='").append(version).append('\'');
        sb.append(", process=").append(process);
        sb.append(", nodeInstances=").append(nodeInstances);
        sb.append('}');
        return sb.toString();
    }
}
