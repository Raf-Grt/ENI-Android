package fr.eni.eni_shop.bo

import java.util.Date

data class Article(
    var id: Long = 0L,
    var name: String = "",
    var description: String = "",
    var price: Float = 0.0f,
    var urlImage: String = "",
    var category: String = "",
    var date: Date = Date()
) {}
