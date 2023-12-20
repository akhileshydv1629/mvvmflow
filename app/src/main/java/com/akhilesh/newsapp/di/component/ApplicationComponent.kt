package com.akhilesh.newsapp.di.component

import android.content.Context
import com.akhilesh.newsapp.NewsApplication
import com.akhilesh.newsapp.data.api.NetworkService
import com.akhilesh.newsapp.data.local.NewsAppDatabase
import com.akhilesh.newsapp.di.ApplicationContext
import com.akhilesh.newsapp.di.module.ApplicationModule
import com.akhilesh.newsapp.utils.DispatcherProvider
import com.akhilesh.newsapp.utils.NetworkHelper
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(application: NewsApplication)

    @ApplicationContext
    fun getApplicationContext(): Context

    fun getNetworkService(): NetworkService

    fun getNewsAppDatabase(): NewsAppDatabase

    fun getNetworkHelper(): NetworkHelper

    fun getDispatcherProvider(): DispatcherProvider
}