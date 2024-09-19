package fr.eni.eni_shop.dao.memory

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import fr.eni.eni_shop.bo.Article
import fr.eni.eni_shop.dao.ArticleDao

@Dao
interface ArticleDaoRoom: ArticleDao {
    @Query("SELECT * FROM article where article.id = :id")
    override fun findById(id: Long): Article?
    @Query("SELECT * FROM article")
    override fun findAll(): List<Article>
    @Insert
    override fun insert(article: Article): Long
    @Delete
    override fun delete(article: Article): Void
}