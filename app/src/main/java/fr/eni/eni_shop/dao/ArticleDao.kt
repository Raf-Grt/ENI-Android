package fr.eni.eni_shop.dao

import fr.eni.eni_shop.bo.Article

interface ArticleDao {
    fun findById(id: Long): Article?
    fun findAll(): List<Article>
    fun insert(article: Article): Long
}