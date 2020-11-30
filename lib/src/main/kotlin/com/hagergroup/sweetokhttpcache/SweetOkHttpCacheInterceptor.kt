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

package com.hagergroup.sweetokhttpcache

import com.apollographql.apollo.api.cache.http.HttpCachePolicy
import com.hagergroup.sweetokhttpcache.api.SweetHttpCache
import com.hagergroup.sweetokhttpcache.request.decorateRequest
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Intercepts the request in order create the cache key and decorate the https requests
 * with the correct header values.
 *
 * @author Ludovic Roland
 * @since 2020.08.20
 */
class SweetOkHttpCacheInterceptor(private val cachePolicies: Map<String, HttpCachePolicy.Policy>)
  : Interceptor
{

  override fun intercept(chain: Interceptor.Chain): Response
  {
    var request = chain.request()

    request.header(SweetHttpCache.CACHE_POLICY_ID)?.let { cachePolicyId ->
      cachePolicies[cachePolicyId]?.let { cachePolicy ->
        request = request.decorateRequest(cachePolicy)
      }
    }

    return chain.proceed(request)
  }

}