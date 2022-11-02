package com.morphylix.android.junior_task_users.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.morphylix.android.junior_task_users.data.database.UserDao
import com.morphylix.android.junior_task_users.data.database.UserDatabase
//import com.morphylix.android.junior_task_users.data.database.migration_1_2
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val DATABASE_NAME = "user-database"

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Singleton
    @Provides
    fun provideUserDatabase(@ApplicationContext context: Context): UserDatabase {
        return Room.databaseBuilder(
            context,
            UserDatabase::class.java,
            DATABASE_NAME
            )
            //.addMigrations(migration_1_2)
            .build()
    }

    @Singleton
    @Provides
    fun provideUserDao(userDatabase: UserDatabase): UserDao {
        return userDatabase.userDao()
    }
}