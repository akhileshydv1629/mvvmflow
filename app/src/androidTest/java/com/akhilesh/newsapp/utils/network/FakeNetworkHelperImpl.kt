package com.akhilesh.newsapp.utils.network

import android.content.Context
import com.akhilesh.newsapp.di.ApplicationContext
import com.akhilesh.newsapp.utils.NetworkHelper

class FakeNetworkHelperImpl(@ApplicationContext val context: Context) : NetworkHelper {

    private var status : Boolean = true

    override fun isNetworkConnected(): Boolean {
        return status
    }

    override fun castToNetworkError(throwable: Throwable): NetworkError {
        return NetworkHelperImpl(context).castToNetworkError(throwable)
    }

    override fun setStatus(status: Boolean) {
        this.status = status
    }

}