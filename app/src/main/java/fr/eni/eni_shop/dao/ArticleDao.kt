package fr.eni.eni_shop.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import fr.eni.eni_shop.bo.Article

@Dao
interface ArticleDao {
    @Query("SELECT * FROM article where article.id = :id")
    fun findById(id: Long): Article?
    @Query("SELECT * FROM article")
    fun findAll(): List<Article>
    @Insert
    fun insert(article: Article): Long
    @Delete
    fun delete(article: Article): Void
}