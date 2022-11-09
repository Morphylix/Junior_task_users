package com.morphylix.android.junior_task_users.presentation.user_list

import com.morphylix.android.junior_task_users.domain.model.domain.User
import java.lang.Exception

sealed class UserListState {
    object Loading: UserListState()

    data class Success(val userList: List<User>): UserListState()

    data class Error(val e: Exception): UserListState()
}