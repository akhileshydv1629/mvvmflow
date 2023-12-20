package com.akhilesh.newsapp.data.repository

import com.akhilesh.newsapp.data.local.dao.LanguageDao
import com.akhilesh.newsapp.data.local.entity.LanguageEntity
import com.akhilesh.newsapp.di.ActivityScope
import com.akhilesh.newsapp.utils.AppConstant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@ActivityScope
class LanguageRepository @Inject constructor(private val languageDao: LanguageDao) {

    fun getLanguages(): Flow<List<LanguageEntity>> {
        return languageDao.getLanguage()
    }

    fun saveLanguage(): Flow<List<Long>> {
        return flow {
            languageDao.clearLanguage()
            emit(languageDao.insertLanguage(AppConstant.LANGUAGES))
        }
    }

}