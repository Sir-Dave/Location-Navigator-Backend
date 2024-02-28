package com.sirdave.locationnavigator.mapper

import com.sirdave.locationnavigator.helper.toFormattedDate
import com.sirdave.locationnavigator.user.User
import com.sirdave.locationnavigator.user.UserDto

fun User.toUserDto(): UserDto {

    return UserDto(
        id = id!!,
        firstName = firstName,
        lastName = lastName,
        email = email,
        dateJoined = dateJoined.toFormattedDate(),
        role = role,
    )
}