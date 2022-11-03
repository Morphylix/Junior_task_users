package com.morphylix.android.junior_task_users.data.model.cache

import com.morphylix.android.junior_task_users.data.model.domain.EyeColor
import com.morphylix.android.junior_task_users.data.model.domain.Fruit
import com.morphylix.android.junior_task_users.data.model.domain.User
import com.morphylix.android.junior_task_users.util.EntityMapper
import javax.inject.Inject

class UserCacheMapper
@Inject constructor() : EntityMapper<UserCacheEntity, User> {
    override fun mapFromEntity(entity: UserCacheEntity): User {

        val eyeColor = EyeColor.convertFromString(entity.eyeColor)

        val favoriteFruit = Fruit.convertFromString(entity.favoriteFruit)

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
            friends = entity.friends
        )
    }

    override fun mapToEntity(model: User): UserCacheEntity {

        val eyeColor = EyeColor.convertToString(model.eyeColor)

        val favoriteFruit = Fruit.convertToString(model.favoriteFruit)

        return UserCacheEntity(
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
            friends = model.friends
        )
    }

    fun mapFromEntityList(entityList: List<UserCacheEntity>): List<User> {
        return entityList.map {
            mapFromEntity(it)
        }
    }
}