package com.sirdave.locationnavigator.mapper

import com.sirdave.locationnavigator.helper.toFormattedDate
import com.sirdave.locationnavigator.place.Place
import com.sirdave.locationnavigator.place.PlaceDto

fun Place.toPlaceDto(): PlaceDto {

    return PlaceDto(
        id = id!!,
        name = name,
        alias = alias,
        longitude = longitude,
        latitude = latitude,
        placeType = placeType.title,
        category = category ?: "",
        imageUrls = imageUrls,
        createdAt = createdAt.toFormattedDate(),
        updatedAt = updatedAt?.toFormattedDate() ?: ""
    )
}