package com.sirdave.locationnavigator.listeners

import com.sirdave.locationnavigator.auth.LoginAttemptService
import com.sirdave.locationnavigator.user.UserPrincipal
import org.springframework.context.event.EventListener
import org.springframework.security.authentication.event.AuthenticationSuccessEvent
import org.springframework.stereotype.Component

@Component
class AuthenticationSuccessListener(private val loginAttemptService: LoginAttemptService) {

    @EventListener
    fun onAuthenticationSuccess(event: AuthenticationSuccessEvent){
        val principal = event.authentication.principal
        if (principal is UserPrincipal){
            loginAttemptService.evictUserFromLoginAttemptCache(principal.username)
        }
    }
}