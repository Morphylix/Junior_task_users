package com.morphylix.android.junior_task_users.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.morphylix.android.junior_task_users.data.model.cache.UserCacheEntity
import com.morphylix.android.junior_task_users.data.model.domain.User

@Database(entities = [ UserCacheEntity::class ], version = 2)
@TypeConverters(UserTypeConverters::class)

abstract class UserDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
}

//val migration_1_2 = object : Migration(1, 2) {
//    override fun migrate(database: SupportSQLiteDatabase) {
//        database.execSQL(
//            "ALTER TABLE User RENAME COLUMN favouriteFruit TO favoriteFruit"
//        )
//    }
//
//}