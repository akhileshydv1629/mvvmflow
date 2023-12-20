package com.akhilesh.newsapp.ui.pagination

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.akhilesh.newsapp.data.model.topheadlines.APIArticle
import com.akhilesh.newsapp.data.repository.PagingTopHeadlineRepository
import com.akhilesh.newsapp.ui.base.BaseViewModel
import com.akhilesh.newsapp.utils.AppConstant
import com.akhilesh.newsapp.utils.NetworkHelper
import kotlinx.coroutines.flow.Flow

class PagingTopHeadlineViewModel(
    topHeadlineRepository: PagingTopHeadlineRepository, networkHelper: NetworkHelper
) : BaseViewModel<List<*>>(networkHelper) {

    val pagingDataFlow: Flow<PagingData<APIArticle>>

    init {
        pagingDataFlow = topHeadlineRepository.getTopHeadlines(country = AppConstant.COUNTRY)
            .cachedIn(viewModelScope)
    }

}