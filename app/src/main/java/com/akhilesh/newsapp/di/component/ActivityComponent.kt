package com.akhilesh.newsapp.di.component

import com.akhilesh.newsapp.data.repository.*
import com.akhilesh.newsapp.di.ActivityScope
import com.akhilesh.newsapp.di.module.ActivityModule
import com.akhilesh.newsapp.ui.country.CountryListActivity
import com.akhilesh.newsapp.ui.language.LanguageActivity
import com.akhilesh.newsapp.ui.newsListScreen.NewsListActivity
import com.akhilesh.newsapp.ui.pagination.PaginationTopHeadlineActivity
import com.akhilesh.newsapp.ui.search.SearchActivity
import com.akhilesh.newsapp.ui.sources.SourcesActivity
import com.akhilesh.newsapp.ui.topheadline.TopHeadlineActivity
import dagger.Component

@ActivityScope
@Component(dependencies = [ApplicationComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {

    fun inject(activity: TopHeadlineActivity)

    fun inject(activity: PaginationTopHeadlineActivity)

    fun inject(activity: SourcesActivity)

    fun inject(activity: NewsListActivity)

    fun inject(activity: CountryListActivity)

    fun inject(activity: LanguageActivity)

    fun inject(activity: SearchActivity)

    fun getTopHeadlineRepository(): TopHeadlineRepository

    fun getPagingTopHeadlineRepository(): PagingTopHeadlineRepository

    fun getNewsSourceRepository(): NewsSourceRepository

    fun getNewsRepository(): NewsRepository

    fun getLanguageRepository(): LanguageRepository

    fun getCountryRepository(): CountryRepository

    fun getSourceRepository(): SearchRepository
}