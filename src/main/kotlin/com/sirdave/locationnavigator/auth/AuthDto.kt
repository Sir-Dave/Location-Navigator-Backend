package com.sirdave.locationnavigator.auth

import com.sirdave.locationnavigator.user.UserDto

data class RegisterRequest(
    var firstName: String,
    var lastName: String,
    var email: String,
    var role: String,
    var password: String,
    var confirmPassword: String
)

data class SignInRequest(
    val email: String,
    val password: String,
)

data class SignInResponse(
    val token: String,
    val user: UserDto
)