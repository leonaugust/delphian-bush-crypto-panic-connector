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

package com.delphian.bush.service;

import com.delphian.bush.dto.CryptoNews;

import java.util.List;
import java.util.Optional;

public interface CryptoPanicService {

    /**
     * Fetches unread news from API, filters by offset and orders by offset.
     * <p>
     * Filters those news, which offset is bigger than {@code sourceOffset}
     * </p>
     *
     * @param recentPageOnly - Fetch from API the recent page only.
     * @param sourceOffset   - The offset connector stopped reading at.
     * @return The news, ordered by offset, which offset is bigger than {@code sourceOffset}
     */
    List<CryptoNews> getFilteredNews(boolean recentPageOnly, Optional<Long> sourceOffset);

    /**
     * Fetches the news from API and filters the unread news only.
     * <p>
     * If {@value com.delphian.bush.config.validator.ActiveProfileValidator#TEST_PROFILE}
     * is active, fetches the news from test file.
     * </p>
     * <p>
     * If {@code recentPageOnly} is true, fetches only the latest page(For
     * example, the connector polled news from API, the next time it will poll only the
     * latest page.)
     * </p>
     * <p>
     * If {@code sourceOffset} is empty, then it's the first poll. Fetches all pages.
     * Otherwise, the connector was rebooted or it exceeds the timeout,
     * all news up to latest source offset should be fetched.
     * </p>
     *
     * @param recentPageOnly - Fetch from API the recent page only.
     * @param sourceOffset   - The offset connector stopped reading at.
     * @return The news, which offset is bigger than {@code sourceOffset}
     */
    List<CryptoNews> getNews(boolean recentPageOnly, Optional<Long> sourceOffset);

}
