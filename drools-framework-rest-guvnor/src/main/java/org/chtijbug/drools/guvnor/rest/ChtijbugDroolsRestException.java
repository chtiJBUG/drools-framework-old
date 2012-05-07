package org.chtijbug.drools.guvnor.rest;

/**
 * Created by IntelliJ IDEA.
 * Date: 26/04/12
 * Time: 14:51
 * To change this template use File | Settings | File Templates.
 */
public class ChtijbugDroolsRestException extends Exception {
    private String className;
    private String Attribute;
    private  String value;
    private  Exception originalException;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getAttribute() {
        return Attribute;
    }

    public void setAttribute(String attribute) {
        Attribute = attribute;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Exception getOriginalException() {
        return originalException;
    }

    public void setOriginalException(Exception originalException) {
        this.originalException = originalException;
    }
}
