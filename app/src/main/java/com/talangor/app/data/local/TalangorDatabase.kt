package com.talangor.app.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.talangor.app.data.local.dao.ActionDao
import com.talangor.app.data.local.dao.MoodEntryDao
import com.talangor.app.data.local.dao.MoodLogDao
import com.talangor.app.data.local.entity.ActionEntity
import com.talangor.app.data.local.entity.MoodEntryEntity
import com.talangor.app.data.local.entity.MoodLogEntity

@Database(
    entities = [
        ActionEntity::class,
        MoodLogEntity::class,
        MoodEntryEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class TalangorDatabase : RoomDatabase() {
    abstract fun actionDao(): ActionDao
    abstract fun moodLogDao(): MoodLogDao
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
                )
                    .fallbackToDestructiveMigration(false)
                    .build()
                    .also { instance = it }
            }
        }
    }
}
