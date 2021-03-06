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

package com.hagergroup.sweetokhttpcache.client

import com.apollographql.apollo.api.cache.http.HttpCachePolicy
import com.hagergroup.sweetokhttpcache.SweetOkHttpCacheInterceptor
import com.hagergroup.sweetokhttpcache.http.SweetApolloHttpCache
import com.hagergroup.sweetokhttpcache.http.SweetDiskLruHttpCacheStore
import com.hagergroup.sweetokhttpcache.log.SweetLogger
import okhttp3.OkHttpClient
import java.io.File

/**
 * Extension for the [OkHttpClient.Builder] class in order to automatically add the cache system
 *
 * @author Ludovic Roland
 * @since 2020.09.08
 */
fun OkHttpClient.Builder.addSweetOkHttpCache(cachePolicies: Map<String, HttpCachePolicy.Policy>, directory: File, cacheSize: Long = 1024 * 1024): OkHttpClient.Builder
{
  addInterceptor(SweetOkHttpCacheInterceptor(cachePolicies))
  addInterceptor(SweetApolloHttpCache(SweetDiskLruHttpCacheStore(directory, cacheSize), SweetLogger()).interceptor())

  return this
}
