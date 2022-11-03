package com.morphylix.android.junior_task_users.data

import android.util.Log
import com.morphylix.android.junior_task_users.data.api.UserApi
import com.morphylix.android.junior_task_users.data.database.UserDao
import com.morphylix.android.junior_task_users.data.model.cache.UserCacheMapper
import com.morphylix.android.junior_task_users.data.model.domain.User
import com.morphylix.android.junior_task_users.data.model.network.UserNetworkMapper
import com.morphylix.android.junior_task_users.domain.UserRepository
import javax.inject.Inject

private const val TAG = "UserRepository"

class UserRepositoryImpl
@Inject constructor(
    private val userApi: UserApi,
    private val userDao: UserDao,
    private val userCacheMapper: UserCacheMapper,
    private val userNetworkMapper: UserNetworkMapper
) :
    UserRepository {

    override suspend fun getUserList(): List<User> {
        var userList = userCacheMapper.mapFromEntityList(userDao.getUserList())
        Log.d(TAG, "Got ${userList.size} users CACHED FROM DB")
        if (userList.isEmpty()) {
            userList =
                userApi.fetchUsers()?.let { userNetworkMapper.mapFromEntityList(it) } ?: listOf()
            cacheUserList(userList)
            Log.i(TAG, "DOWNLOADED AND CACHED USERS")
        }
        return userList
    }

    override suspend fun getUser(id: Int): User? {
        val user = userDao.getUser(id)?.let { userCacheMapper.mapFromEntity(it) }
        Log.i(TAG, "Got user with id ${user?.id}")
        return user
    }

    override suspend fun cacheUserList(userList: List<User>) {
        userDao.addUserList(userList.map {
            userCacheMapper.mapToEntity(it)
        })
    }

    //////////////////////////////// RETROFIT //////////////////////////////////


    /////////////////////////////// /RETROFIT ///////////////////////////////////
}