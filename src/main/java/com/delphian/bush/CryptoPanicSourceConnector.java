package com.delphian.bush;

import com.delphian.bush.config.CryptoPanicSourceConnectorConfig;
import com.delphian.bush.util.VersionUtil;
import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.connect.connector.Task;
import org.apache.kafka.connect.source.SourceConnector;

import java.util.*;

// Should load the configs and create a few tasks
public class CryptoPanicSourceConnector extends SourceConnector {
    private CryptoPanicSourceConnectorConfig config;

    @Override
    public void start(Map<String, String> props) {
        // The complete version includes error handling as well.
        config = new CryptoPanicSourceConnectorConfig(props);
    }

    @Override
    public Class<? extends Task> taskClass() {
        return CryptoPanicSourceTask.class;
    }

    @Override
    public List<Map<String, String>> taskConfigs(int maxTasks) {
        ArrayList<Map<String, String>> configs = new ArrayList<>(1);
        configs.add(config.originalsStrings());
        return configs;
    }

    @Override
    public void stop() {
        // Nothing to do since no background monitoring is required
    }

    @Override
    public ConfigDef config() {
        // TODO. Can add validators here
        return CryptoPanicSourceConnectorConfig.conf();
    }

    @Override
    public String version() {
        return VersionUtil.getVersion();
    }
}
