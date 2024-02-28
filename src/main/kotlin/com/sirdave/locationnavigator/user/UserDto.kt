package com.sirdave.locationnavigator.user

data class UserDto(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phoneNumber: String?,
    val dateJoined: String,
    val role: String,
)