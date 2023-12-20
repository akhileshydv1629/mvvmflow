package com.akhilesh.newsapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.akhilesh.newsapp.data.api.NetworkService
import com.akhilesh.newsapp.data.local.dao.TopHeadlinesDao
import com.akhilesh.newsapp.data.model.topheadlines.APIArticle
import com.akhilesh.newsapp.di.ActivityScope
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ActivityScope
class PagingTopHeadlineRepository @Inject constructor(
    private val networkService: NetworkService, private val topHeadlinesDao: TopHeadlinesDao
) {

    companion object {
        const val NETWORK_PAGE_SIZE = 10
    }

    fun getTopHeadlines(country: String): Flow<PagingData<APIArticle>> {
        return Pager(config = PagingConfig(
            pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false
        ), pagingSourceFactory = {
            TopHeadlinePagingSource(networkService, country)
        }).flow
    }
}