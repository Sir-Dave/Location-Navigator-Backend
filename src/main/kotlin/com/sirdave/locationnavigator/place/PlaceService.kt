package com.sirdave.locationnavigator.place

import org.springframework.web.multipart.MultipartFile

interface PlaceService {

    fun createNewPlace(name: String,
                       alias: String,
                       longitude: Double,
                       latitude: Double,
                       images: List<MultipartFile>?,
                       placeType: String,
                       category: String?): PlaceDto

    fun searchPlaces(name: String, pageNo: Int, pageSize: Int): List<PlaceDto>

    fun getPlacesByPlaceType(type: String, pageNo: Int, pageSize: Int): List<PlaceDto>

    fun findAll(pageNo: Int, pageSize: Int): List<PlaceDto>

    fun getOnePlace(id: Long): PlaceDto

    fun updatePlace(
        id: Long,
        name: String?,
        alias: String?,
        longitude: Double?,
        latitude: Double?,
        type: String?,
        category: String?,
        images: List<MultipartFile>?
    ): PlaceDto
}