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

import com.delphian.bush.dto.Currency;
import com.delphian.bush.config.schema.CurrencySchema;
import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.Struct;

import static com.delphian.bush.config.schema.CurrencySchema.CURRENCY_SCHEMA;

public class CurrencyMapper implements ConnectDataMapper<Currency> {
    public static final CurrencyMapper INSTANCE = new CurrencyMapper();

    /**
     *
     * @inheritDoc
     */
    @Override
    public Schema getSchema() {
        return CURRENCY_SCHEMA;
    }

    /**
     *
     * @inheritDoc
     */
    @Override
    public Currency to(Struct s) {
        return Currency.builder()
                .code(s.getString(CurrencySchema.CODE_FIELD))
                .title(s.getString(CurrencySchema.TITLE_FIELD))
                .url(s.getString(CurrencySchema.URL_FIELD))
                .slug(s.getString(CurrencySchema.SLUG_FIELD))
                .build();
    }

    /**
     *
     * @inheritDoc
     */
    @Override
    public Struct to(Currency c) {
        Struct s = new Struct(getSchema());
        if (c == null) {
            return null;
        }

        s.put(CurrencySchema.CODE_FIELD, c.getCode());
        s.put(CurrencySchema.TITLE_FIELD, c.getTitle());
        s.put(CurrencySchema.URL_FIELD, c.getUrl());
        s.put(CurrencySchema.SLUG_FIELD, c.getSlug());
        return s;
    }
}