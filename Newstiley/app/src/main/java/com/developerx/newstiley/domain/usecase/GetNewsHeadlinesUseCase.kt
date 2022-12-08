package com.developerx.newstiley.domain.usecase

import com.developerx.newstiley.data.model.APIResponse
import com.developerx.newstiley.data.util.Resource
import com.developerx.newstiley.domain.respository.NewsRepository

class GetNewsHeadlinesUseCase(private val newsRepository: NewsRepository){
    suspend fun execute(country: String, page: Int) : Resource<APIResponse> {
        return newsRepository.getNewsHeadlines(country, page)
    }
}