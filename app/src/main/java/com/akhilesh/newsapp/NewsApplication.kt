package com.akhilesh.newsapp

import android.app.Application
import com.akhilesh.newsapp.di.component.ApplicationComponent
import com.akhilesh.newsapp.di.component.DaggerApplicationComponent
import com.akhilesh.newsapp.di.module.ApplicationModule

class NewsApplication : Application() {

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        injectDependencies()
    }

    private fun injectDependencies() {
        applicationComponent =
            DaggerApplicationComponent.builder().applicationModule(ApplicationModule(this)).build()
        applicationComponent.inject(this)
    }

    // Needed to replace the component with a test specific one
    fun setTestComponent(applicationComponent: ApplicationComponent) {
        this.applicationComponent = applicationComponent
    }
}