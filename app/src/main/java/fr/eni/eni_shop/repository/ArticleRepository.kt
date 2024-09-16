package fr.eni.eni_shop.repository

import fr.eni.eni_shop.bo.Article
import fr.eni.eni_shop.dao.ArticleDao
import fr.eni.eni_shop.dao.DaoFactory
import fr.eni.eni_shop.dao.DaoType

class ArticleRepository {
    private val articleDao: ArticleDao = DaoFactory.createArticleDao(DaoType.MEMORY)

    fun getArticleById(id: Long): Article? {
        return this.articleDao.findById(id)
    }

    fun addArticle(article: Article): Long {
        return this.articleDao.insert(article)
    }

    fun getAllArticles(): List<Article> {
        return this.articleDao.findAll()
    }

}