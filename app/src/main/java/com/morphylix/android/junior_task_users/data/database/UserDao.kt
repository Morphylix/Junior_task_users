package com.morphylix.android.junior_task_users.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.morphylix.android.junior_task_users.data.model.cache.UserCacheEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM UserCacheEntity")
    suspend fun getUserList(): List<UserCacheEntity>

    @Query("SELECT * FROM UserCacheEntity WHERE id = (:id)")
    suspend fun getUser(id: Int): UserCacheEntity?

    @Insert(onConflict = REPLACE)
    suspend fun addUserList(userList: List<UserCacheEntity>)
}