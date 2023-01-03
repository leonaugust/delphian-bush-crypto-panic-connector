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

import com.delphian.bush.dto.CryptoNews;
import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.SchemaBuilder;

import static com.delphian.bush.config.CryptoPanicSourceConnectorConfig.APPLICATION_CONFIG;
import static com.delphian.bush.config.schema.CurrencySchema.*;
import static com.delphian.bush.config.schema.NewsSourceSchema.SOURCE_SCHEMA;
import static com.delphian.bush.util.VersionUtil.FIRST_VERSION;

public class CryptoNewsSchema {

  public static final String SCHEMA_NAME = CryptoNews.class.getName();
  public static final String ID_FIELD = "id";
  public static final String KIND_FIELD = "kind";
  public static final String DOMAIN_FIELD = "domain";
  public static final String TITLE_FIELD = "title";
  public static final String PUBLISHED_AT_FIELD = "published_at";
  public static final String SLUG_FIELD = "slug";
  public static final String URL_FIELD = "url";
  public static final String CREATED_AT_FIELD = "created_at";


  public static final Schema NEWS_SCHEMA = SchemaBuilder.struct()
      .name(SCHEMA_NAME)
      .version(FIRST_VERSION)
      .field(NewsSourceSchema.SCHEMA_NAME, SOURCE_SCHEMA)
      .field(CurrencySchema.SCHEMA_NAME, SchemaBuilder.array(CURRENCY_SCHEMA).optional())
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
      .field(ID_FIELD, Schema.STRING_SCHEMA);

}
