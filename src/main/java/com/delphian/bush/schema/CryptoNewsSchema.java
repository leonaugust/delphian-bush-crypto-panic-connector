package com.delphian.bush.schema;

import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.SchemaBuilder;

import static com.delphian.bush.config.CryptoPanicSourceConnectorConfig.APPLICATION_CONFIG;
import static com.delphian.bush.schema.CurrenciesSchema.CURRENCIES_SCHEMA;
import static com.delphian.bush.schema.CurrenciesSchema.CURRENCIES_SCHEMA_NAME;
import static com.delphian.bush.schema.SourceSchema.SOURCE_SCHEMA;
import static com.delphian.bush.schema.SourceSchema.SOURCE_SCHEMA_NAME;

public class CryptoNewsSchema {

    public static final Integer FIRST_VERSION = 1;

    public static final String ID_FIELD = "id";
    public static final String KIND_FIELD = "kind";
    public static final String DOMAIN_FIELD = "domain";
    public static final String TITLE_FIELD = "title";
    public static final String PUBLISHED_AT_FIELD = "published_at";
    public static final String SLUG_FIELD = "slug";
    public static final String URL_FIELD = "url";
    public static final String CREATED_AT_FIELD = "created_at";


    public static final Schema NEWS_SCHEMA = SchemaBuilder.struct().name("News")
            .version(FIRST_VERSION)
            .field(SOURCE_SCHEMA_NAME,  SOURCE_SCHEMA)
            .field(CURRENCIES_SCHEMA_NAME, CURRENCIES_SCHEMA)
            .field(KIND_FIELD, Schema.OPTIONAL_STRING_SCHEMA)
            .field(DOMAIN_FIELD, Schema.OPTIONAL_STRING_SCHEMA)
            .field(TITLE_FIELD, Schema.OPTIONAL_STRING_SCHEMA)
            .field(PUBLISHED_AT_FIELD, Schema.OPTIONAL_STRING_SCHEMA)
            .field(SLUG_FIELD, Schema.OPTIONAL_STRING_SCHEMA)
            .field(ID_FIELD, Schema.OPTIONAL_STRING_SCHEMA)
            .field(URL_FIELD, Schema.OPTIONAL_STRING_SCHEMA)
            .field(CREATED_AT_FIELD, Schema.OPTIONAL_STRING_SCHEMA)
            .build();

    public static final Schema NEWS_KEY_SCHEMA = SchemaBuilder.struct()
            .version(FIRST_VERSION)
            .field(APPLICATION_CONFIG, Schema.STRING_SCHEMA)
            .field(ID_FIELD, Schema.STRING_SCHEMA); // also add currency name. Should divide names, because news can have multiple news related to currencies

}
