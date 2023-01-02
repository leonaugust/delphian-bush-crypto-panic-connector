package com.delphian.bush.service;

import com.delphian.bush.config.CryptoPanicSourceConnectorConfig;
import com.delphian.bush.dto.CryptoNews;
import com.delphian.bush.dto.CryptoNewsResponse;
import com.delphian.bush.util.json.NewsJsonServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.delphian.bush.config.CryptoPanicSourceConnectorConfig.*;

@Slf4j
public class CryptoPanicServiceImpl implements CryptoPanicService {

    private final CryptoPanicSourceConnectorConfig config;

    public CryptoPanicServiceImpl(CryptoPanicSourceConnectorConfig config) {
        this.config = config;
    }

    public static final String TEST_PROFILE = "test";
    public static final String PROD_PROFILE = "prod";
    public static final int START_PAGE = 1;

    /**
     *
     * @inheritDoc
     */
    @Override
    public List<CryptoNews> getFilteredNews(boolean recentPageOnly, Optional<Long> sourceOffset) {
        Boolean additionalDebugEnabled = config.getBoolean(DEBUG_ADDITIONAL_INFO);
        if (additionalDebugEnabled) {
            log.info("Latest sourceOffset is not null, additional checking required");
        }

        List<CryptoNews> filtered = getNews(recentPageOnly, sourceOffset).stream()
                .filter(n -> {
                    if (sourceOffset.isPresent()) {
                        if (additionalDebugEnabled) {
                            log.info("newsId: [{}] is bigger than latestOffset: [{}], added news to result", Long.parseLong(n.getId()), sourceOffset.get());
                        }
                        return Long.parseLong(n.getId()) > sourceOffset.get();
                    } else {
                        if (additionalDebugEnabled) {
                            log.info("Latest offset was null, added news to result");
                        }
                        return true;
                    }
                })
                .sorted(Comparator.comparing(CryptoNews::getId))
                .collect(Collectors.toList());
        log.info("The amount of filtered news which offset is greater than sourceOffset: {}", filtered.size());
        return filtered;
    }

    /**
     *
     * @inheritDoc
     */
    @Override
    public List<CryptoNews> getNews(boolean recentPageOnly, Optional<Long> sourceOffset) {
        String profile = config.getString(PROFILE_ACTIVE_CONFIG);
        if (profile.equals(TEST_PROFILE)) {
            return getMockedCryptoNews();
        } else {
            if (recentPageOnly) {
                return getNewsFromApi(String.valueOf(START_PAGE), 0).getResults();
            }

            if (!sourceOffset.isPresent()) {
                return getAllPages();
            }

            return getPagesBeforeOffsetIncluding(sourceOffset);
        }
    }

    @SuppressWarnings("all")
    private List<CryptoNews> getPagesBeforeOffsetIncluding(Optional<Long> sourceOffset) {
        List<CryptoNews> cryptoNews = new ArrayList<>();
        long page = 1;

        CryptoNewsResponse firstPageResponse = getNewsFromApi(String.valueOf(page), 3);
        cryptoNews.addAll(firstPageResponse.getResults());
        boolean containsLatestSourceOffset = firstPageResponse.getResults().stream()
                .anyMatch(n -> sourceOffset.get().equals(Long.parseLong(n.getId())));
        boolean hasNext = firstPageResponse.getNext() != null || !firstPageResponse.getNext().equals("null");

        while (!containsLatestSourceOffset && hasNext) {
            page++;
            CryptoNewsResponse newsResponse = getNewsFromApi(String.valueOf(page), 4);
            cryptoNews.addAll(newsResponse.getResults());
            containsLatestSourceOffset = newsResponse.getResults().stream()
                    .anyMatch(n -> sourceOffset.get().equals(Long.parseLong(n.getId())));
            hasNext = newsResponse.getNext() != null && !newsResponse.getNext().equals("null");
        }

        return cryptoNews;
    }

    private List<CryptoNews> getAllPages() {
        return IntStream.rangeClosed(START_PAGE, 10)
                .mapToObj(page -> getNewsFromApi(String.valueOf(page), 4))
                .map(CryptoNewsResponse::getResults)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    private static List<CryptoNews> getMockedCryptoNews() {
        log.info("Using test mocked news");
        try {
            log.info("Response from mocked-news file");
            return new NewsJsonServiceImpl(new ObjectMapper()).getFromJson().getResults();
        } catch (IOException e) {
            log.error("Exception occurred: {}", e.getMessage());
            throw new RuntimeException();
        }
    }


    private CryptoNewsResponse getNewsFromApi(String page, int timeoutSeconds) {
        if (timeoutSeconds > 0) {
            try {
                TimeUnit.SECONDS.sleep(timeoutSeconds);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        log.info("Getting news from API");
        Map<String, Object> params = new HashMap<>();
        params.put("auth_token", config.getString(CRYPTO_PANIC_KEY_CONFIG));
        params.put("public", "true");
        params.put("page", page);
        String apiUrl = "https://cryptopanic.com/api/v1/posts/";
        HttpResponse<JsonNode> response = Unirest.get(apiUrl)
                .header("accept", "application/json")
                .queryString(params)
                .asJson();
        if (response == null || response.getBody() == null) {
            throw new RuntimeException("Api Returned incorrect response");
        }

        try {
            return new ObjectMapper().readValue(response.getBody().toString(), CryptoNewsResponse.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
