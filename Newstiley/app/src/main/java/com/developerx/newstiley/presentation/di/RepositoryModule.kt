package com.developerx.newstiley.presentation.di

import com.developerx.newstiley.data.repository.NewsRepositoryImpl
import com.developerx.newstiley.data.repository.dataSource.NewsLocalDataSource
import com.developerx.newstiley.data.repository.dataSource.NewsRemoteDataSource
import com.developerx.newstiley.domain.respository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun providesNewsRepository(
        newsRemoteDataSource: NewsRemoteDataSource,
        newsLocalDataSource: NewsLocalDataSource
    ): NewsRepository {
        return NewsRepositoryImpl(newsRemoteDataSource, newsLocalDataSource)
    }
}