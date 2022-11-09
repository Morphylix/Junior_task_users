package com.morphylix.android.junior_task_users.di

import com.morphylix.android.junior_task_users.data.UserRepositoryImpl
import com.morphylix.android.junior_task_users.data.api.UserApi
import com.morphylix.android.junior_task_users.data.database.UserDao
import com.morphylix.android.junior_task_users.domain.model.cache.UserCacheMapper
import com.morphylix.android.junior_task_users.domain.model.network.UserNetworkMapper
import com.morphylix.android.junior_task_users.domain.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides // TODO: REPLACE WITH @Binds ANNOTATION
    fun provideRepository(
        userApi: UserApi,
        userDao: UserDao,
        userCacheMapper: UserCacheMapper,
        userNetworkMapper: UserNetworkMapper
    ): UserRepository {
        return UserRepositoryImpl(userApi, userDao, userCacheMapper, userNetworkMapper)
    }
}