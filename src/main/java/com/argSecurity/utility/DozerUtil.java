/**
 * Benetech trainning app Copyrights reserved
 */
package com.argSecurity.utility;

import org.dozer.DozerBeanMapper;


/**
 * Singleton for Dozer library
 * 
 * @author daniela.depablos
 * 
 */
public final class DozerUtil {
    /**
     * The instance of xstream
     */
    private static DozerBeanMapper instance = null;

    /**
     * Private singleton constructor
     */
    private DozerUtil() {
    }

    /**
     * Creates the instance in a synchronized way in case of any problem
     */
    private synchronized static void createInstance() {
        if (instance == null) {
            instance = new DozerBeanMapper();
        }
    }

    /**
     * Gets the instance of the dozer mapper
     * 
     * @return The instance if there is one
     */
    public static DozerBeanMapper getInstance() {
        if (instance == null) {
            createInstance();
        }
        return instance;
    }
}
