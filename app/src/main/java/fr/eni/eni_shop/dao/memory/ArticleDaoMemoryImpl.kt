package fr.eni.eni_shop.dao.memory

import android.content.ContentValues.TAG
import android.util.Log
import fr.eni.eni_shop.bo.Article
import fr.eni.eni_shop.dao.ArticleDao
import java.util.Date

class ArticleDaoMemoryImpl: ArticleDao {

    companion object {
        private var INSTANCE: ArticleDao? = null

        fun getInstance(): ArticleDao {
            var instance = INSTANCE

            if (instance == null) {
                instance = ArticleDaoMemoryImpl()
                INSTANCE = instance
            }

            return instance
        }
    }

    private val articlesInMemory: MutableList<Article> = mutableListOf<Article>(

        Article(
            id = 1,
            title = "Fjallraven - Foldsack No. 1 Backpack, Fits 15 Laptops",
            description = "Your perfect pack for everyday use and walks in the forest. Stash your laptop (up to 15 inches) in the padded sleeve, your everyday",
            price = 109.95f,
            image = "https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg",
            category = "men's clothing",
            date = Date().toInstant().toEpochMilli(),
        ),

        Article(
            id = 2,
            title = "Mens Casual Premium Slim Fit T-Shirts",
            description = "Slim-fitting style, contrast raglan long sleeve, three-button henley placket, light weight & soft fabric for breathable and comfortable wearing. And Solid stitched shirts with round neck made for durability and a great fit for casual fashion wear and diehard baseball fans. The Henley style round neckline includes a three-button placket.",
            price = 22.3f,
            image = "https://fakestoreapi.com/img/71-3HjGNDUL._AC_SY879._SX._UX._SY._UY_.jpg",
            category = "men's clothing",
            date = Date().toInstant().toEpochMilli(),
        ),

        Article(
            id = 3,
            title = "Mens Cotton Jacket",
            description = "great outerwear jackets for Spring/Autumn/Winter, suitable for many occasions, such as working, hiking, camping, mountain/rock climbing, cycling, traveling or other outdoors. Good gift choice for you or your family member. A warm hearted love to Father, husband or son in this thanksgiving or Christmas Day.",
            price = 55.99f,
            image = "https://fakestoreapi.com/img/71li-ujtlUL._AC_UX679_.jpg",
            category = "men's clothing",
            date = Date().toInstant().toEpochMilli(),
        )
    )

    override fun insert(article: Article): Long {
        article.id = articlesInMemory.size.toLong() + 1
        articlesInMemory.add(article)
        return article.id
    }

    override fun findById(id: Long): Article? {
        var article: Article? = null

        try {
            article = articlesInMemory.first() {
                it.id == id
            }

        } catch (e: NoSuchElementException) {
            Log.i(TAG, "Article non trouvé !")
        }

        return article
    }

    override fun findAll(): List<Article> {
        return articlesInMemory
    }

    override fun delete(article: Article): Void {
        TODO("Not yet implemented")
    }

}
