package com.morphylix.android.junior_task_users.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.morphylix.android.junior_task_users.domain.UserRepository
import com.morphylix.android.junior_task_users.data.model.domain.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel
@Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    private val _userListLiveData: MutableLiveData<List<User>> = MutableLiveData()
    val userListLiveData: LiveData<List<User>>
        get() = _userListLiveData

    fun getUserList() {
        viewModelScope.launch {
            _userListLiveData.value = userRepository.getUserList()
        }
    }
}