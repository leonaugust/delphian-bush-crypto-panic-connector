/*
 * MIT License
 *
 * Copyright (c) 2023 Leon Galushko
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.delphian.bush;

import com.delphian.bush.config.CryptoPanicSourceConnectorConfig;
import com.delphian.bush.config.schema.CryptoNewsSchema;
import com.delphian.bush.dto.CryptoNews;
import com.delphian.bush.service.CryptoPanicService;
import com.delphian.bush.service.CryptoPanicServiceImpl;
import com.delphian.bush.util.VersionUtil;
import com.delphian.bush.util.mapper.CryptoNewsMapper;
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

  public static final int MAX_TIMEOUT_HOURS = 1;
  private LocalDateTime latestPoll = null;

  private boolean recentPageOnly;

  private CryptoPanicSourceConnectorConfig config;

  private CryptoPanicService cryptoPanicService;

  /**
   * @inheritDoc
   */
  @Override
  public String version() {
    return VersionUtil.getVersion();
  }

  private Long timeoutSeconds;

  /**
   * Sets the initial properties of the connector at the start.
   */
  @Override
  public void start(Map<String, String> props) {
    config = new CryptoPanicSourceConnectorConfig(props);
    cryptoPanicService = new CryptoPanicServiceImpl(config);
    timeoutSeconds = config.getLong(POLL_TIMEOUT_CONFIG);
  }

  /**
   * @return News records.
   * @throws InterruptedException on sleep().
   */
  @Override
  public List<SourceRecord> poll() throws InterruptedException {
    if (latestPoll == null || now().isAfter(latestPoll.plusSeconds(timeoutSeconds))) {
      if (latestPoll == null || now().isAfter(latestPoll.plusHours(MAX_TIMEOUT_HOURS))) {
        recentPageOnly = false;
      }
    } else {
      log.info("Poll timeout: [{}] seconds", timeoutSeconds);
      TimeUnit.SECONDS.sleep(timeoutSeconds);
    }
    latestPoll = LocalDateTime.now();

    Optional<Long> sourceOffset = getLatestSourceOffset();
    List<CryptoNews> filteredNews = cryptoPanicService.getFilteredNews(recentPageOnly,
        sourceOffset);
    recentPageOnly = true;

    List<SourceRecord> records = new ArrayList<>();
    if (filteredNews != null && !filteredNews.isEmpty()) {
      for (CryptoNews news : filteredNews) {
        records.add(generateRecordFromNews(news));
      }
    }

    return records;
  }

  /**
   * @return Returns the latest offset from the context
   * {@link org/apache/kafka/connect/source/SourceTask.java}
   */
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

  /**
   * @param cryptoNews news to send.
   * @return SourceRecord from news.
   */
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

  /**
   * @return Partition to send.
   */
  private Map<String, String> sourcePartition() {
    Map<String, String> partitionProperties = new HashMap<>();
    partitionProperties.put(APPLICATION_CONFIG, config.getString(APPLICATION_CONFIG));
    return partitionProperties;
  }

  /**
   * @param cryptoNews news
   * @return the offset from news.
   */
  private Map<String, String> sourceOffset(CryptoNews cryptoNews) {
    Map<String, String> map = new HashMap<>();
    map.put(CryptoNewsSchema.ID_FIELD, cryptoNews.getId());
    return map;
  }

  /**
   * @param news news
   * @return Struct key from news
   */
  private Struct buildRecordKey(CryptoNews news) {
    return new Struct(CryptoNewsSchema.NEWS_KEY_SCHEMA)
        .put(APPLICATION_CONFIG, config.getString(APPLICATION_CONFIG))
        .put(CryptoNewsSchema.ID_FIELD, news.getId());
  }

  /**
   * @param cryptoNews news
   * @return Struct value from news.
   */
  public Struct buildRecordValue(CryptoNews cryptoNews) {
    return CryptoNewsMapper.INSTANCE.to(cryptoNews);
  }

  /**
   * @inheritDoc
   */
  @Override
  public void stop() {

  }
}
