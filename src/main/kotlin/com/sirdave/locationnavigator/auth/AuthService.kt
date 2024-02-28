package com.sirdave.locationnavigator.auth

interface AuthService {
    fun register(registerRequest: RegisterRequest)
}