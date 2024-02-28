package com.sirdave.locationnavigator.auth

import com.sirdave.locationnavigator.helper.ApiResponse
import com.sirdave.locationnavigator.mapper.toUserDto
import com.sirdave.locationnavigator.security.JwtTokenProvider
import com.sirdave.locationnavigator.user.UserPrincipal
import com.sirdave.locationnavigator.user.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping(path = ["/api/v1/auth"])
class AuthController(
    private val authService: AuthService,
    private val userService: UserService,
    private val jwtTokenProvider: JwtTokenProvider,
    private val authenticationManager: AuthenticationManager
) {

    @PostMapping("/register")
    fun registerUser(
        @RequestBody registerRequest: RegisterRequest,
        servletRequest: HttpServletRequest
    ): ResponseEntity<ApiResponse> {
        authService.register(registerRequest)
        val response = ApiResponse(
            HttpStatus.CREATED.value(),
            HttpStatus.CREATED,
            HttpStatus.CREATED.reasonPhrase,
            "User registered successfully."
        )
        return ResponseEntity(response, HttpStatus.CREATED)
    }


    @PostMapping("/login")
    fun loginUser(@RequestBody signInRequest: SignInRequest): ResponseEntity<SignInResponse> {
        authenticateUser(signInRequest.email, signInRequest.password)
        val user = userService.findUserByEmail(signInRequest.email)

        val userPrincipal = UserPrincipal(user)
        val token = jwtTokenProvider.generateJwtToken(userPrincipal)
        val response = SignInResponse(token, user.toUserDto())
        return ResponseEntity(response, HttpStatus.OK)
    }

    private fun authenticateUser(email: String, password: String){
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(email, password)
        )
    }
}