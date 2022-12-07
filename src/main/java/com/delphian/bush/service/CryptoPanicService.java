package com.delphian.bush.service;

import com.delphian.bush.dto.CryptoNews;
import com.delphian.bush.dto.CryptoNewsResponse;

import java.util.List;
import java.util.Optional;

public interface CryptoPanicService {

    List<CryptoNews> getCryptoNewsByProfile(boolean fetchAllPreviousNews, Optional<Long> sourceOffset);

}
