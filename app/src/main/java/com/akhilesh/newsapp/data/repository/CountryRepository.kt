package com.akhilesh.newsapp.data.repository

import com.akhilesh.newsapp.data.local.dao.CountryDao
import com.akhilesh.newsapp.data.local.entity.Country
import com.akhilesh.newsapp.di.ActivityScope
import com.akhilesh.newsapp.utils.AppConstant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@ActivityScope
class CountryRepository @Inject constructor(private val countryDao: CountryDao) {

    fun getCountries(): Flow<List<Country>> {
        return countryDao.getCountry()
    }

    suspend fun saveCountries(): Flow<List<Long>> {
        return flow {
            countryDao.clearCountry()
            emit(countryDao.insertCountry(AppConstant.COUNTRIES))
        }
    }
}