package com.delphian.bush.service;

import com.delphian.bush.dto.CryptoNews;

import java.util.List;
import java.util.Optional;

public interface CryptoPanicService {

    List<CryptoNews> getFilteredNews(boolean recentPageOnly, Optional<Long> sourceOffset);

    List<CryptoNews> getNews(boolean recentPageOnly, Optional<Long> sourceOffset);

}
