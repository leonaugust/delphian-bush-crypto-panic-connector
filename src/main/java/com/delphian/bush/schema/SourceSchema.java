package com.delphian.bush.schema;

import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.SchemaBuilder;

public class SourceSchema {

    public static final Integer FIRST_VERSION = 1;
    public static final String SOURCE_SCHEMA_NAME = "source";

    public static final String TITLE_FIELD = "title";
    public static final String REGION_FIELD = "region";
    public static final String DOMAIN_FIELD = "domain";
    public static final String PATH_FIELD = "path";

    public static final Schema SOURCE_SCHEMA = SchemaBuilder.struct()
            .name(SOURCE_SCHEMA_NAME)
            .field(TITLE_FIELD, Schema.OPTIONAL_STRING_SCHEMA)
            .field(REGION_FIELD, Schema.OPTIONAL_STRING_SCHEMA)
            .field(DOMAIN_FIELD, Schema.OPTIONAL_STRING_SCHEMA)
            .field(PATH_FIELD, Schema.OPTIONAL_STRING_SCHEMA)
            .version(FIRST_VERSION)
            .optional()
            .build();


}
