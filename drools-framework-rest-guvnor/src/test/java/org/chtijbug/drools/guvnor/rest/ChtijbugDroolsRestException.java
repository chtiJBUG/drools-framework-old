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

}
