package fr.eni.eni_shop.dao

import fr.eni.eni_shop.dao.memory.ArticleDaoMemoryImpl
import fr.eni.eni_shop.dao.memory.ArticleDaoRoom

abstract class DaoFactory {
    companion object {
        fun createArticleDao(type: DaoType): ArticleDao {
            var dao: ArticleDao

            when(type) {
                DaoType.MEMORY -> dao = ArticleDaoMemoryImpl()
                DaoType.NETWORK -> TODO("")
                DaoType.ROOM -> TODO("")
            }

            return dao
        }
    }
}