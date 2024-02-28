package com.sirdave.locationnavigator.place

import com.sirdave.locationnavigator.exception.EntityNotFoundException
import com.sirdave.locationnavigator.helper.getEnumName
import com.sirdave.locationnavigator.mapper.toPlaceDto
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDateTime

@Service
class PlaceServiceImpl(private val placeRepository: PlaceRepository): PlaceService{

    override fun createNewPlace(
        name: String,
        alias: String,
        longitude: Double,
        latitude: Double,
        images: List<MultipartFile>,
        placeType: String,
        category: String?
    ): PlaceDto {
        val type = getEnumName<PlaceType>(placeType)

        val place = Place(
            name = name,
            alias = alias,
            longitude = longitude,
            latitude = latitude,
            placeType = type
        )
        if (!category.isNullOrBlank() && type == PlaceType.HALL_OF_RESIDENCE){
            val hostelCategory = getEnumName<HostelCategory>(category)
            place.category = hostelCategory.title
        }
        val imageUrls = uploadFiles(images)
        place.imageUrls.addAll(imageUrls)

        return placeRepository.save(place).toPlaceDto()
    }

    override fun searchPlaces(name: String): List<PlaceDto> {
        return placeRepository.searchPlaces(name).map { it.toPlaceDto() }
    }

    override fun getPlacesByPlaceType(type: String): List<PlaceDto> {
        val placeType = getEnumName<PlaceType>(type)
        return placeRepository.getPlacesByPlaceType(placeType).map { it.toPlaceDto() }
    }

    override fun findPlaceById(id: Long): Place {
        return placeRepository.findById(id).orElseThrow { EntityNotFoundException("Place with id $id does not exist") }
    }

    override fun updatePlace(
        id: Long,
        name: String?,
        alias: String?,
        longitude: Double?,
        latitude: Double?,
        type: String?,
        category: String?
    ): PlaceDto {
        val place = findPlaceById(id)

        if (!name.isNullOrBlank())
            place.name = name

        if (!alias.isNullOrBlank())
            place.alias = alias

        if (longitude != null)
            place.longitude = longitude

        if (latitude != null)
            place.latitude = latitude

        if (type != null) {
            val placeType = getEnumName<PlaceType>(type)
            place.placeType = placeType
        }

        if (category != null && type != null) {
            val placeType = getEnumName<PlaceType>(type)
            val hostelCategory = getEnumName<HostelCategory>(category)
            if (placeType == PlaceType.HALL_OF_RESIDENCE)
                place.category = hostelCategory.title
        }

        place.updatedAt = LocalDateTime.now()

        return placeRepository.save(place).toPlaceDto()
    }

    private fun uploadFiles(images: List<MultipartFile>): List<String>{
        //TODO: Implement this
        return emptyList()
    }

}