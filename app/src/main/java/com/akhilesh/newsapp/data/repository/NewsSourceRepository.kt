package com.akhilesh.newsapp.data.repository

import com.akhilesh.newsapp.data.api.NetworkService
import com.akhilesh.newsapp.data.local.dao.NewsSourceDao
import com.akhilesh.newsapp.data.local.entity.NewsSource
import com.akhilesh.newsapp.data.model.newssources.APINewsSource
import com.akhilesh.newsapp.di.ActivityScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@ActivityScope
class NewsSourceRepository @Inject constructor(
    private val networkService: NetworkService, private val newsSourceDao: NewsSourceDao
) {

    fun getNewsSources(): Flow<List<APINewsSource>> {
        return flow {
            emit(networkService.getNewsSources())
        }.map {
            it.newsSource
        }
    }

    fun saveNewsSources(newsSources: List<NewsSource>): Flow<List<Long>> {
        println("NewsSourceDao:::$newsSourceDao")
        return flow {
            emit(newsSourceDao.insertAndDeleteNewsSources(newsSources))
        }
    }

    fun getAllNewsSources(): Flow<List<NewsSource>> {
        return newsSourceDao.getAllNewsSources()
    }
}