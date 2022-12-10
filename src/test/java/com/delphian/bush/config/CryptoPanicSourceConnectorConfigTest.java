package com.delphian.bush.config;

import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.common.config.ConfigValue;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static com.delphian.bush.config.CryptoPanicSourceConnectorConfig.*;
import static com.delphian.bush.service.CryptoPanicServiceImpl.TEST_PROFILE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CryptoPanicSourceConnectorConfigTest {

    private final ConfigDef configDef = CryptoPanicSourceConnectorConfig.conf();
    private final Map<String, String> config = new HashMap<>();

    {{
        config.put("name", "CryptoPanicSourceConnectorDemo");
        config.put("tasks.max", "1");
        config.put("connector.class", "com.delphian.bush.CryptoPanicSourceConnector");
        config.put(APPLICATION_CONFIG, "crypto-hoover");
        config.put(CRYPTO_PANIC_KEY_CONFIG, "YOUR_API_KEY");
        config.put(PROFILE_ACTIVE_CONFIG, TEST_PROFILE);
        config.put(POLL_TIMEOUT_CONFIG, "60");
        config.put(TOPIC_CONFIG, "news");
    }}

    @Test
    void checkDocumentedConfigIsValidTest() {
        assertTrue(configDef.validate(config)
                .stream()
                .allMatch(configValue -> configValue.errorMessages().size() == 0));
    }

    @Test
    void canReadConfigCorrectlyTest() {
        CryptoPanicSourceConnectorConfig config = new CryptoPanicSourceConnectorConfig(this.config);
        assertEquals("news", config.getString(TOPIC_CONFIG));
    }

    @Test
    void validateActiveProfileTest() {
        config.put(PROFILE_ACTIVE_CONFIG, "notExistingProfile");
        ConfigValue configValue = configDef.validateAll(config).get(PROFILE_ACTIVE_CONFIG);
        assertTrue(configValue.errorMessages().size() > 0);
    }

    @Test
    void validatePollTimeoutTest() {
        config.put(POLL_TIMEOUT_CONFIG, "0");
        ConfigValue configValue = configDef.validateAll(config).get(POLL_TIMEOUT_CONFIG);
        assertTrue(configValue.errorMessages().size() > 0);

        config.put(POLL_TIMEOUT_CONFIG, null);
        configValue = configDef.validateAll(config).get(POLL_TIMEOUT_CONFIG);
        assertTrue(configValue.errorMessages().size() > 0);
    }


}
