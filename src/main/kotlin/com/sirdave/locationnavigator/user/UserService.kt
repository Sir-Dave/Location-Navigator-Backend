package com.sirdave.locationnavigator.user

interface UserService {

    fun saveUser(user: User)

    fun findUserById(id: Long): User

    fun findUserByEmail(email: String): User

    fun isUserDoesNotExist(email: String): Boolean

}