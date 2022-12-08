package com.developerx.newstiley.domain.usecase

import com.developerx.newstiley.data.model.Article
import com.developerx.newstiley.domain.respository.NewsRepository

class SaveNewsUseCase(private val newsRepository: NewsRepository) {
    suspend fun execute(article: Article) = newsRepository.saveNews(article)
}