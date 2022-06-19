package com.delphian.bush.service;

import com.delphian.bush.dto.CryptoNewsResponse;

import java.util.Optional;

public interface CryptoPanicService {

    CryptoNewsResponse getCryptoNewsByProfile(String profile, String cryptoPanicKey,
                                              boolean fetchAllPreviousNews, Optional<Long> sourceOffset);

}
