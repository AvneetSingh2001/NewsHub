package com.developerx.newstiley.data.repository.dataSourceImpl

import com.developerx.newstiley.data.db.ArticleDao
import com.developerx.newstiley.data.model.Article
import com.developerx.newstiley.data.repository.dataSource.NewsLocalDataSource
import kotlinx.coroutines.flow.Flow

class NewsLocalDataSourceImpl(
    private val articleDao: ArticleDao
): NewsLocalDataSource {

    override suspend fun saveArticleToDb(article: Article) {
        articleDao.insert(article)
    }

    override fun getSavedArticles(): Flow<List<Article>> {
        return articleDao.getAllArticles()
    }

    override suspend fun deleteArticle(article: Article) {
        articleDao.deleteArticle(article)
    }
}