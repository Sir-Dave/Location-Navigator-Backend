package com.sirdave.locationnavigator.mapper

import com.sirdave.locationnavigator.category.Category
import com.sirdave.locationnavigator.category.CategoryDto
import com.sirdave.locationnavigator.helper.toFormattedDate
import com.sirdave.locationnavigator.user.User
import com.sirdave.locationnavigator.user.UserDto

fun Category.toCategoryDto(): CategoryDto {
    return CategoryDto(
        id = id!!,
        name = name
    )
}