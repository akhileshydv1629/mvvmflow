package com.akhilesh.newsapp.ui.language

import androidx.lifecycle.viewModelScope
import com.akhilesh.newsapp.data.local.entity.asLanguage
import com.akhilesh.newsapp.data.repository.LanguageRepository
import com.akhilesh.newsapp.ui.base.BaseViewModel
import com.akhilesh.newsapp.utils.DispatcherProvider
import com.akhilesh.newsapp.utils.NetworkHelper
import com.akhilesh.newsapp.utils.Resource
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class LanguageListViewModel(
    private val languageRepository: LanguageRepository,
    networkHelper: NetworkHelper,
    private val dispatcherProvider: DispatcherProvider
) : BaseViewModel<List<*>>(networkHelper) {

    init {
        saveLanguage()
    }

    private fun saveLanguage() {
        viewModelScope.launch(dispatcherProvider.main) {
            languageRepository.saveLanguage().flowOn(dispatcherProvider.io).catch { e ->
                println("Exception :::$e")
                _data.value = Resource.error(e.toString())
            }.collect {
                println("saveLanguage::$it")
                fetchLanguage()
            }
        }
    }

    private fun fetchLanguage() {
        viewModelScope.launch(dispatcherProvider.main) {
            languageRepository.getLanguages().map { languageListEntity ->
                languageListEntity.map {
                    it.asLanguage()
                }
            }.flowOn(dispatcherProvider.io).catch { e ->
                println("Exception :::$e")
                _data.value = Resource.error(e.toString())
            }.collect { langList ->
                _data.value = Resource.success(langList.sortedBy { it.name })
            }
        }
    }
}