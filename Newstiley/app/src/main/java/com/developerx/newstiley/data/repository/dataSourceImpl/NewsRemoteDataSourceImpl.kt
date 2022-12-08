package com.developerx.newstiley.data.repository.dataSourceImpl

import com.developerx.newstiley.data.api.NewsAPIService
import com.developerx.newstiley.data.model.APIResponse
import com.developerx.newstiley.data.repository.dataSource.NewsRemoteDataSource
import retrofit2.Response

class NewsRemoteDataSourceImpl(
    private val newsAPIService: NewsAPIService
): NewsRemoteDataSource {
    override suspend fun getNewsHeadline(country: String, page: Int): Response<APIResponse> {
        return newsAPIService.getTopHeadlines(country, page)
    }

    override suspend fun getSeachedNews(
        country: String,
        searchQuery: String,
        page: Int
    ): Response<APIResponse> {
        return newsAPIService.getSearchedTopHeadlines(country, searchQuery, page)
    }


}