package com.morphylix.android.junior_task_users.domain.model.domain

import com.morphylix.android.junior_task_users.presentation.R

enum class EyeColor(val imgId: Int) {
    BROWN(R.drawable.eye_color_brown), GREEN(R.drawable.eye_color_green), BLUE(R.drawable.eye_color_blue);

    companion object {
        fun convertFromString(str: String) = when (str.lowercase()) {
            "blue" -> BLUE
            "brown" -> BROWN
            "green" -> GREEN
            else -> throw IllegalArgumentException("Wrong eye color")
        }

        fun convertToString(eyeColor: EyeColor) = when (eyeColor) {
            GREEN -> "green"
            BLUE -> "blue"
            BROWN -> "brown"
        }
    }
}

