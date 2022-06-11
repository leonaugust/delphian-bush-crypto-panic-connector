package com.delphian.bush.schema;

import com.delphian.bush.dto.Currencies;
import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.SchemaBuilder;
import org.apache.kafka.connect.data.Struct;

public class CurrenciesSchema {


    public static final String CURRENCIES_SCHEMA_NAME = "currencies";

    public static final String CODE_FIELD = "code";
    public static final String TITLE_FIELD = "title";
    public static final String SLUG_FIELD = "slug";
    public static final String URL_FIELD = "url";

    public static final Schema CURRENCIES_SCHEMA = SchemaBuilder.struct()
            .name(Currencies.class.getName())
            .doc("A currency item")
            .field(CODE_FIELD, Schema.OPTIONAL_STRING_SCHEMA)
            .field(TITLE_FIELD, Schema.OPTIONAL_STRING_SCHEMA)
            .field(SLUG_FIELD, Schema.OPTIONAL_STRING_SCHEMA)
            .field(URL_FIELD, Schema.OPTIONAL_STRING_SCHEMA)
            .optional()
            .build();

    public static final ConnectPOJOConverter<Currencies> CONVERTER = new CurrencyConverter();





}
