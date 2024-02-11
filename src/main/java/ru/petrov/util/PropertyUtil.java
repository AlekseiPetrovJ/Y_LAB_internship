package ru.petrov.util;

import ru.petrov.annotations.LoggableWithDuration;

import java.util.ResourceBundle;
    public class PropertyUtil {
        /**
         *
         * @param baseName -  name of resource containing the necessary keys
         * @param key - name key that will be found in file with properties
         * @return String key value
         */
        public static String getProperty(String baseName, String key){
            return getResourceBundle(baseName).getString(key);
        }

        /**
         *
         * @param resource -  resource containing the necessary keys
         * @param key - name key that will be found in file with properties
         * @return String key value
         */
        public static String getProperty(ResourceBundle resource, String key) {
            return resource.getString(key);
        }

        /**
         *
         * @param baseName - name of resource containing the necessary keys
         * @return ResourceBundle resources
         */
        @LoggableWithDuration
        public static ResourceBundle getResourceBundle(String baseName){
            return ResourceBundle.getBundle(baseName);
        }
}
