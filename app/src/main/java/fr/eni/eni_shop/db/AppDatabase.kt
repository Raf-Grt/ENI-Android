package fr.eni.eni_shop.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import fr.eni.eni_shop.bo.Article
import fr.eni.eni_shop.dao.memory.ArticleDaoRoom

@Database(version = 2, entities = [Article::class])
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun articleDao(): ArticleDaoRoom

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            var instance = INSTANCE

            if (instance == null) {
                instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "EniShop"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
            }

            return instance
        }
    }
}