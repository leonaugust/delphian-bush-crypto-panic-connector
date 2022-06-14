package com.delphian.bush.util.converter;

import com.delphian.bush.dto.CryptoNews;
import com.delphian.bush.dto.Currency;
import com.delphian.bush.schema.CryptoNewsSchema;
import com.delphian.bush.schema.CurrencySchema;
import com.delphian.bush.schema.NewsSourceSchema;
import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.Struct;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.delphian.bush.schema.CurrencySchema.CONVERTER;

public class CryptoNewsConverter implements ConnectPOJOConverter<CryptoNews> {
    public static final CryptoNewsConverter INSTANCE = new CryptoNewsConverter();

    @Override
    public Schema getSchema() {
        return CryptoNewsSchema.NEWS_SCHEMA;
    }

    @Override
    public CryptoNews fromConnectData(Struct s) {
        List<Struct> currenciesStruct = (List<Struct>) s.get("currencies");
        List<Currency> currencies = currenciesStruct.stream().map(c -> CurrencyConverter.INSTANCE.fromConnectData(c))
                .collect(Collectors.toList());

        return CryptoNews.builder()
                .source(NewsSourceConverter.INSTANCE.fromConnectData(s.getStruct(NewsSourceSchema.SOURCE_SCHEMA_NAME)))
                .currencies(currencies)
                .kind(s.getString(CryptoNewsSchema.KIND_FIELD))
                .domain(s.getString(CryptoNewsSchema.DOMAIN_FIELD))
                .title(s.getString(CryptoNewsSchema.TITLE_FIELD))
                .publishedAt(s.getString(CryptoNewsSchema.PUBLISHED_AT_FIELD))
                .slug(s.getString(CryptoNewsSchema.SLUG_FIELD))
                .id(s.getString(CryptoNewsSchema.ID_FIELD))
                .url(s.getString(CryptoNewsSchema.URL_FIELD))
                .createdAt(s.getString(CryptoNewsSchema.CREATED_AT_FIELD))
                .build();
    }

    @Override
    public Struct toConnectData(CryptoNews cryptoNews) {
        Struct struct = new Struct(CryptoNewsSchema.NEWS_SCHEMA)
                .put(NewsSourceSchema.SOURCE_SCHEMA_NAME, NewsSourceConverter.INSTANCE.toConnectData(cryptoNews.getSource()))
                .put(CryptoNewsSchema.KIND_FIELD, cryptoNews.getKind())
                .put(CryptoNewsSchema.DOMAIN_FIELD, cryptoNews.getDomain())
                .put(CryptoNewsSchema.TITLE_FIELD, cryptoNews.getTitle())
                .put(CryptoNewsSchema.PUBLISHED_AT_FIELD, cryptoNews.getPublishedAt())
                .put(CryptoNewsSchema.SLUG_FIELD, cryptoNews.getSlug())
                .put(CryptoNewsSchema.ID_FIELD, cryptoNews.getId())
                .put(CryptoNewsSchema.URL_FIELD, cryptoNews.getUrl())
                .put(CryptoNewsSchema.CREATED_AT_FIELD, cryptoNews.getCreatedAt());

        List<Currency> currencies = Optional.ofNullable(cryptoNews.getCurrencies()).orElse(new ArrayList<>());
        final List<Struct> items = currencies.stream()
                .map(CONVERTER::toConnectData)
                .collect(Collectors.toList());
        struct.put(CurrencySchema.CURRENCIES_SCHEMA_NAME, items);
        return struct;
    }
}