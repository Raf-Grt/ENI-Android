package fr.eni.eni_shop.dao.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitClient {
    private val  BASE_URL = "https://fakestoreapi.com/"

    val moshiConverter = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshiConverter))
        .build()

    val articleApiService by lazy {
        retrofit.create(ArticleApiService::class.java)
    }
}