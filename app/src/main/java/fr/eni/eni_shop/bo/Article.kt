package fr.eni.eni_shop.bo

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import java.util.Date

@Entity
data class Article(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    var title: String = "",
    var description: String = "",
    var price: Float = 0.0f,
    var image: String = "",
    var category: String = "",
    @Json(ignore = true)
    var date: Long = System.currentTimeMillis()
) {}
