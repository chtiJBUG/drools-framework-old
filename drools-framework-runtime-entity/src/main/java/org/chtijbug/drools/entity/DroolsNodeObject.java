/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chtijbug.drools.entity;

import java.io.Serializable;

/**
 * @author nheron
 */
public class DroolsNodeObject implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 2149698078767524188L;
    protected String id;
    private String nodeType;

    /**
     *
     */
    public DroolsNodeObject() {
    }

    protected DroolsNodeObject(String id, String nodeType) {
        this.id = id;
        this.nodeType = nodeType;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DroolsNodeObject other = (DroolsNodeObject) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    public static DroolsNodeObject createDroolsNodeObject(String id, String nodeType) {
        return new DroolsNodeObject(id, nodeType);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("DroolsNodeObject");
        sb.append("{id='").append(id).append('\'');
        sb.append("nodeType='").append(nodeType).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
