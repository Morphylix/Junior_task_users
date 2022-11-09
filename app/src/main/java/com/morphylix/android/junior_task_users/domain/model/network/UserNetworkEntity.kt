package com.morphylix.android.junior_task_users.domain.model.network

import com.google.gson.annotations.SerializedName
import java.util.*

data class UserNetworkEntity(
    val id: Int = 0,
    val name: String = "Name",
    val email: String = "Email",
    val isActive: Boolean = true,
    val age: Int = 20,
    val company: String = "",
    val phone: String = "+79213432534",
    val address: String = "",
    val about: String = "",
    val eyeColor: String = "blue",
    val favoriteFruit: String = "banana",
    @SerializedName("registered") val registerDate: Date = Date(42114413413),
    val latitude: Float = 0.0f,
    val longitude: Float = 0.0f,
    val friends: List<UserFriendsNetworkEntity> = mutableListOf()

)