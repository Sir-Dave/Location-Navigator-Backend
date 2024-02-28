package com.sirdave.locationnavigator.auth

import com.sirdave.locationnavigator.exception.EntityExistsException
import com.sirdave.locationnavigator.exception.PasswordsDoNotMatchException
import com.sirdave.locationnavigator.helper.getEnumName
import com.sirdave.locationnavigator.user.User
import com.sirdave.locationnavigator.user.UserPrincipal
import com.sirdave.locationnavigator.user.UserService
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
@Qualifier("userDetailsService")
class AuthServiceImpl(
    private val userService: UserService,
    private val passwordEncoder: BCryptPasswordEncoder,
    private val loginAttemptService: LoginAttemptService
) : AuthService, UserDetailsService {

    override fun loadUserByUsername(email: String): UserDetails {
        val user = userService.findUserByEmail(email)
        validateLoginAttempt(user)
        userService.saveUser(user)
        return UserPrincipal(user)
    }

    override fun register(registerRequest: RegisterRequest) {
        validateNewUser(registerRequest.email)
        doPasswordsMatch(registerRequest.password, registerRequest.confirmPassword)

        val encodedPassword = encodePassword(registerRequest.password)
        val role = getEnumName<Role>(registerRequest.role)

        val user = User(
            firstName = registerRequest.firstName,
            lastName = registerRequest.lastName,
            email = registerRequest.email,
            password = encodedPassword,
            dateJoined = LocalDateTime.now(),
            role = role.name,
            authorities = role.authorities,
            isActive = true,
            isNotLocked = true
        )
        //TODO: Implement a mailing service to verify user emails
        userService.saveUser(user)
    }

    private fun validateLoginAttempt(user: User){
        if (user.isNotLocked)
            user.isNotLocked = !loginAttemptService.hasExceededMaximumAttempt(user.email)

        else loginAttemptService.evictUserFromLoginAttemptCache(user.email)
    }

    private fun validateNewUser(email: String){
        check(userService.isUserDoesNotExist(email)){
            throw EntityExistsException("User with email $email already exists")
        }
    }

    private fun doPasswordsMatch(p1: String, p2: String){
        check(p1 == p2){
            throw PasswordsDoNotMatchException("Passwords do not match")
        }
    }

    private fun encodePassword(password: String): String{
        return passwordEncoder.encode(password)
    }
}