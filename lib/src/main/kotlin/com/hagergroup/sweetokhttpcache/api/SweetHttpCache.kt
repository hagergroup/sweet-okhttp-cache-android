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

package com.hagergroup.sweetokhttpcache.api

import com.apollographql.apollo.api.cache.http.HttpCache

/**
 * Extension of the [HttpCache] interface in order to add some http request / response cache possibilities.
 *
 * @author Ludovic Roland
 * @since 2020.11.27
 */
interface SweetHttpCache
  : HttpCache
{

  companion object
  {

    /**
     * Identifies the cache policy to apply to the request
     */
    const val CACHE_POLICY_ID = "CachePolicyId"

    /**
     * Identifies the clean-up policy to apply to the request
     */
    const val CACHE_CLEANER_POLICY_ID = "CacheCleanerPolicyId"

    /**
     * Clean-up all flag http header
     */
    const val CACHE_CLEANER_POLICY_CLEAN_UP_ALL = "CacheCleanerPolicyCleanUpAll"

    /**
     * Clean-up flag http header
     */
    const val CACHE_CLEANER_POLICY_CLEAN_UP = "CacheCleanerPolicyCleanUp"

    /**
     * Clean-up with timeout flag http header
     */
    const val CACHE_CLEANER_POLICY_TIME_OUT = "CacheCleanerPolicyTimeOut"

  }

  /**
   * Clear timeout cached http responses
   */
  fun clear(timeout: Long)

}