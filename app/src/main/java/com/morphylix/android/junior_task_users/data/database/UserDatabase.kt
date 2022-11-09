package com.morphylix.android.junior_task_users.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.morphylix.android.junior_task_users.domain.model.cache.UserCacheEntity

@Database(entities = [UserCacheEntity::class], version = 2)
@TypeConverters(UserTypeConverters::class)

abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}