package com.sirdave.locationnavigator.place

import org.springframework.web.multipart.MultipartFile

interface PlaceService {

    fun createNewPlace(name: String,
                       alias: String,
                       longitude: Double,
                       latitude: Double,
                       images: List<MultipartFile>?,
                       category: String
    ): PlaceDto

    fun searchPlaces(name: String, pageNo: Int, pageSize: Int): List<PlaceDto>

    fun getPlacesByCategory(category: String, pageNo: Int, pageSize: Int): List<PlaceDto>

    fun findAll(pageNo: Int, pageSize: Int): List<PlaceDto>

    fun getOnePlace(id: Long): PlaceDto

    fun updatePlace(
        id: Long,
        name: String?,
        alias: String?,
        longitude: Double?,
        latitude: Double?,
        category: String?,
        images: List<MultipartFile>?
    ): PlaceDto
}