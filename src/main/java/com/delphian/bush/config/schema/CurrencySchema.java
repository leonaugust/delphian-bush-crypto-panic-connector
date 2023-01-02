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

import com.delphian.bush.dto.Currency;
import com.delphian.bush.util.mapper.ConnectDataMapper;
import com.delphian.bush.util.mapper.CurrencyMapper;
import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.SchemaBuilder;

public class CurrencySchema {


    public static final String SCHEMA_NAME = Currency.class.getName();

    public static final String CODE_FIELD = "code";
    public static final String TITLE_FIELD = "title";
    public static final String SLUG_FIELD = "slug";
    public static final String URL_FIELD = "url";

    public static final Schema CURRENCY_SCHEMA = SchemaBuilder.struct()
            .name(SCHEMA_NAME)
            .doc("A currency item")
            .field(CODE_FIELD, Schema.OPTIONAL_STRING_SCHEMA)
            .field(TITLE_FIELD, Schema.OPTIONAL_STRING_SCHEMA)
            .field(SLUG_FIELD, Schema.OPTIONAL_STRING_SCHEMA)
            .field(URL_FIELD, Schema.OPTIONAL_STRING_SCHEMA)
            .optional()
            .build();

    public static final ConnectDataMapper<Currency> CONVERTER = new CurrencyMapper();





}
