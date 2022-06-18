package com.delphian.bush.service;

import com.delphian.bush.dto.CryptoNewsResponse;

public interface CryptoPanicService {

    CryptoNewsResponse getCryptoNews(String profile, String cryptoPanicKey);

}
