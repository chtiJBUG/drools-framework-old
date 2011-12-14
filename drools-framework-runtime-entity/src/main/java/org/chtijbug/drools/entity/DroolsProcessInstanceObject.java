/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chtijbug.drools.entity;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author nheron
 */
public class DroolsProcessInstanceObject {

    private final String id;
    private final DroolsProcessObject process;
    private final List<DroolsNodeInstanceObject> nodeInstances = new LinkedList<DroolsNodeInstanceObject>();
    
    public DroolsProcessInstanceObject(String id, DroolsProcessObject process) {
        this.id = id;
        this.process = process;
    }

    public String getId() {
        return id;
    }

    public DroolsProcessObject getProcess() {
        return process;
    }

    public List<DroolsNodeInstanceObject> getNodeInstances() {
        return nodeInstances;
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
    
}
