package com.delphian.bush;

import com.delphian.bush.config.CryptoPanicSourceConnectorConfig;
import com.delphian.bush.dto.CryptoNews;
import com.delphian.bush.dto.CryptoNewsResponse;
import com.delphian.bush.schema.CryptoNewsSchema;
import com.delphian.bush.util.VersionUtil;
import com.delphian.bush.util.converter.CryptoNewsConverter;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.connect.data.Struct;
import org.apache.kafka.connect.source.SourceRecord;
import org.apache.kafka.connect.source.SourceTask;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.delphian.bush.config.CryptoPanicSourceConnectorConfig.*;
import static com.delphian.bush.util.WebUtil.getRestTemplate;

@Slf4j
public class CryptoPanicSourceTask extends SourceTask {

    private CryptoPanicSourceConnectorConfig config;

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
        TimeUnit.SECONDS.sleep(15); // TODO. extract to config
        String cryptoPanicKey = config.getString(CRYPTO_PANIC_KEY_CONFIG);
        String apiUrl = "https://cryptopanic.com/api/v1/posts/" +
                "?auth_token=" + cryptoPanicKey +
                "&public=true";

        List<SourceRecord> records = new ArrayList<>();

        ResponseEntity<CryptoNewsResponse> responseEntity = getRestTemplate().getForEntity(apiUrl, CryptoNewsResponse.class);
        CryptoNewsResponse newsResponse = responseEntity.getBody();

        if (newsResponse != null) {
            List<CryptoNews> results = newsResponse.getResults();

            if (results != null && !results.isEmpty()) {
                for (CryptoNews news : results) {
                    records.add(generateRecordFromNews(news));
                }
            }
        }

        return records;
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

    private Struct buildRecordKey(CryptoNews news){
        // Key Schema
       return new Struct(CryptoNewsSchema.NEWS_KEY_SCHEMA)
                .put(APPLICATION_CONFIG, config.getString(APPLICATION_CONFIG))
                .put(CryptoNewsSchema.ID_FIELD, news.getId());
    }

    public Struct buildRecordValue(CryptoNews cryptoNews){
        Struct struct = CryptoNewsConverter.INSTANCE.toConnectData(cryptoNews);
        log.debug("Resulting struct: {}", struct);
        return struct;
    }

    @Override
    public void stop() {

    }
}
