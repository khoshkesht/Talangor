package com.talangor.app.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.talangor.app.data.local.dao.MoodEntryDao
import com.talangor.app.data.local.entity.MoodEntryEntity

@Database(
    entities = [MoodEntryEntity::class],
    version = 1,
    exportSchema = true
)
abstract class TalangorDatabase : RoomDatabase() {
    abstract fun moodEntryDao(): MoodEntryDao

    companion object {
        @Volatile
        private var instance: TalangorDatabase? = null

        fun getInstance(context: Context): TalangorDatabase {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    TalangorDatabase::class.java,
                    "talangor.db"
                ).build().also { instance = it }
            }
        }
    }
}
