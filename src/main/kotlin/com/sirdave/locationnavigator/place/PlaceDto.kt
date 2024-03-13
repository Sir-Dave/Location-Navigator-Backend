package com.sirdave.locationnavigator.place

data class PlaceDto(
    val id: Long,
    var name: String,
    var alias: String,
    var longitude: Double,
    var latitude: Double,
    var placeType: String,
    var category: String?,
    var imageUrls: List<String>,
    var createdAt: String,
    var updatedAt: String?
)
