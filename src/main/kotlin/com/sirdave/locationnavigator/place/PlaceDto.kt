package com.sirdave.locationnavigator.place

import java.time.LocalDateTime

data class PlaceDto(
    val id: Long,
    var name: String,
    var alias: String,
    var longitude: Double,
    var latitude: Double,
    var placeType: String,
    var category: String,
    var imageUrls: List<String>,
    var createdAt: LocalDateTime,
    var updatedAt: LocalDateTime?
)
