package com.sirdave.locationnavigator.auth

const val VIEW_PLACES = "view-places"
const val CREATE_PLACES = "create-places"
const val MANAGE_PLACES = "manage-places"
const val MANAGE_USERS = "manage-users"

object Authority {
    val USER_AUTHORITIES = arrayOf(
        VIEW_PLACES,
        CREATE_PLACES
    )

    val ADMIN_AUTHORITIES = arrayOf(
        VIEW_PLACES,
        CREATE_PLACES,
        MANAGE_USERS,
        MANAGE_PLACES
    )
}