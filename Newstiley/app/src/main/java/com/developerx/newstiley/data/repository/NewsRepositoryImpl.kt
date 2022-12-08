package com.developerx.newstiley.data.repository

import com.developerx.newstiley.data.model.APIResponse
import com.developerx.newstiley.data.model.Article
import com.developerx.newstiley.data.repository.dataSource.NewsLocalDataSource
import com.developerx.newstiley.data.repository.dataSource.NewsRemoteDataSource
import com.developerx.newstiley.data.util.Resource
import com.developerx.newstiley.domain.respository.NewsRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class NewsRepositoryImpl(
    private val newsRemoteDataSource: NewsRemoteDataSource,
    private val newsLocalDataSource: NewsLocalDataSource
): NewsRepository {
    override suspend fun getNewsHeadlines(country: String, page: Int): Resource<APIResponse> {
        return responseToResource(newsRemoteDataSource.getNewsHeadline(country, page))
    }

    fun responseToResource(response: Response<APIResponse>): Resource<APIResponse> {
        if(response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }

        return Resource.Error(response.message())
    }

    override suspend fun getSearchedNews(country: String, searchQuery: String, page: Int): Resource<APIResponse> {
        return responseToResource(newsRemoteDataSource.getSeachedNews(country, searchQuery, page))
    }

    override suspend fun saveNews(article: Article) {
        newsLocalDataSource.saveArticleToDb(article)
    }

    override suspend fun DeleteSavedNews(article: Article) {
        newsLocalDataSource.deleteArticle(article)
    }

    override fun getSavedNews(): Flow<List<Article>> {
        return newsLocalDataSource.getSavedArticles()
    }

}