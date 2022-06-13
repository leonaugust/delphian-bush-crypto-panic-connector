package com.delphian.bush.util.converter;

import com.delphian.bush.dto.Currency;
import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.Struct;

import static com.delphian.bush.schema.CurrencySchema.CURRENCY_SCHEMA;

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
                .code(s.getString("code"))
                .title(s.getString("title"))
                .url(s.getString("url"))
                .slug(s.getString("slug"))
                .build();
    }

    @Override
    public Struct toConnectData(Currency c) {
        Struct s = new Struct(getSchema());
        if (c == null) {
            return null;
        }

        s.put("code", c.getCode());
        s.put("title", c.getTitle());
        s.put("url", c.getUrl());
        s.put("slug", c.getSlug());
        return s;
    }
}