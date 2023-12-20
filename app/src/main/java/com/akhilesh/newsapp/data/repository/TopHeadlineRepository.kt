package com.akhilesh.newsapp.data.repository

import com.akhilesh.newsapp.data.api.NetworkService
import com.akhilesh.newsapp.data.local.dao.TopHeadlinesDao
import com.akhilesh.newsapp.data.local.entity.Article
import com.akhilesh.newsapp.data.model.topheadlines.APIArticle
import com.akhilesh.newsapp.di.ActivityScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@ActivityScope
class TopHeadlineRepository @Inject constructor(
    private val networkService: NetworkService, private val topHeadlinesDao: TopHeadlinesDao
) {

    fun getTopHeadlines(country: String): Flow<List<APIArticle>> {
        return flow {
            emit(networkService.getTopHeadlines(country))
        }.map {
            it.articles
        }
    }

    fun saveTopHeadlinesArticles(articles: List<Article>, country: String): Flow<List<Long>> {
        return flow {
            emit(topHeadlinesDao.insertAndDeleteTopHeadlineArticles(country, articles))
        }
    }


    fun getAllTopHeadlinesArticles(country: String): Flow<List<Article>> {
        return topHeadlinesDao.getAllTopHeadlinesArticle(country)
    }
}