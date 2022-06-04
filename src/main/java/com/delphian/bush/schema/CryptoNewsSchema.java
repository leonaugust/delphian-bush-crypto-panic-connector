package com.delphian.bush.schema;

import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.SchemaBuilder;

import static com.delphian.bush.config.CryptoPanicSourceConnectorConfig.APPLICATION_CONFIG;

public class CryptoNewsSchema {

    public static final Integer FIRST_VERSION = 1;
    public static final String CURRENCIES_SCHEMA_NAME = "currencies";
    public static final String SOURCE_SCHEMA_NAME = "source";

    public static final String ID_FIELD = "id";
    public static final Schema CURRENCIES_SCHEMA = SchemaBuilder.struct()
            .name(CURRENCIES_SCHEMA_NAME)
            .field("code", Schema.OPTIONAL_STRING_SCHEMA)
            .field("title", Schema.OPTIONAL_STRING_SCHEMA)
            .field("slug", Schema.OPTIONAL_STRING_SCHEMA)
            .field("url", Schema.OPTIONAL_STRING_SCHEMA)
            .version(FIRST_VERSION)
            .optional()
            .build();

    public static final Schema SOURCE_SCHEMA = SchemaBuilder.struct()
            .name(SOURCE_SCHEMA_NAME)
            .field("title", Schema.OPTIONAL_STRING_SCHEMA)
            .field("region", Schema.OPTIONAL_STRING_SCHEMA)
            .field("domain", Schema.OPTIONAL_STRING_SCHEMA)
            .field("path", Schema.OPTIONAL_STRING_SCHEMA)
            .version(FIRST_VERSION)
            .optional()
            .build();

    public static final Schema NEWS_SCHEMA = SchemaBuilder.struct().name("News")
            .version(FIRST_VERSION)
            .field("kind", Schema.OPTIONAL_STRING_SCHEMA)
            .field("domain", Schema.OPTIONAL_STRING_SCHEMA)
            .field(SOURCE_SCHEMA_NAME,  SOURCE_SCHEMA)
            .field("title", Schema.OPTIONAL_STRING_SCHEMA)
            .field("published_at", Schema.OPTIONAL_STRING_SCHEMA)
            .field("slug", Schema.OPTIONAL_STRING_SCHEMA)
            .field("id", Schema.OPTIONAL_STRING_SCHEMA)
            .field("url", Schema.OPTIONAL_STRING_SCHEMA)
            .field("created_at", Schema.OPTIONAL_STRING_SCHEMA)
            .field(CURRENCIES_SCHEMA_NAME, CURRENCIES_SCHEMA)
            .build();

    public static final Schema NEWS_KEY_SCHEMA = SchemaBuilder.struct()
            .version(FIRST_VERSION)
            .field(APPLICATION_CONFIG, Schema.STRING_SCHEMA)
            .field(ID_FIELD, Schema.STRING_SCHEMA); // also add currency name. Should divide names, because news can have multiple news related to currencies

}
