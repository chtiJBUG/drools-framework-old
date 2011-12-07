/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chtijbug.drools.entity.history;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nheron
 */
public class FactObject {

    private String fullClassName;
    private int hashCode;
    private int version;
    private List<FactObjectAttribute> listfactObjectAttributes = new ArrayList<FactObjectAttribute>();

    public FactObject(String fullClassName, int hashCode) {
        this.fullClassName = fullClassName;
        this.hashCode = hashCode;
        this.version = 0;
    }

    public FactObject(String fullClassName, int hashCode, int version) {
        this.fullClassName = fullClassName;
        this.hashCode = hashCode;
        this.version = version;
    }

    public int getObjectVersion() {
        return version;
    }

    public int getNextObjectVersion() {
        return version + 1;
    }

    public List<FactObjectAttribute> getListfactObjectAttributes() {
        return listfactObjectAttributes;
    }

    public void setListfactObjectAttributes(List<FactObjectAttribute> listfactObjectAttributes) {
        this.listfactObjectAttributes = listfactObjectAttributes;
    }

    public String getFullClassName() {
        return fullClassName;
    }

    public void setFullClassName(String fullClassName) {
        this.fullClassName = fullClassName;
    }

    public int getHashCode() {
        return hashCode;
    }

    public void setHashCode(int hashCode) {
        this.hashCode = hashCode;
    }

    public static FactObject createFactObject(Object o) {
        return createFactObject(o, 0);
    }

    public static FactObject createFactObject(Object o, int version) {
        FactObject createFactObject = null;
        if (o != null) {
            createFactObject = new FactObject(o.getClass().getCanonicalName(), o.hashCode(), version);
        }
        return createFactObject;
    }
}
