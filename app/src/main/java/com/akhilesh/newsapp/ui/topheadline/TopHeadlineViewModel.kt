package com.akhilesh.newsapp.ui.topheadline

import androidx.lifecycle.viewModelScope
import com.akhilesh.newsapp.data.model.topheadlines.asEntity
import com.akhilesh.newsapp.data.repository.TopHeadlineRepository
import com.akhilesh.newsapp.ui.base.BaseViewModel
import com.akhilesh.newsapp.utils.AppConstant
import com.akhilesh.newsapp.utils.DispatcherProvider
import com.akhilesh.newsapp.utils.NetworkHelper
import com.akhilesh.newsapp.utils.Resource
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class TopHeadlineViewModel(
    private val topHeadlineRepository: TopHeadlineRepository,
    networkHelper: NetworkHelper,
    private val dispatcherProvider: DispatcherProvider
) : BaseViewModel<List<*>>(networkHelper) {

    init {
        startFetchingNews()
    }

    fun startFetchingNews() {
        if (checkInternetConnection()) {
            fetchNews()
        } else {
            fetchNewsFromDatabase()
        }
    }

    @OptIn(FlowPreview::class)
    private fun fetchNews() {
        viewModelScope.launch(dispatcherProvider.main) {
            topHeadlineRepository.getTopHeadlines(country = AppConstant.COUNTRY).map {
                it.map { articleApi -> articleApi.asEntity(AppConstant.COUNTRY) }.toList()
            }.flatMapConcat {
                return@flatMapConcat topHeadlineRepository.saveTopHeadlinesArticles(
                    it, AppConstant.COUNTRY
                )
            }.flowOn(dispatcherProvider.io).catch { e ->
                println("Exception $e")
                _data.value = Resource.error(e.toString())
            }.collect {
                fetchNewsFromDatabase()
            }
        }
    }

    private fun fetchNewsFromDatabase() {
        viewModelScope.launch(dispatcherProvider.main) {
            topHeadlineRepository.getAllTopHeadlinesArticles(AppConstant.COUNTRY)
                .flowOn(dispatcherProvider.io).catch { e ->
                    _data.value = Resource.error(e.toString())
                }.collect {
                    if (!checkInternetConnection() && it.isEmpty()) {
                        _data.value = Resource.error("Data Not found.")
                    } else {
                        _data.value = Resource.success(it)
                    }
                }
        }
    }
}