package fr.eni.eni_shop.repository

import fr.eni.eni_shop.bo.Article
import fr.eni.eni_shop.dao.ArticleDao
import fr.eni.eni_shop.dao.DaoType
import fr.eni.eni_shop.dao.memory.ArticleDaoRoom

class ArticleRepository(private val memoryDao: ArticleDao, private val roomDao: ArticleDaoRoom) {

    fun getArticleById(id: Long, daoType: DaoType = DaoType.MEMORY): Article? {
        return when (daoType) {
            DaoType.MEMORY -> memoryDao.findById(id)
            else -> roomDao.findById(id)
        }
    }

    fun getAllArticles(daoType: DaoType = DaoType.MEMORY): List<Article> {
        return when (daoType) {
            DaoType.MEMORY -> memoryDao.findAll()
            else -> roomDao.findAll()
        }
    }

    fun saveArticle(article: Article, daoType: DaoType = DaoType.MEMORY): Long {
        return when (daoType) {
            DaoType.MEMORY -> memoryDao.insert(article)
            else -> roomDao.insert(article)
        }
    }

    fun deleteArticle(article: Article, daoType: DaoType = DaoType.ROOM) {
        when (daoType) {
            DaoType.MEMORY -> memoryDao.delete(article)
            else -> roomDao.delete(article)
        }
    }

}