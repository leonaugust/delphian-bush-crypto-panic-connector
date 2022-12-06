package com.delphian.bush.util;

import com.delphian.bush.config.CryptoPanicSourceConnectorConfig;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Loads properties from project directory file
 * /config/custom-connector.properties
 */
public class PropertiesUtil {

    static Properties properties = new Properties();

    static {{
        String configFile = System.getProperty("user.dir") + "/config/custom-connector.properties";
        try (InputStream is = new FileInputStream(configFile)) {
            properties.load(is);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }}

    public static String getProperty(String property) {
        return properties.getProperty(property);
    }

    public static String getApiKey() {
        return properties.getProperty(CryptoPanicSourceConnectorConfig.CRYPTO_PANIC_KEY_CONFIG);
    }

}
