package com.sirdave.locationnavigator.constants

object SecurityConstants {
    const val EXPIRATION_DATE = 602_000_000 // 7 days
    const val TOKEN_PREFIX = "Bearer "
    const val JWT_TOKEN_HEADER = "Jwt-Token"
    const val JWT_ISSUER = "University of Ibadan"
    const val JWT_AUDIENCE = "Location Navigator"
    const val AUTHORITIES = "authorities"
    const val OPTIONS_HTTP_METHOD = "OPTIONS"
    const val TOKEN_CANNOT_BE_VERIFIED = "Token cannot be verified"
    const val FORBIDDEN_MESSAGE = "You need to log in to access this page"
    const val ACCESS_DENIED = "You do not have permission to access this page"
    val PUBLIC_URLS = arrayOf("$AUTH_ENDPOINT/**")
}