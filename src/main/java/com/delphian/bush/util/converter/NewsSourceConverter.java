package com.delphian.bush.util.converter;

import com.delphian.bush.dto.NewsSource;
import com.delphian.bush.config.schema.NewsSourceSchema;
import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.Struct;

public class NewsSourceConverter implements ConnectPOJOConverter<NewsSource> {
    public static final NewsSourceConverter INSTANCE = new NewsSourceConverter();

    /**
     *
     * @inheritDoc
     */
    @Override
    public Schema getSchema() {
        return NewsSourceSchema.SOURCE_SCHEMA;
    }

    /**
     *
     * @inheritDoc
     */
    @Override
    public NewsSource fromConnectData(Struct s) {
        // simple conversion, but more complex types could throw errors
        return NewsSource.builder()
                .title(s.getString(NewsSourceSchema.TITLE_FIELD))
                .region(s.getString(NewsSourceSchema.REGION_FIELD))
                .domain(s.getString(NewsSourceSchema.DOMAIN_FIELD))
                .path(s.getString(NewsSourceSchema.PATH_FIELD))
                .build();
    }

    /**
     *
     * @inheritDoc
     */
    @Override
    public Struct toConnectData(NewsSource c) {
        Struct s = new Struct(getSchema());
        if (c == null) {
            return null;
        }

        s.put(NewsSourceSchema.TITLE_FIELD, c.getTitle());
        s.put(NewsSourceSchema.REGION_FIELD, c.getRegion());
        s.put(NewsSourceSchema.DOMAIN_FIELD, c.getDomain());
        s.put(NewsSourceSchema.PATH_FIELD, c.getPath());
        return s;
    }
}