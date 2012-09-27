package org.chtijbug.drools.common.log;

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
     * @param args       Arguments used as parameters for the method
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
     * @param result     Method result
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

    public void trace(String message, Object... objects) {
        if (this.log.isTraceEnabled()) {
            StringBuffer buffer = new StringBuffer();
            if (message != null) {
                buffer.append(message);
            }
            // Log result if present
            if (objects != null) {
                for (Object object : objects) {
                    buffer.append(object).append(" ");
                }
            }
            this.log.trace(buffer.toString());
        }
    }

    public void debug(String message, Object... objects) {
        if (this.log.isDebugEnabled()) {
            StringBuffer buffer = new StringBuffer();
            if (message != null) {
                buffer.append(message);
            }
            // Log result if present
            if (objects != null) {
                for (Object object : objects) {
                    buffer.append(object).append(" ");
                }
            }
            this.log.debug(buffer.toString());
        }
    }

    public void info(String message, Object... objects) {
        StringBuffer buffer = new StringBuffer();
        if (message != null) {
            buffer.append(message);
        }
        // Log result if present
        if (objects != null) {
            for (Object object : objects) {
                buffer.append(object).append(" ");
            }
        }
        this.log.info(buffer.toString());
    }

    public void warn(String message, Object... objects) {
        StringBuffer buffer = new StringBuffer();
        if (message != null) {
            buffer.append(message);
        }
        // Log result if present
        if (objects != null) {
            for (Object object : objects) {
                buffer.append(object).append(" ");
            }
        }
        this.log.info(buffer.toString());
    }

    public void error(String message, Object... objects) {
        StringBuffer buffer = new StringBuffer();
        if (message != null) {
            buffer.append(message);
        }
        // Log result if present
        if (objects != null) {
            for (Object object : objects) {
                buffer.append(object).append(" ");
            }
        }
        this.log.error(buffer.toString());
    }

    public boolean isTraceEnabled() {
        return this.log.isTraceEnabled();
    }

    public boolean isDebugEnabled() {
        return this.log.isDebugEnabled();
    }

    public boolean isInfoEnabled() {
        return this.log.isInfoEnabled();
    }

    public boolean isWarnEnabled() {
        return this.log.isWarnEnabled();
    }

    public boolean isErrorEnabled() {
        return this.log.isErrorEnabled();
    }
}
