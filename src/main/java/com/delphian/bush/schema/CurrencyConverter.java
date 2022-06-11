package com.delphian.bush.schema;

import com.delphian.bush.dto.Currencies;
import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.Struct;

import static com.delphian.bush.schema.CurrenciesSchema.CURRENCIES_SCHEMA;

public class CurrencyConverter implements ConnectPOJOConverter<Currencies> {

    @Override
    public Schema getSchema() {
        return CURRENCIES_SCHEMA;
    }

    @Override
    public Currencies fromConnectData(Struct s) {
        // simple conversion, but more complex types could throw errors
        return new Currencies(
                s.getString("code"),
                s.getString("title"),
                s.getString("url"),
                s.getString("slug")
        );
    }

    @Override
    public Struct toConnectData(Currencies c) {
        Struct s = new Struct(getSchema());
        s.put("code", c.getCode());
        s.put("title", c.getTitle());
        s.put("url", c.getUrl());
        s.put("slug", c.getSlug());
        return s;
    }
}