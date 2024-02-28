package com.sirdave.locationnavigator.place

import org.springframework.web.multipart.MultipartFile

interface PlaceService {

    fun createNewPlace(name: String,
                       alias: String,
                       longitude: Double,
                       latitude: Double,
                       images: List<MultipartFile>,
                       placeType: String,
                       category: String?): PlaceDto

    fun searchPlaces(name: String): List<PlaceDto>

    fun getPlacesByPlaceType(type: String): List<PlaceDto>

    fun findPlaceById(id: Long): Place

    fun updatePlace(
        id: Long,
        name: String?,
        alias: String?,
        longitude: Double?,
        latitude: Double?,
        type: String?,
        category: String?
    ): PlaceDto
}