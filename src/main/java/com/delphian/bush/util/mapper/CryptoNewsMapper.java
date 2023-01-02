package com.delphian.bush.util.mapper;

import com.delphian.bush.dto.CryptoNews;
import com.delphian.bush.dto.Currency;
import com.delphian.bush.config.schema.CryptoNewsSchema;
import com.delphian.bush.config.schema.CurrencySchema;
import com.delphian.bush.config.schema.NewsSourceSchema;
import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.Struct;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.delphian.bush.config.schema.CurrencySchema.CONVERTER;

public class CryptoNewsMapper implements ConnectDataMapper<CryptoNews> {
    public static final CryptoNewsMapper INSTANCE = new CryptoNewsMapper();

    /**
     *
     * @inheritDoc
     */
    @Override
    public Schema getSchema() {
        return CryptoNewsSchema.NEWS_SCHEMA;
    }

    /**
     *
     * @inheritDoc
     */
    @Override
    public CryptoNews to(Struct s) {
        List<Struct> currenciesStruct = (List<Struct>) s.get(CurrencySchema.SCHEMA_NAME);
        List<Currency> currencies = currenciesStruct.stream().map(c -> CurrencyMapper.INSTANCE.to(c))
                .collect(Collectors.toList());

        return CryptoNews.builder()
                .source(NewsSourceMapper.INSTANCE.to(s.getStruct(NewsSourceSchema.SCHEMA_NAME)))
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

    /**
     *
     * @inheritDoc
     */
    @Override
    public Struct to(CryptoNews cryptoNews) {
        Struct struct = new Struct(CryptoNewsSchema.NEWS_SCHEMA)
                .put(NewsSourceSchema.SCHEMA_NAME, NewsSourceMapper.INSTANCE.to(cryptoNews.getSource()))
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
                .map(CONVERTER::to)
                .collect(Collectors.toList());
        struct.put(CurrencySchema.SCHEMA_NAME, items);
        return struct;
    }
}