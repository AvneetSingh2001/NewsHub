package com.developerx.newstiley.presentation.di

import com.developerx.newstiley.data.api.NewsAPIService
import com.developerx.newstiley.data.repository.dataSource.NewsRemoteDataSource
import com.developerx.newstiley.data.repository.dataSourceImpl.NewsRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteDataModule {

    @Singleton
    @Provides
    fun getNewsRemoteDataSource(newsAPIService: NewsAPIService) : NewsRemoteDataSource {
        return return NewsRemoteDataSourceImpl(newsAPIService)
    }
}