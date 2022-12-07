package com.delphian.bush.service;

import com.delphian.bush.dto.CryptoNewsResponse;

import java.util.Optional;

public interface CryptoPanicService {

    CryptoNewsResponse getCryptoNewsByProfile(boolean fetchAllPreviousNews, Optional<Long> sourceOffset);

}
