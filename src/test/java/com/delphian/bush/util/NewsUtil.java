package com.delphian.bush.util;

import com.delphian.bush.dto.CryptoNews;
import com.delphian.bush.dto.Currency;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewsUtil {

    public static Map<String, Integer> getCurrenciesCount(List<CryptoNews> newsList, boolean print) {
        Map<String, Integer> countByCurrency = new HashMap<>();
        newsList.stream().forEach(news -> {
            countByCurrency.merge(mapCurrencyCode(news), 1, Integer::sum);
        });

        if (print) {
            System.out.println("+---------------------------+\nCurrency map count:");
            countByCurrency.forEach((key, value) -> System.out.println(key + ":" + value));
            System.out.println("+---------------------------+");
        }

        return countByCurrency;
    }

    private static String mapCurrencyCode(CryptoNews news) {
        List<Currency> currencies = news.getCurrencies();
        if (currencies == null || currencies.isEmpty()) {
            return null;
        }

        return currencies.stream().filter(c -> c.getCode() != null).map(Currency::getCode)
                .findFirst()
                .orElse(null);
    }


}
