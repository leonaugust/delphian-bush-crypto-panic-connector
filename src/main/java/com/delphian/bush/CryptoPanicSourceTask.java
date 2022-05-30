package com.delphian.bush;

import com.delphian.bush.config.CryptoPanicSourceConnectorConfig;
import com.delphian.bush.dto.CryptoNews;
import com.delphian.bush.dto.CryptoNewsResponse;
import com.delphian.bush.dto.NewsSource;
import com.delphian.bush.util.VersionUtil;
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
import static com.delphian.bush.schema.CryptoNewsSchema.*;
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
        TimeUnit.SECONDS.sleep(15);
        String cryptoPanicKey = config.getString(CRYPTO_PANIC_KEY_CONFIG);
        String apiUrl = "https://cryptopanic.com/api/v1/posts/" +
                "?auth_token=" + cryptoPanicKey +
                "&public=true";

        List<SourceRecord> records = new ArrayList<>();

        // Track the last place we left?
        ResponseEntity<CryptoNewsResponse> responseEntity = getRestTemplate().getForEntity(apiUrl, CryptoNewsResponse.class);
        CryptoNewsResponse newsResponse = responseEntity.getBody();

        log.error("Received news records: " + newsResponse.getResults().size());

        if (newsResponse != null) {
            List<CryptoNews> results = newsResponse.getResults();

            if (results != null && !results.isEmpty()) {
                for (CryptoNews news : results) {
                    records.add(generateRecordFromNews(news));
                }
            }
        }

        return records;

        // 5*. think about the case when it should sleep
    }

    private SourceRecord generateRecordFromNews(CryptoNews cryptoNews) {
        return new SourceRecord(
                sourcePartition(),
                sourceOffset(cryptoNews),
                config.getString(TOPIC_CONFIG),
                null,
                NEWS_KEY_SCHEMA,
                buildRecordKey(cryptoNews),
                NEWS_SCHEMA,
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
        map.put(ID_FIELD, cryptoNews.getId());
        return map;
    }

    private Struct buildRecordKey(CryptoNews news){
        // Key Schema
       return new Struct(NEWS_KEY_SCHEMA)
                .put(APPLICATION_CONFIG, config.getString(APPLICATION_CONFIG))
                .put(ID_FIELD, news.getId());
    }


    public Struct buildRecordValue(CryptoNews cryptoNews){
        // Issue top level fields
        Struct valueStruct = new Struct(NEWS_SCHEMA)
                .put("kind", cryptoNews.getKind())
                .put("domain", cryptoNews.getDomain())
                .put("title", cryptoNews.getTitle())
                .put("published_at", cryptoNews.getPublishedAt())
                .put("slug", cryptoNews.getSlug())
                .put("id", cryptoNews.getId())
                .put("url", cryptoNews.getUrl())
                .put("created_at", cryptoNews.getCreatedAt())
                .put("id", cryptoNews.getId());

        NewsSource source = cryptoNews.getSource();
        if (source != null) {
            Struct sourceStruct = new Struct(SOURCE_SCHEMA)
                    .put("title", source.getTitle())
                    .put("region", source.getRegion())
                    .put("domain", source.getDomain())
                    .put("path", source.getPath());
            valueStruct.put(SOURCE_SCHEMA_NAME, sourceStruct);
        }

        return valueStruct;
    }

    @Override
    public void stop() {

    }
}
