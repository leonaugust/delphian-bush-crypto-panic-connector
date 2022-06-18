package com.delphian.bush;

import com.delphian.bush.config.CryptoPanicSourceConnectorConfig;
import com.delphian.bush.dto.CryptoNews;
import com.delphian.bush.dto.CryptoNewsResponse;
import com.delphian.bush.schema.CryptoNewsSchema;
import com.delphian.bush.service.CryptoPanicService;
import com.delphian.bush.service.CryptoPanicServiceImpl;
import com.delphian.bush.util.VersionUtil;
import com.delphian.bush.util.converter.CryptoNewsConverter;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.connect.data.Struct;
import org.apache.kafka.connect.source.SourceRecord;
import org.apache.kafka.connect.source.SourceTask;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.delphian.bush.config.CryptoPanicSourceConnectorConfig.*;

@Slf4j
public class CryptoPanicSourceTask extends SourceTask {

    private CryptoPanicSourceConnectorConfig config;

    private CryptoPanicService cryptoPanicService = new CryptoPanicServiceImpl();

    @Override
    public String version() {
        return VersionUtil.getVersion();
    }

    @Override
    public void start(Map<String, String> props) {
        config = new CryptoPanicSourceConnectorConfig(props);
    }

    @Override
    public List<SourceRecord> poll() throws InterruptedException {
        TimeUnit.SECONDS.sleep(30); // TODO. extract to config
        List<SourceRecord> records = new ArrayList<>();
        String profile = config.getString(PROFILE_ACTIVE_CONFIG);
        String cryptoPanicKey = config.getString(CRYPTO_PANIC_KEY_CONFIG);
        CryptoNewsResponse newsResponse = cryptoPanicService.getCryptoNews(profile, cryptoPanicKey);

        Optional<Long> sourceOffset = getLatestSourceOffset();
        if (newsResponse != null && newsResponse.getResults() != null) {
            List<CryptoNews> filteredNews = newsResponse.getResults().stream()
                    .filter(n -> {
                        if (sourceOffset.isPresent()) {
                            log.info("Latest offset is not null, additional checking required");
                            if (Long.parseLong(n.getId()) > sourceOffset.get()) {
                                log.info("newsId: [{}] is bigger than latestOffset: [{}], added news to result", Long.parseLong(n.getId()), sourceOffset.get());
                                return true;
                            }
                        } else {
                            log.info("Latest offset was null, added news to result");
                            return true;
                        }
                        return false;
                    })
                    .sorted(Comparator.comparing(CryptoNews::getId))
                    .collect(Collectors.toList());

            log.info("The amount of filtered news which offset is greater than sourceOffset: {}", filteredNews.size());
            if (!CollectionUtils.isEmpty(filteredNews)) {
                for (CryptoNews news : filteredNews) {
                    records.add(generateRecordFromNews(news));
                }
            }
        }

        return records;
    }

    private Optional<Long> getLatestSourceOffset() {
        Map<String, Object> offset = context.offsetStorageReader().offset(sourcePartition());
        if (offset != null) {
            log.info("Offset is not null");
            Object id = offset.get("id");
            log.info("The offset id was null");
            if (id != null) {
                Long latestOffset = Long.valueOf((String) id);
                log.info("Offset information: {}", latestOffset);
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

    // Track which source we have been reading.
    private Map<String, String> sourcePartition() {
        Map<String, String> partitionProperties = new HashMap<>();
        partitionProperties.put(APPLICATION_CONFIG, config.getString(APPLICATION_CONFIG));
        return partitionProperties;
    }

    //  Track the exact place we have been reading
    // do something with pagination and size
    private Map<String, String> sourceOffset(CryptoNews cryptoNews) {
        Map<String, String> map = new HashMap<>();
        map.put(CryptoNewsSchema.ID_FIELD, cryptoNews.getId());
        return map;
    }

    private Struct buildRecordKey(CryptoNews news) {
        // Key Schema
        return new Struct(CryptoNewsSchema.NEWS_KEY_SCHEMA)
                .put(APPLICATION_CONFIG, config.getString(APPLICATION_CONFIG))
                .put(CryptoNewsSchema.ID_FIELD, news.getId());
    }

    public Struct buildRecordValue(CryptoNews cryptoNews) {
        Struct struct = CryptoNewsConverter.INSTANCE.toConnectData(cryptoNews);
//        log.debug("Resulting struct: {}", struct);
        return struct;
    }

    @Override
    public void stop() {

    }
}
