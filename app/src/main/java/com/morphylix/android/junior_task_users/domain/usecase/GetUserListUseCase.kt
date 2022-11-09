package com.morphylix.android.junior_task_users.domain.usecase

import com.morphylix.android.junior_task_users.domain.UserRepository
import com.morphylix.android.junior_task_users.domain.model.domain.User
import javax.inject.Inject

class GetUserListUseCase
@Inject constructor(private val userRepository: UserRepository) {
    suspend fun execute(): List<User> {
        return userRepository.getUserList()
    }
}