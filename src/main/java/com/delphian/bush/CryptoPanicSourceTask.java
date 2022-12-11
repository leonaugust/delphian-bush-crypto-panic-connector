package com.delphian.bush;

import com.delphian.bush.config.CryptoPanicSourceConnectorConfig;
import com.delphian.bush.config.schema.CryptoNewsSchema;
import com.delphian.bush.dto.CryptoNews;
import com.delphian.bush.service.CryptoPanicService;
import com.delphian.bush.service.CryptoPanicServiceImpl;
import com.delphian.bush.util.VersionUtil;
import com.delphian.bush.util.converter.CryptoNewsConverter;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.connect.data.Struct;
import org.apache.kafka.connect.source.SourceRecord;
import org.apache.kafka.connect.source.SourceTask;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.delphian.bush.config.CryptoPanicSourceConnectorConfig.*;
import static java.time.LocalDateTime.now;

@Slf4j
public class CryptoPanicSourceTask extends SourceTask {

    private LocalDateTime latestPoll = null;

    private boolean recentPageOnly;

    private CryptoPanicSourceConnectorConfig config;

    private CryptoPanicService cryptoPanicService;

    @Override
    public String version() {
        return VersionUtil.getVersion();
    }

    private Long timeoutSeconds;

    @Override
    public void start(Map<String, String> props) {
        config = new CryptoPanicSourceConnectorConfig(props);
        cryptoPanicService = new CryptoPanicServiceImpl(config);
        timeoutSeconds = config.getLong(POLL_TIMEOUT_CONFIG);
    }

    @Override
    public List<SourceRecord> poll() throws InterruptedException {
        if (latestPoll == null || now().isAfter(latestPoll.plusSeconds(timeoutSeconds))) {
            if (latestPoll == null || now().isAfter(latestPoll.plusHours(1))) {
                recentPageOnly = false;
            }
        } else {
            log.info("Poll timeout: [{}] seconds", timeoutSeconds);
            TimeUnit.SECONDS.sleep(timeoutSeconds);
        }
        latestPoll = LocalDateTime.now();

        Optional<Long> sourceOffset = getLatestSourceOffset();
        List<CryptoNews> filteredNews = cryptoPanicService.getFilteredNews(recentPageOnly, sourceOffset);
        recentPageOnly = true;

        List<SourceRecord> records = new ArrayList<>();
        if (filteredNews != null && !filteredNews.isEmpty()) {
            for (CryptoNews news : filteredNews) {
                records.add(generateRecordFromNews(news));
            }
        }

        return records;
    }

    private Optional<Long> getLatestSourceOffset() {
        Map<String, Object> offset = context.offsetStorageReader().offset(sourcePartition());
        if (offset != null) {
            log.info("Offset is not null");
            Object id = offset.get("id");
            if (id != null) {
                Long latestOffset = Long.valueOf((String) id);
                log.info("latestOffset: {}", latestOffset);
                return Optional.of(latestOffset);
            }
        }
        return Optional.empty();
    }

    private SourceRecord generateRecordFromNews(CryptoNews cryptoNews) {
        return new SourceRecord(
                sourcePartition(),
                sourceOffset(cryptoNews),
                config.getString(TOPIC_CONFIG),
                null,
                CryptoNewsSchema.NEWS_KEY_SCHEMA,
                buildRecordKey(cryptoNews),
                CryptoNewsSchema.NEWS_SCHEMA,
                buildRecordValue(cryptoNews),
                Instant.now().toEpochMilli()
        );
    }

    private Map<String, String> sourcePartition() {
        Map<String, String> partitionProperties = new HashMap<>();
        partitionProperties.put(APPLICATION_CONFIG, config.getString(APPLICATION_CONFIG));
        return partitionProperties;
    }

    private Map<String, String> sourceOffset(CryptoNews cryptoNews) {
        Map<String, String> map = new HashMap<>();
        map.put(CryptoNewsSchema.ID_FIELD, cryptoNews.getId());
        return map;
    }

    private Struct buildRecordKey(CryptoNews news) {
        return new Struct(CryptoNewsSchema.NEWS_KEY_SCHEMA)
                .put(APPLICATION_CONFIG, config.getString(APPLICATION_CONFIG))
                .put(CryptoNewsSchema.ID_FIELD, news.getId());
    }

    public Struct buildRecordValue(CryptoNews cryptoNews) {
        return CryptoNewsConverter.INSTANCE.toConnectData(cryptoNews);
    }

    @Override
    public void stop() {

    }
}
