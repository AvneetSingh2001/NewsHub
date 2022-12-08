package com.developerx.newstiley.data.repository.dataSource

import com.developerx.newstiley.data.model.APIResponse
import retrofit2.Response

interface NewsRemoteDataSource {
    suspend fun getNewsHeadline(country: String, page: Int): Response<APIResponse>

    suspend fun getSeachedNews(country: String, searchQuery: String, page: Int) : Response<APIResponse>
}