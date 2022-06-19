package com.delphian.bush.util.json;

import com.delphian.bush.dto.CryptoNewsResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

/**
 * Information about news with currencies:
 *
 * BTC - 7
 * RUNE - 1
 * ADA - 2
 * ETH - 2
 */
public class NewsJsonServiceImpl extends JsonToPojoService<CryptoNewsResponse> {

    public NewsJsonServiceImpl(ObjectMapper objectMapper) throws IOException {
        super(new ClassPathResource("mocked-news.json").getInputStream(), objectMapper, CryptoNewsResponse.class);
    }

}
