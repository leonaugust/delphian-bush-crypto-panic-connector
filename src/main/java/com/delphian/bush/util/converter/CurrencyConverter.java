package com.delphian.bush.util.converter;

import com.delphian.bush.dto.Currency;
import com.delphian.bush.config.schema.CurrencySchema;
import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.Struct;

import static com.delphian.bush.config.schema.CurrencySchema.CURRENCY_SCHEMA;

public class CurrencyConverter implements ConnectPOJOConverter<Currency> {
    public static final CurrencyConverter INSTANCE = new CurrencyConverter();

    @Override
    public Schema getSchema() {
        return CURRENCY_SCHEMA;
    }

    @Override
    public Currency fromConnectData(Struct s) {
        // simple conversion, but more complex types could throw errors
        return Currency.builder()
                .code(s.getString(CurrencySchema.CODE_FIELD))
                .title(s.getString(CurrencySchema.TITLE_FIELD))
                .url(s.getString(CurrencySchema.URL_FIELD))
                .slug(s.getString(CurrencySchema.SLUG_FIELD))
                .build();
    }

    @Override
    public Struct toConnectData(Currency c) {
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