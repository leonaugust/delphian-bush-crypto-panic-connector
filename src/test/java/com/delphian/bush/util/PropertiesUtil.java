package com.delphian.bush.util;

import com.delphian.bush.config.CryptoPanicSourceConnectorConfig;
import org.apache.kafka.common.config.ConfigDef;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import static com.delphian.bush.config.CryptoPanicSourceConnectorConfig.*;

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

    public static Map<String, String> getProperties() {
        Map<String, String> props = new HashMap<>();
        properties.stringPropertyNames().forEach(p -> {
            props.put(p, properties.getProperty(p));
        });

        return props;
    }

    public static Map<String, String> getPropertiesOverridden(Map<String, String> overridden) {
        Map<String, String> props = getProperties();
        props.putAll(overridden);

        if (!properties.stringPropertyNames().contains(TOPIC_CONFIG)) {
            props.put(TOPIC_CONFIG, null);
        }
        if (!properties.stringPropertyNames().contains(APPLICATION_CONFIG)) {
            props.put(APPLICATION_CONFIG, null);
        }

        if (!properties.stringPropertyNames().contains(CRYPTO_PANIC_KEY_CONFIG)) {
            props.put(CRYPTO_PANIC_KEY_CONFIG, null);
        }
        if (!properties.stringPropertyNames().contains(PROFILE_ACTIVE_CONFIG)) {
            props.put(PROFILE_ACTIVE_CONFIG, null);
        }
        if (!properties.stringPropertyNames().contains(POLL_TIMEOUT_CONFIG)) {
            props.put(POLL_TIMEOUT_CONFIG, null);
        }
        return props;
    }

    public static String getApiKey() {
        return properties.getProperty(CRYPTO_PANIC_KEY_CONFIG);
    }

}
