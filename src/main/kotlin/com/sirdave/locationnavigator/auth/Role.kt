package com.sirdave.locationnavigator.auth

import com.sirdave.locationnavigator.auth.Authority.ADMIN_AUTHORITIES
import com.sirdave.locationnavigator.auth.Authority.USER_AUTHORITIES

enum class Role(val authorities: Array<String>) {
    ROLE_USER(USER_AUTHORITIES),
    ROLE_ADMIN(ADMIN_AUTHORITIES)
}