package com.athae.skillsandclasses.Log;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.StackLocatorUtil;

public class skillsandclassesLog {

    private Logger LOGGER;

    private skillsandclassesLog(Logger LOGGER) {
        this.LOGGER = LOGGER;
    }

    public static skillsandclassesLog get() {
        var name = LogManager.getLogger(StackLocatorUtil.getCallerClass(2));
        skillsandclassesLog b = new skillsandclassesLog(LogManager.getLogger("Exile Log: " + name.getName()));
        return b;
    }

    public void warn(String str, Object... obj) {
        LOGGER.warn(str, obj);
    }

    public void log(String str, Object... obj) {
        LOGGER.info(str, obj);
    }

    public void debug(String str, Object... obj) {
        LOGGER.debug(str, obj);
    }

    public void onlyInConsole(String str) {
        System.out.println(str);
    }

}