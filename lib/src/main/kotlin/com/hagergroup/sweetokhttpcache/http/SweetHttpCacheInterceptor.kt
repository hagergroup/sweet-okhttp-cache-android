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

import com.apollographql.apollo.api.internal.ApolloLogger
import com.apollographql.apollo.cache.http.HttpCacheInterceptor
import com.hagergroup.sweetokhttpcache.api.SweetHttpCache
import com.hagergroup.sweetokhttpcache.internal.NoContentResponseBody
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response

/**
 * @author Ludovic Roland
 * @since 2020.11.27
 */
class SweetHttpCacheInterceptor(cache: SweetApolloHttpCache, logger: ApolloLogger)
  : HttpCacheInterceptor(cache, logger)
{

  override fun intercept(chain: Interceptor.Chain): Response
  {
    val request = chain.request()

    if (shouldCleanUpCache(request) == true)
    {
      logger.d("Clean up http cache")
      return cleanUpCacheResponse(request)
    }

    return super.intercept(chain)
  }

  private fun shouldCleanUpCache(request: Request): Boolean =
      request.header(SweetHttpCache.CACHE_CLEANER_POLICY_ID) != null

  private fun cleanUpCacheResponse(request: Request): Response
  {
    if (SweetHttpCache.CACHE_CLEANER_POLICY_CLEAN_UP_ALL == request.header(SweetHttpCache.CACHE_CLEANER_POLICY_ID))
    {
      cleanUpAllCache()
    }
    else if (SweetHttpCache.CACHE_CLEANER_POLICY_CLEAN_UP == request.header(SweetHttpCache.CACHE_CLEANER_POLICY_ID))
    {
      cleanUpCache(request)
    }

    return Response.Builder().apply {
      code(200)
      body(NoContentResponseBody.create())
      message("OK")
      protocol(Protocol.HTTP_1_1)
      request(request)
    }.build()
  }

  private fun cleanUpAllCache()
  {
    cache.clear()
  }

  @Throws(NumberFormatException::class)
  private fun cleanUpCache(request: Request)
  {
    val timeout = request.header(SweetHttpCache.CACHE_CLEANER_POLICY_TIME_OUT)?.toLong() ?: 0
    (cache as? SweetHttpCache)?.clear(timeout)
  }

}