package org.chtijbug.drools.runtime;

/**
 * Created by IntelliJ IDEA.
 * Date: 24/04/12
 * Time: 18:11
 * To change this template use File | Settings | File Templates.
 */
public class DroolsChtijbugException extends  Exception{
    public static String insertByReflection="insertByReflection";
    public static String MaxNumberRuleExecutionReached = "MaxNumberRuleExecutionReached";
    public static String fireAllRules="fireAllRules";
    public static String  KbaseAcquire="KbaseAcquire";
    public static String KbaseNotInitialised="KbaseNotInitialised" ;
    public static String ErrorToLoadAgent="ErrorToLoadAgent" ;
    public static String ErrorRegisteringMBeans="";
    private String key;
    private String Description;
    private Exception originException;

    public DroolsChtijbugException(String key, String description, Exception originException) {
        this.key = key;
        Description = description;
        this.originException = originException;
    }

    public String getKey() {
        return key;
    }

    public String getDescription() {
        return Description;
    }

    public Exception getOriginException() {
        return originException;
    }
}
