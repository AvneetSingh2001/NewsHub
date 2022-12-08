package com.developerx.newstiley.domain.usecase

import com.developerx.newstiley.data.model.Article
import com.developerx.newstiley.domain.respository.NewsRepository
import kotlinx.coroutines.flow.Flow

class GetSavedNewsUseCase(private val newsRepository: NewsRepository) {
    fun execute() : Flow<List<Article>> = newsRepository.getSavedNews()
}