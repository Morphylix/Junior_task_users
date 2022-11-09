package com.morphylix.android.junior_task_users.domain.model.domain

import com.morphylix.android.junior_task_users.presentation.R

enum class Fruit(val imgId: Int) {
    STRAWBERRY(R.drawable.strawberry), BANANA(R.drawable.banana), APPLE(R.drawable.apple);

    companion object {
        fun convertFromString(str: String) = when (str.lowercase()) {
            "banana" -> BANANA
            "strawberry" -> STRAWBERRY
            "apple" -> APPLE
            else -> throw IllegalArgumentException("Wrong favorite fruit")
        }

        fun convertToString(fruit: Fruit) = when (fruit) {
            BANANA -> "banana"
            STRAWBERRY -> "strawberry"
            APPLE -> "apple"
        }
    }
}