package com.morphylix.android.junior_task_users.presentation

import android.app.Application
import com.morphylix.android.junior_task_users.data.UserRepositoryImpl
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class UserApplication: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}