package com.delphian.bush.config.validator;

import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.common.config.ConfigException;

import java.util.Arrays;
import java.util.List;

public class ActiveProfileValidator implements ConfigDef.Validator{

    public static final String TEST_PROFILE = "test";
    public static final String PROD_PROFILE = "prod";

    public static final List<String> AVAILABLE_PROFILES = Arrays.asList(TEST_PROFILE, PROD_PROFILE);

    /**
     * Ensures the selected profile is one of {AVAILABLE_PROFILES}
     * @param name The name of the configuration
     * @param value The value of the configuration
     */
    @Override
    public void ensureValid(String name, Object value) {
        String profile = (String) value;
        if (!AVAILABLE_PROFILES.contains(profile)) {
            throw new ConfigException(name, value, "Selected profile is invalid, available profiles "  + AVAILABLE_PROFILES);
        }

    }
}
