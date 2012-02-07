/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chtijbug.drools.entity;

import java.io.Serializable;

/**
 * @author nheron
 */
public class DroolsNodeInstanceObject implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 9121172218519979577L;
    protected String id;
    protected DroolsNodeObject node;

    /**
     *
     */
    public DroolsNodeInstanceObject() {
    }

    protected DroolsNodeInstanceObject(String id, DroolsNodeObject node) {
        this.id = id;
        this.node = node;
    }

    public String getId() {
        return id;
    }

    public DroolsNodeObject getNode() {
        return node;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DroolsNodeInstanceObject other = (DroolsNodeInstanceObject) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        if (this.node != other.node && (this.node == null || !this.node.equals(other.node))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 71 * hash + (this.node != null ? this.node.hashCode() : 0);
        return hash;
    }

    public static DroolsNodeInstanceObject createDroolsNodeInstanceObject(String nodeInstanceId, DroolsNodeObject nodeObject) {
        return new DroolsNodeInstanceObject(nodeInstanceId, nodeObject);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("DroolsNodeInstanceObject");
        sb.append("{id='").append(id).append('\'');
        sb.append(", node=").append(node.toString());
        sb.append('}');
        return sb.toString();
    }
}
