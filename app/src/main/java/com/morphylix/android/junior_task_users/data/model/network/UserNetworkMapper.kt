package com.morphylix.android.junior_task_users.data.model.network

import com.morphylix.android.junior_task_users.data.model.cache.UserCacheEntity
import com.morphylix.android.junior_task_users.data.model.domain.EyeColor
import com.morphylix.android.junior_task_users.data.model.domain.Fruit
import com.morphylix.android.junior_task_users.data.model.domain.User
import com.morphylix.android.junior_task_users.util.EntityMapper
import javax.inject.Inject

class UserNetworkMapper
    @Inject constructor(): EntityMapper<UserNetworkEntity, User> {


    override fun mapFromEntity(entity: UserNetworkEntity): User {

        val eyeColor = when(entity.eyeColor.lowercase()) {
            "blue" -> EyeColor.BLUE
            "brown" -> EyeColor.BROWN
            "green" -> EyeColor.GREEN
            else -> throw IllegalArgumentException("Wrong eye color")
        }

        val favoriteFruit = when(entity.favoriteFruit.lowercase()) {
            "banana" -> Fruit.BANANA
            "strawberry" -> Fruit.STRAWBERRY
            "apple" -> Fruit.APPLE
            else -> throw IllegalArgumentException("Wrong favorite fruit")
        }

        val friends: List<Int> = entity.friends.map {
            it.id
        }

        return User(
            id = entity.id,
            name = entity.name,
            email = entity.email,
            isActive = entity.isActive,
            age = entity.age,
            company = entity.company,
            phone = entity.phone,
            address = entity.address,
            about = entity.about,
            eyeColor = eyeColor,
            favoriteFruit = favoriteFruit,
            registerDate = entity.registerDate,
            latitude = entity.latitude,
            longitude = entity.longitude,
            friends = friends
        )
    }

    override fun mapToEntity(model: User): UserNetworkEntity {

        val eyeColor = when (model.eyeColor) {
            EyeColor.GREEN -> "green"
            EyeColor.BLUE -> "blue"
            EyeColor.BROWN -> "brown"
        }

        val favoriteFruit = when (model.favoriteFruit) {
            Fruit.APPLE -> "apple"
            Fruit.BANANA -> "banana"
            Fruit.STRAWBERRY -> "strawberry"
        }

        val friends: List<UserFriendsNetworkEntity> = model.friends.map {
            UserFriendsNetworkEntity(it)
        }

        return UserNetworkEntity(
            id = model.id,
            name = model.name,
            email = model.email,
            isActive = model.isActive,
            age = model.age,
            company = model.company,
            phone = model.phone,
            address = model.address,
            about = model.about,
            eyeColor = eyeColor,
            favoriteFruit = favoriteFruit,
            registerDate = model.registerDate,
            latitude = model.latitude,
            longitude = model.longitude,
            friends = friends
        )
    }

    fun mapFromEntityList(entities: List<UserNetworkEntity>): List<User> {
        return entities.map { entity ->
            mapFromEntity(entity)
        }
    }
}