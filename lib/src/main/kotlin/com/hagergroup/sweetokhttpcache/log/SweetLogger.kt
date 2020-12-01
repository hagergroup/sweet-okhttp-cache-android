package com.hagergroup.sweetokhttpcache.log

import com.apollographql.apollo.Logger
import timber.log.Timber

/**
 * @author Ludovic Roland
 * @since 2020.11.30
 */
class SweetLogger
  : Logger
{

  override fun log(priority: Int, message: String, t: Throwable?, vararg args: Any)
  {
    Timber.log(priority, t, message, *args)
  }

}