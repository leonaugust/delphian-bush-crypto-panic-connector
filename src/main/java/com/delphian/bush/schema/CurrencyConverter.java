package com.delphian.bush.schema;

import com.delphian.bush.dto.Currency;
import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.Struct;

import static com.delphian.bush.schema.CurrencySchema.CURRENCY_SCHEMA;

public class CurrencyConverter implements ConnectPOJOConverter<Currency> {

    @Override
    public Schema getSchema() {
        return CURRENCY_SCHEMA;
    }

    @Override
    public Currency fromConnectData(Struct s) {
        // simple conversion, but more complex types could throw errors
        return new Currency(
                s.getString("code"),
                s.getString("title"),
                s.getString("url"),
                s.getString("slug")
        );
    }

    @Override
    public Struct toConnectData(Currency c) {
        Struct s = new Struct(getSchema());
        s.put("code", c.getCode());
        s.put("title", c.getTitle());
        s.put("url", c.getUrl());
        s.put("slug", c.getSlug());
        return s;
    }
}