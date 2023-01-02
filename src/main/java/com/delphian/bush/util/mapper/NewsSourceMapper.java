/*
 * MIT License
 *
 * Copyright (c) 2023 Leon Galushko
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.delphian.bush.util.mapper;

import com.delphian.bush.dto.NewsSource;
import com.delphian.bush.config.schema.NewsSourceSchema;
import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.Struct;

public class NewsSourceMapper implements ConnectDataMapper<NewsSource> {
    public static final NewsSourceMapper INSTANCE = new NewsSourceMapper();

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
    public NewsSource to(Struct s) {
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
    public Struct to(NewsSource c) {
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