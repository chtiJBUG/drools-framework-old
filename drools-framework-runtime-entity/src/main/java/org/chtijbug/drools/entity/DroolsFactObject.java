/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chtijbug.drools.entity;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * @author nheron
 */
public class DroolsFactObject implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 8185674445343213645L;
    private String fullClassName;
    private int hashCode;
    protected int version;
    private List<DroolsFactObjectAttribute> listfactObjectAttributes = new ArrayList<DroolsFactObjectAttribute>();
    private final transient Object realObject;
    private String realObject_JSON;

    /**
     *
     */
    public DroolsFactObject() {
        realObject = null;
    }

    public DroolsFactObject(Object realObject, int version) throws IOException {
        this.realObject = realObject;
        this.version = version;
        ObjectMapper mapper = new ObjectMapper();
        Writer strWriter = new StringWriter();
        mapper.writeValue(strWriter, realObject);
        this.realObject_JSON = strWriter.toString();
    }

    public String getRealObject_JSON() {
        return realObject_JSON;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("DroolsFactObject");
        sb.append("{fullClassName='").append(fullClassName).append('\'');
        sb.append(", hashCode=").append(hashCode);
        sb.append(", version=").append(version);
        sb.append(", listfactObjectAttributes=").append(listfactObjectAttributes);
        sb.append("attributes :\n");
        for (DroolsFactObjectAttribute foa : this.getListfactObjectAttributes()) {
            sb.append("- " + foa.getAttributeType() + " " + foa.getAttributeName() + "=" + foa.getAttributeValue() + "\n");
        }
        sb.append(", realObject=").append(realObject);
        sb.append('}');
        return sb.toString();
    }

    public int getObjectVersion() {
        return version;
    }

    public int getNextObjectVersion() {
        return version + 1;
    }

    public List<DroolsFactObjectAttribute> getListfactObjectAttributes() {
        return listfactObjectAttributes;
    }

    public void setListfactObjectAttributes(List<DroolsFactObjectAttribute> listfactObjectAttributes) {
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
    public Object getRealObject(){
        return realObject;
    }
}
