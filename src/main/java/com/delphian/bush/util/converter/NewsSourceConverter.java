package com.delphian.bush.util.converter;

import com.delphian.bush.dto.NewsSource;
import com.delphian.bush.schema.NewsSourceSchema;
import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.Struct;

public class NewsSourceConverter implements ConnectPOJOConverter<NewsSource> {
    public static final NewsSourceConverter INSTANCE = new NewsSourceConverter();

    @Override
    public Schema getSchema() {
        return NewsSourceSchema.SOURCE_SCHEMA;
    }

    @Override
    public NewsSource fromConnectData(Struct s) {
        // simple conversion, but more complex types could throw errors
        return NewsSource.builder()
                .title(s.getString("title"))
                .region(s.getString("region"))
                .domain(s.getString("domain"))
                .path(s.getString("path"))
                .build();
    }

    @Override
    public Struct toConnectData(NewsSource c) {
        Struct s = new Struct(getSchema());
        if (c == null) {
            return null;
        }

        s.put("title", c.getTitle());
        s.put("region", c.getRegion());
        s.put("domain", c.getDomain());
        s.put("path", c.getPath());
        return s;
    }
}