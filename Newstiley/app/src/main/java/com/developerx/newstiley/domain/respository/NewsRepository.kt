package com.developerx.newstiley.domain.respository

import com.developerx.newstiley.data.model.APIResponse
import com.developerx.newstiley.data.model.Article
import com.developerx.newstiley.data.util.Resource
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    suspend fun getNewsHeadlines(country: String, page: Int) : Resource<APIResponse>
    suspend fun getSearchedNews(country: String, searchQuery : String, page: Int) : Resource<APIResponse>

    suspend fun saveNews(article : Article)
    suspend fun DeleteSavedNews(article: Article)

    fun getSavedNews() : Flow<List<Article>>
}