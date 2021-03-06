// MIT License
//
// Copyright (c) 2020 Hager Group
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.

package com.hagergroup.sweetokhttpcache.http

import com.apollographql.apollo.Logger
import com.apollographql.apollo.cache.http.ApolloHttpCache
import com.hagergroup.sweetokhttpcache.api.SweetHttpCache
import com.hagergroup.sweetokhttpcache.api.SweetHttpCacheStore
import okhttp3.Interceptor
import java.io.IOException

/**
 * Extends the [ApolloHttpCache] class in order to override the [cacheStore] attribute
 * and the[ApolloHttpCache.interceptor] method with our own [Interceptor].
 *
 * Implements also the [SweetHttpCache.clear] method.
 *
 * @author Ludovic Roland
 * @since 2020.11.27
 */
class SweetApolloHttpCache(cacheStore: SweetHttpCacheStore, logger: Logger)
  : ApolloHttpCache(cacheStore, logger), SweetHttpCache
{

  override fun interceptor(): Interceptor
  {
    return SweetHttpCacheInterceptor(this, logger)
  }

  override fun clear(timeout: Long)
  {
    try
    {
      (cacheStore as? SweetHttpCacheStore)?.delete(timeout)
    }
    catch (e: IOException)
    {
      logger.e(e, "Failed to clear http cache")
    }
  }

}