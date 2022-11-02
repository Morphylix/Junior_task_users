package com.morphylix.android.junior_task_users.data.api

import com.morphylix.android.junior_task_users.data.model.domain.User
import com.morphylix.android.junior_task_users.data.model.network.UserNetworkEntity
import retrofit2.Call
import retrofit2.http.GET

interface UserApi {
    @GET("v0/b/candidates--questionnaire.appspot.com/o/users.json?alt=media&token=e3672c23-b1a5-4ca7-bb77-b6580d75810c")
    suspend fun fetchUsers(): List<UserNetworkEntity>?
}