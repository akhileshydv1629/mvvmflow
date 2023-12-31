package com.akhilesh.newsapp.utils

import androidx.annotation.VisibleForTesting
import com.akhilesh.newsapp.utils.network.NetworkError

interface NetworkHelper {

    fun isNetworkConnected(): Boolean

    fun castToNetworkError(throwable: Throwable): NetworkError

    @VisibleForTesting
    fun setStatus(status: Boolean)

}