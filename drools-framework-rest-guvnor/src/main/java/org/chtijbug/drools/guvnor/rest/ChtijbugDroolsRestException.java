package org.chtijbug.drools.guvnor.rest;

public class ChtijbugDroolsRestException extends Exception {
    private String className;
    private String attribute;
    private String value;

    public ChtijbugDroolsRestException(Throwable throwable) {
        super(throwable);
    }

    public ChtijbugDroolsRestException(String s) {
        super(s);
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        attribute = attribute;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
