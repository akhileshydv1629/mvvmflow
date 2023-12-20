package com.akhilesh.newsapp.ui.sources

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.akhilesh.newsapp.data.model.newssources.asEntity
import com.akhilesh.newsapp.data.repository.NewsSourceRepository
import com.akhilesh.newsapp.ui.base.BaseViewModel
import com.akhilesh.newsapp.utils.DispatcherProvider
import com.akhilesh.newsapp.utils.NetworkHelper
import com.akhilesh.newsapp.utils.Resource
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SourcesViewModel(
    private val newsSourceRepository: NewsSourceRepository,
    networkHelper: NetworkHelper,
    private val dispatcherProvider: DispatcherProvider
) : BaseViewModel<List<*>>(networkHelper) {


    init {
        if (checkInternetConnection()) fetchNewsSources()
        else fetchNewsSourcesFromDB()
    }

    @OptIn(FlowPreview::class)
    fun fetchNewsSources() {
        viewModelScope.launch(dispatcherProvider.main) {
            newsSourceRepository.getNewsSources().map { apiSourceList ->
                apiSourceList.map { it.asEntity() }.toList()
            }.flatMapConcat {
                return@flatMapConcat newsSourceRepository.saveNewsSources(it)
            }.flowOn(dispatcherProvider.io).catch { e ->
                println("Exception $e")
                _data.value = Resource.error(e.toString())
            }.collect {
                Log.d("collect data::", it.toString())
                fetchNewsSourcesFromDB()
            }
        }
    }

    private fun fetchNewsSourcesFromDB() {
        viewModelScope.launch(dispatcherProvider.main) {
            newsSourceRepository.getAllNewsSources().catch { e ->
                _data.value = Resource.error(e.toString())
            }.flowOn(dispatcherProvider.io).collect {
                if (!checkInternetConnection() && it.isEmpty()) {
                    _data.value = Resource.error("Data Not found.")
                } else {
                    _data.value = Resource.success(it)
                }
            }
        }
    }
}