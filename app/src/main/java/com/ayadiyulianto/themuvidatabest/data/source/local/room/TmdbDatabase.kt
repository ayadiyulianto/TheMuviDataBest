package com.ayadiyulianto.themuvidatabest.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ayadiyulianto.themuvidatabest.data.source.local.entity.MovieEntity
import com.ayadiyulianto.themuvidatabest.data.source.local.entity.SeasonEntity
import com.ayadiyulianto.themuvidatabest.data.source.local.entity.TvShowEntity

@Database(
    entities = [MovieEntity::class, TvShowEntity::class, SeasonEntity::class],
    version = 1,
    exportSchema = false
)
abstract class TmdbDatabase : RoomDatabase() {
    abstract fun tmdbDao(): TmdbDao

    companion object {

        @Volatile
        private var INSTANCE: TmdbDatabase? = null

        fun getInstance(context: Context): TmdbDatabase =
            INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    TmdbDatabase::class.java,
                    "Tmdb.db"
                ).build().apply {
                    INSTANCE = this
                }
            }
    }
}