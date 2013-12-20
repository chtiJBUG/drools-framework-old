package org.chtijbug.drools.common.log;

import java.util.HashMap;
import java.util.Map;

/**
* Created with IntelliJ IDEA.
* User: samuel
* Date: 14/09/12
* Time: 14:44
*/
public class LoggerFactory {
    private static final Map<String, Logger> LOGGERS = new HashMap<String, Logger>();

    /**
* This method instanciate every {@link Logger} of the application FFR.
*
* @param loggerName
* the logger's name
* @return {@link Logger}
*/
    public static synchronized Logger getLogger(String loggerName) {
        if (!LOGGERS.containsKey(loggerName)) {
            org.slf4j.Logger loggerToWrap = org.slf4j.LoggerFactory.getLogger(loggerName);
            Logger wrapper = new Logger(loggerToWrap);
            LOGGERS.put(loggerName, wrapper);
        }
        return (Logger) LOGGERS.get(loggerName);
    }

    /**
* This method instanciate every {@link Logger} of the application FFR.
*
* @param clazz
* the {@link Class} to log
* @return {@link Logger}
*/
    public static synchronized Logger getLogger(Class<?> clazz) {
        final String className = clazz.getName();
        if (!LOGGERS.containsKey(clazz)) {
            org.slf4j.Logger loggerToWrap = org.slf4j.LoggerFactory.getLogger(clazz);
            Logger wrapper = new Logger(loggerToWrap);
            LOGGERS.put(className, wrapper);
        }
        return (Logger) LOGGERS.get(className);
    }
}