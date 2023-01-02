package com.delphian.bush;

import com.delphian.bush.config.CryptoPanicSourceConnectorConfig;
import com.delphian.bush.util.VersionUtil;
import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.connect.connector.Task;
import org.apache.kafka.connect.source.SourceConnector;

import java.util.*;

public class CryptoPanicSourceConnector extends SourceConnector {
    private CryptoPanicSourceConnectorConfig config;

    /**
     *
     * @inheritDoc
     */
    @Override
    public void start(Map<String, String> props) {
        config = new CryptoPanicSourceConnectorConfig(props);
    }

    /**
     *
     * @inheritDoc
     */
    @Override
    public Class<? extends Task> taskClass() {
        return CryptoPanicSourceTask.class;
    }

    /**
     *
     * @inheritDoc
     */
    @Override
    public List<Map<String, String>> taskConfigs(int maxTasks) {
        ArrayList<Map<String, String>> configs = new ArrayList<>(1);
        configs.add(config.originalsStrings());
        return configs;
    }

    /**
     *
     * @inheritDoc
     */
    @Override
    public void stop() {
        // Nothing to do since no background monitoring is required
    }

    /**
     *
     * @inheritDoc
     */
    @Override
    public ConfigDef config() {
        return CryptoPanicSourceConnectorConfig.conf();
    }

    /**
     *
     * @inheritDoc
     */
    @Override
    public String version() {
        return VersionUtil.getVersion();
    }
}
