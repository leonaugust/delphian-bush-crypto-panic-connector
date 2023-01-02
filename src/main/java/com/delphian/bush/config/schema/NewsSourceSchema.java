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

package com.delphian.bush.config.schema;

import com.delphian.bush.dto.NewsSource;
import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.SchemaBuilder;

import static com.delphian.bush.util.VersionUtil.FIRST_VERSION;

public class NewsSourceSchema {

    public static final String SCHEMA_NAME = NewsSource.class.getName();

    public static final String TITLE_FIELD = "title";
    public static final String REGION_FIELD = "region";
    public static final String DOMAIN_FIELD = "domain";
    public static final String PATH_FIELD = "path";

    public static final Schema SOURCE_SCHEMA = SchemaBuilder.struct()
            .name(SCHEMA_NAME)
            .field(TITLE_FIELD, Schema.OPTIONAL_STRING_SCHEMA)
            .field(REGION_FIELD, Schema.OPTIONAL_STRING_SCHEMA)
            .field(DOMAIN_FIELD, Schema.OPTIONAL_STRING_SCHEMA)
            .field(PATH_FIELD, Schema.OPTIONAL_STRING_SCHEMA)
            .version(FIRST_VERSION)
            .optional()
            .build();

}
