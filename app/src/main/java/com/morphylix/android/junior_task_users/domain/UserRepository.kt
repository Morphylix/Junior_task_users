package com.morphylix.android.junior_task_users.domain

import androidx.lifecycle.LiveData
import com.morphylix.android.junior_task_users.data.model.domain.User

interface UserRepository {
    suspend fun getUserList(): List<User>

    suspend fun getUser(id: Int): User?

    suspend fun cacheUserList(userList: List<User>)

}