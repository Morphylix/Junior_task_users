package com.morphylix.android.junior_task_users.presentation.user_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.morphylix.android.junior_task_users.domain.model.domain.User
import com.morphylix.android.junior_task_users.domain.usecase.GetUserListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel
@Inject constructor(private val getUserListUseCase: GetUserListUseCase) : ViewModel() {

    private val _userListLiveData: MutableLiveData<List<User>> = MutableLiveData()
    val userListLiveData: LiveData<List<User>>
        get() = _userListLiveData

    fun getUserList() {
        viewModelScope.launch {
            _userListLiveData.value = getUserListUseCase.execute()
        }
    }
}