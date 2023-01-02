package com.delphian.bush.util.json;

import com.delphian.bush.dto.CryptoNewsResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

public class NewsJsonServiceImpl {

    private final ObjectMapper objectMapper;
    private final InputStream inputStream;
    private final Class<CryptoNewsResponse> targetClass;

    private static final Logger log = LoggerFactory.getLogger(NewsJsonServiceImpl.class);

    public NewsJsonServiceImpl(ObjectMapper objectMapper) throws IOException {
        this.objectMapper = objectMapper;
        this.inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("mocked-news.json");
        this.targetClass = CryptoNewsResponse.class;
    }

    /**
     * @return The news from test file <a href="file:../resources/mocked-news.json</a>
     */
    public CryptoNewsResponse getFromJson() {
        try {
            return objectMapper.readValue(inputStream, targetClass);
        } catch (IOException e) {
            log.debug("Json test service encountered unexpected exception: {}", e.getMessage());
            return null;
        }
    }
}
