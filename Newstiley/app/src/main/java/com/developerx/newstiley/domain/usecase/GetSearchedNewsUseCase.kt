package com.developerx.newstiley.domain.usecase

import com.developerx.newstiley.data.model.APIResponse
import com.developerx.newstiley.data.util.Resource
import com.developerx.newstiley.domain.respository.NewsRepository

class GetSearchedNewsUseCase(private val newsRepository: NewsRepository) {

    suspend fun execute(country: String, searchQuery: String, page: Int) : Resource<APIResponse> = newsRepository.getSearchedNews(country,searchQuery, page)
}