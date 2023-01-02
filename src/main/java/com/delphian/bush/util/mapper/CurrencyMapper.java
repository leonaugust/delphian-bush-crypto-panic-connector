package com.delphian.bush.util.mapper;

import com.delphian.bush.dto.Currency;
import com.delphian.bush.config.schema.CurrencySchema;
import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.Struct;

import static com.delphian.bush.config.schema.CurrencySchema.CURRENCY_SCHEMA;

public class CurrencyMapper implements ConnectDataMapper<Currency> {
    public static final CurrencyMapper INSTANCE = new CurrencyMapper();

    /**
     *
     * @inheritDoc
     */
    @Override
    public Schema getSchema() {
        return CURRENCY_SCHEMA;
    }

    /**
     *
     * @inheritDoc
     */
    @Override
    public Currency to(Struct s) {
        return Currency.builder()
                .code(s.getString(CurrencySchema.CODE_FIELD))
                .title(s.getString(CurrencySchema.TITLE_FIELD))
                .url(s.getString(CurrencySchema.URL_FIELD))
                .slug(s.getString(CurrencySchema.SLUG_FIELD))
                .build();
    }

    /**
     *
     * @inheritDoc
     */
    @Override
    public Struct to(Currency c) {
        Struct s = new Struct(getSchema());
        if (c == null) {
            return null;
        }

        s.put(CurrencySchema.CODE_FIELD, c.getCode());
        s.put(CurrencySchema.TITLE_FIELD, c.getTitle());
        s.put(CurrencySchema.URL_FIELD, c.getUrl());
        s.put(CurrencySchema.SLUG_FIELD, c.getSlug());
        return s;
    }
}