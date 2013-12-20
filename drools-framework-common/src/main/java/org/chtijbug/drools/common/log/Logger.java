package org.chtijbug.drools.common.log;

import org.slf4j.Marker;

/**
* Created with IntelliJ IDEA.
* User: samuel
* Date: 14/09/12
* Time: 14:44
*/
public class Logger {

    /**
* Wrapped logger
*/
    private org.slf4j.Logger log;

    protected Logger(org.slf4j.Logger logger) {
        this.log = logger;
    }

    /**
* Log a message using the DEBUG level allowing tracing the entering in a method.
*
* @param methodName Method name which has been called
* @param args Arguments used as parameters for the method
*/
    public void entry(String methodName, Object... args) {
        if (this.log.isDebugEnabled()) {
            StringBuffer buffer = new StringBuffer("[Entering ");
            buffer.append(methodName);
            // Log parameters if present
            if (args != null) {
                buffer.append(" using parameters '{'");
                for (Object object : args) {
                    buffer.append("{").append(object).append("} ");
                }
                buffer.append("'}'");
            }
            buffer.append("]");
            this.log.debug(buffer.toString());
        }
    }

    /**
* Log a message using the DEBUG level allowing tracing the exit from a method. Only for
* internal use.
*
* @param methodName Method name which has been called
* @param result Method result
*/
    public void exit(String methodName, Object result) {
        if (this.log.isDebugEnabled()) {
            StringBuffer buffer = new StringBuffer("[Exiting ");
            buffer.append(methodName);
            // Log result if present
            if (result != null) {
                buffer.append(" with result '{'");
                buffer.append(result);
                buffer.append("'}'");
            }
            buffer.append("]");
            this.log.debug(buffer.toString());
        }
    }

    public void exit(String methodName) {
        exit(methodName, null);
    }


    /* ---------- Delegates */

    public String getName() {
        return log.getName();
    }

    public void info(Marker marker, String msg, Throwable t) {
        log.info(marker, msg, t);
    }

    public boolean isDebugEnabled(Marker marker) {
        return log.isDebugEnabled(marker);
    }

    public void debug(String format, Object arg1, Object arg2) {
        log.debug(format, arg1, arg2);
    }

    public boolean isTraceEnabled(Marker marker) {
        return log.isTraceEnabled(marker);
    }

    public boolean isInfoEnabled(Marker marker) {
        return log.isInfoEnabled(marker);
    }

    public void trace(String format, Object[] argArray) {
        log.trace(format, argArray);
    }

    public boolean isDebugEnabled() {
        return log.isDebugEnabled();
    }

    public void error(Marker marker, String format, Object[] argArray) {
        log.error(marker, format, argArray);
    }

    public void debug(Marker marker, String format, Object arg) {
        log.debug(marker, format, arg);
    }

    public void error(Marker marker, String msg, Throwable t) {
        log.error(marker, msg, t);
    }

    public void trace(Marker marker, String msg, Throwable t) {
        log.trace(marker, msg, t);
    }

    public boolean isWarnEnabled(Marker marker) {
        return log.isWarnEnabled(marker);
    }

    public void warn(Marker marker, String format, Object[] argArray) {
        log.warn(marker, format, argArray);
    }

    public void error(Marker marker, String msg) {
        log.error(marker, msg);
    }

    public boolean isWarnEnabled() {
        return log.isWarnEnabled();
    }

    public boolean isTraceEnabled() {
        return log.isTraceEnabled();
    }

    public void info(Marker marker, String format, Object[] argArray) {
        log.info(marker, format, argArray);
    }

    public void trace(Marker marker, String format, Object arg) {
        log.trace(marker, format, arg);
    }

    public void trace(Marker marker, String format, Object[] argArray) {
        log.trace(marker, format, argArray);
    }

    public void debug(String msg) {
        log.debug(msg);
    }

    public void error(String msg) {
        log.error(msg);
    }

    public void info(String format, Object arg1, Object arg2) {
        log.info(format, arg1, arg2);
    }

    public void warn(String format, Object arg) {
        log.warn(format, arg);
    }

    public void debug(String format, Object arg) {
        log.debug(format, arg);
    }

    public void info(String format, Object[] argArray) {
        log.info(format, argArray);
    }

    public void info(Marker marker, String format, Object arg) {
        log.info(marker, format, arg);
    }

    public boolean isErrorEnabled() {
        return log.isErrorEnabled();
    }

    public boolean isErrorEnabled(Marker marker) {
        return log.isErrorEnabled(marker);
    }

    public void debug(Marker marker, String format, Object[] argArray) {
        log.debug(marker, format, argArray);
    }

    public void warn(Marker marker, String msg, Throwable t) {
        log.warn(marker, msg, t);
    }

    public void error(String format, Object arg1, Object arg2) {
        log.error(format, arg1, arg2);
    }

    public void info(String msg) {
        log.info(msg);
    }

    public void trace(String msg) {
        log.trace(msg);
    }

    public void info(String format, Object arg) {
        log.info(format, arg);
    }

    public void trace(String msg, Throwable t) {
        log.trace(msg, t);
    }

    public void info(String msg, Throwable t) {
        log.info(msg, t);
    }

    public void warn(String format, Object[] argArray) {
        log.warn(format, argArray);
    }

    public void info(Marker marker, String msg) {
        log.info(marker, msg);
    }

    public void warn(String msg, Throwable t) {
        log.warn(msg, t);
    }

    public void trace(Marker marker, String msg) {
        log.trace(marker, msg);
    }

    public void warn(Marker marker, String format, Object arg) {
        log.warn(marker, format, arg);
    }

    public void debug(String msg, Throwable t) {
        log.debug(msg, t);
    }

    public void debug(Marker marker, String msg) {
        log.debug(marker, msg);
    }

    public void trace(String format, Object arg) {
        log.trace(format, arg);
    }

    public void info(Marker marker, String format, Object arg1, Object arg2) {
        log.info(marker, format, arg1, arg2);
    }

    public void error(String format, Object arg) {
        log.error(format, arg);
    }

    public void debug(String format, Object[] argArray) {
        log.debug(format, argArray);
    }

    public void debug(Marker marker, String msg, Throwable t) {
        log.debug(marker, msg, t);
    }

    public void warn(String msg) {
        log.warn(msg);
    }

    public void error(String format, Object[] argArray) {
        log.error(format, argArray);
    }

    public void trace(String format, Object arg1, Object arg2) {
        log.trace(format, arg1, arg2);
    }

    public void error(Marker marker, String format, Object arg) {
        log.error(marker, format, arg);
    }

    public void debug(Marker marker, String format, Object arg1, Object arg2) {
        log.debug(marker, format, arg1, arg2);
    }

    public void error(String msg, Throwable t) {
        log.error(msg, t);
    }

    public void warn(Marker marker, String format, Object arg1, Object arg2) {
        log.warn(marker, format, arg1, arg2);
    }

    public void error(Marker marker, String format, Object arg1, Object arg2) {
        log.error(marker, format, arg1, arg2);
    }

    public boolean isInfoEnabled() {
        return log.isInfoEnabled();
    }

    public void warn(Marker marker, String msg) {
        log.warn(marker, msg);
    }

    public void trace(Marker marker, String format, Object arg1, Object arg2) {
        log.trace(marker, format, arg1, arg2);
    }

    public void warn(String format, Object arg1, Object arg2) {
        log.warn(format, arg1, arg2);
    }
}