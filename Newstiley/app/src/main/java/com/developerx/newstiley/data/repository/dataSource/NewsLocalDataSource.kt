package com.developerx.newstiley.data.repository.dataSource

import com.developerx.newstiley.data.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsLocalDataSource {

    suspend fun saveArticleToDb(article: Article)

    fun getSavedArticles() : Flow<List<Article>>

    suspend fun deleteArticle(article: Article)
}