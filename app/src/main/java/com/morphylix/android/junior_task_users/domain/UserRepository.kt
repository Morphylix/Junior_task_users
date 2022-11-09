package com.morphylix.android.junior_task_users.domain

import com.morphylix.android.junior_task_users.domain.model.domain.User

interface UserRepository {
    suspend fun getUserList(): List<User>

    suspend fun getUser(id: Int): User?
}