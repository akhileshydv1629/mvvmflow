package com.akhilesh.newsapp.utils.network

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import com.akhilesh.newsapp.di.ApplicationContext
import com.akhilesh.newsapp.utils.NetworkHelper
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException


class NetworkHelperImpl(@ApplicationContext val context: Context) : NetworkHelper {

    companion object {
        private const val TAG = "NetworkHelper"
    }

    override fun isNetworkConnected(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork?.isConnected ?: false
    }

    override fun castToNetworkError(throwable: Throwable): NetworkError {

        val defaultNetworkError = NetworkError()
        try {
            if (throwable is ConnectException) return NetworkError(0, "0")
            if (throwable !is HttpException) return defaultNetworkError
            val error = GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
                .fromJson(throwable.response()?.errorBody()?.string(), NetworkError::class.java)
            return NetworkError(throwable.code(), error.statusCode, error.message)
        } catch (e: IOException) {
            Log.e(TAG, e.toString())
        } catch (e: JsonSyntaxException) {
            Log.e(TAG, e.toString())
        } catch (e: NullPointerException) {
            Log.e(TAG, e.toString())
        }
        return defaultNetworkError
    }

    override fun setStatus(status: Boolean) {

    }
}