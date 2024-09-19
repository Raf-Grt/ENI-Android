package fr.eni.eni_shop.dao.network

import fr.eni.eni_shop.bo.Article
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ArticleApiService {

    @GET("products")
    suspend fun getArticles(): List<Article>

    @GET("products/categories")
    suspend fun getAllCategories(): List<String>

    @GET("products/{id}")
    suspend fun getArticleById(@Path("id") id: Long): Article?

    @POST("products")
    suspend fun createArticle(@Body article: Article): Article

    @PUT("products/{id}")
    suspend fun updateArticle(@Path("id") id: Int, @Body article: Article): Article

    @DELETE("products/{id}")
    suspend fun deleteArticle(@Path("id") id: Int)

}