package com.delphian.bush.service;

import com.delphian.bush.dto.CryptoNewsResponse;
import com.delphian.bush.util.json.NewsJsonServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

import static com.delphian.bush.util.WebUtil.getRestTemplate;

@Slf4j
public class CryptoPanicServiceImpl implements CryptoPanicService {

    public static final String TEST_PROFILE = "test";

    @Override
    public CryptoNewsResponse getCryptoNews(String profile, String cryptoPanicKey) {
        if (profile.equals(TEST_PROFILE)) {
            log.info("Using test mocked news");
            try {
                log.info("Response from mocked-news file");
                return new NewsJsonServiceImpl(new ObjectMapper()).getFromJson();
            } catch (IOException e) {
                log.error("Something happened. {}", e.getMessage());
                throw new RuntimeException();
            }
        } else {
            log.info("Getting news from API");
            String apiUrl = "https://cryptopanic.com/api/v1/posts/" +
                    "?auth_token=" + cryptoPanicKey +
                    "&public=true";
            ResponseEntity<CryptoNewsResponse> responseEntity = getRestTemplate().getForEntity(apiUrl, CryptoNewsResponse.class);
            return responseEntity.getBody();
        }
    }
}
