package com.morphylix.android.junior_task_users.presentation.user_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.morphylix.android.junior_task_users.domain.UserRepository
import com.morphylix.android.junior_task_users.domain.model.domain.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "UserDetailsViewModel"

@HiltViewModel
class UserDetailsViewModel
@Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    fun getUser(id: Int): Pair<LiveData<User?>, LiveData<List<User>>> {
        val userLiveData: MutableLiveData<User?> = MutableLiveData()
        val userFriendsListLiveData: MutableLiveData<List<User>> = MutableLiveData()
        val userFriendsList: MutableList<User> = mutableListOf()
        viewModelScope.launch {
            val currentUser = userRepository.getUser(id)
            userLiveData.value = currentUser
            currentUser?.friends?.forEach { friendId ->
                userRepository.getUser(friendId)?.let { userFriendsList.add(it) }
            }
            userFriendsListLiveData.value = userFriendsList
        }
        return Pair(userLiveData, userFriendsListLiveData)
    }
}