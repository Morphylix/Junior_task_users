package com.morphylix.android.junior_task_users.presentation.user_details

import com.morphylix.android.junior_task_users.domain.model.domain.User
import java.lang.Exception

sealed class UserDetailsState {

    object Loading: UserDetailsState()

    data class Error(val e: Exception): UserDetailsState()

    data class Success(val user: User, val friendsList: List<User>): UserDetailsState()
}