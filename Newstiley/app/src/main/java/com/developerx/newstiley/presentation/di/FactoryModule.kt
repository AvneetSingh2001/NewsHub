package com.developerx.newstiley.presentation.di

import android.app.Application
import com.developerx.newstiley.domain.usecase.*
import com.developerx.newstiley.presentation.viewmodel.NewsViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FactoryModule {

    @Singleton
    @Provides
    fun provideNewsViewModelFactory(
        application: Application,
        getNewsHeadlinesUseCase: GetNewsHeadlinesUseCase,
        getSearchedNewsUseCase: GetSearchedNewsUseCase,
        saveNewsUseCase: SaveNewsUseCase,
        getSavedNewsUseCase: GetSavedNewsUseCase,
        deleteSavedNewsUseCase: DeleteSavedNewsUseCase
    ) : NewsViewModelFactory {
        return NewsViewModelFactory(application,getNewsHeadlinesUseCase, getSearchedNewsUseCase, saveNewsUseCase, getSavedNewsUseCase, deleteSavedNewsUseCase)
    }
}