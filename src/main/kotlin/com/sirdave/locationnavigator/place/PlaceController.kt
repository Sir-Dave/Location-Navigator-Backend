package com.sirdave.locationnavigator.place

import com.sirdave.locationnavigator.constants.PLACES_ENDPOINT
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping(path = [PLACES_ENDPOINT])
class PlaceController(private val service: PlaceService) {

    @PostMapping
    fun createNewPlace(
        @RequestParam name: String,
        @RequestParam alias: String,
        @RequestParam longitude: Double,
        @RequestParam latitude: Double,
        @RequestPart images: List<MultipartFile>,
        @RequestParam placeType: String,
        @RequestParam(required = false) category: String?
    ): ResponseEntity<PlaceDto> {
        val place = service.createNewPlace(
            name = name,
            alias = alias,
            longitude = longitude,
            latitude = latitude,
            images = images,
            placeType = placeType,
            category = category
        )
        return ResponseEntity(place, HttpStatus.CREATED)
    }

    @PutMapping("/{id}")
    fun updatePlace(
        @PathVariable("id") id: Long,
        @RequestParam(required = false) name: String?,
        @RequestParam(required = false) alias: String?,
        @RequestParam(required = false) longitude: Double?,
        @RequestParam(required = false) latitude: Double?,
        @RequestParam(required = false) placeType: String?,
        @RequestParam(required = false) category: String?,
        @RequestPart(required = false) images: List<MultipartFile>,
    ): ResponseEntity<PlaceDto> {
        val place = service.updatePlace(
            id = id,
            name = name,
            alias = alias,
            longitude = longitude,
            latitude = latitude,
            type = placeType,
            category = category,
            images = images
        )
        return ResponseEntity(place, HttpStatus.OK)
    }

    @GetMapping
    fun searchPlaces(
        @RequestParam(required = false) name: String?,
        @RequestParam(required = false) type: String?,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "20") pageSize: Int,
    ): ResponseEntity<List<PlaceDto>> {
        val places = if (!name.isNullOrBlank())
            service.searchPlaces(name = name, pageNo = page, pageSize = pageSize)

        else if (!type.isNullOrBlank())
            service.getPlacesByPlaceType(type = type, pageNo = page, pageSize = pageSize)

        else service.findAll(pageNo = page, pageSize = pageSize)
        return ResponseEntity(places, HttpStatus.OK)
    }

    @GetMapping("/{id}")
    fun getPlace(
        @PathVariable("id") id: Long): ResponseEntity<PlaceDto> {
        val place = service.getOnePlace(id = id)
        return ResponseEntity(place, HttpStatus.OK)
    }
}