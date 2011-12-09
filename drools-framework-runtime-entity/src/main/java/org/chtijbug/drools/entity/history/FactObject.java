/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chtijbug.drools.entity.history;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.beanutils.BeanMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author nheron
 */
public class FactObject {

    static final Logger LOGGER = LoggerFactory.getLogger(FactObject.class);
    private String fullClassName;
    private int hashCode;
    private int version;
    private List<FactObjectAttribute> listfactObjectAttributes = new ArrayList<FactObjectAttribute>();
    private Object realObject;

    public FactObject(Object realObject, int version) {
        this.realObject = realObject;
        this.version = version;
        this.fullClassName = realObject.getClass().getCanonicalName();
        this.hashCode = realObject.hashCode();
        try {
            this.introspect();
        } catch (Exception e) {
            LOGGER.error("Not possible to introspect {}", realObject);
        }

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

    private void introspect() throws Exception {
        BeanMap m = new BeanMap(this.realObject);
        for (Object para : m.keySet()) {
            if (!para.toString().equals("class")) {
                FactObjectAttribute attribute = new FactObjectAttribute(para.toString(), m.get(para).toString(), m.get(para).getClass().getSimpleName());
                this.listfactObjectAttributes.add(attribute);
            }

        }

    }

    public static FactObject createFactObject(Object o) {
        return createFactObject(o, 0);
    }

    public static FactObject createFactObject(Object o, int version) {
        FactObject createFactObject = null;
        if (o != null) {
            createFactObject = new FactObject(o, version);
        }
        return createFactObject;
    }
}
