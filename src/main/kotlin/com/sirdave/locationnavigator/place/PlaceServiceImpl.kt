package com.sirdave.locationnavigator.place

import com.sirdave.locationnavigator.exception.EntityNotFoundException
import com.sirdave.locationnavigator.helper.getEnumName
import com.sirdave.locationnavigator.mapper.toPlaceDto
import com.sirdave.locationnavigator.service.CloudinaryService
import org.slf4j.LoggerFactory
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDateTime

@Service
class PlaceServiceImpl(
    private val placeRepository: PlaceRepository,
    private val cloudinaryService: CloudinaryService
): PlaceService{

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
        val imageUrls = uploadFiles(images, name)
        place.imageUrls.addAll(imageUrls)

        return placeRepository.save(place).toPlaceDto()
    }

    override fun searchPlaces(name: String, pageNo: Int, pageSize: Int): List<PlaceDto> {
        val pageable = PageRequest.of(pageNo, pageSize)
        return placeRepository.searchPlaces(name, pageable).map { it.toPlaceDto() }
    }

    override fun getPlacesByPlaceType(type: String, pageNo: Int, pageSize: Int): List<PlaceDto> {
        val placeType = getEnumName<PlaceType>(type)
        val pageable = PageRequest.of(pageNo, pageSize)
        return placeRepository.getPlacesByPlaceType(placeType, pageable).map { it.toPlaceDto() }
    }

    override fun findAll(pageNo: Int, pageSize: Int): List<PlaceDto> {
        val pageable = PageRequest.of(pageNo, pageSize)
        val page = placeRepository.findAll(pageable)
        return if (page.hasContent()) page.content.map { it.toPlaceDto() } else emptyList()
    }

    override fun getOnePlace(id: Long): PlaceDto {
        return findPlaceById(id).toPlaceDto()
    }

    private fun findPlaceById(id: Long): Place {
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

    private fun uploadFiles(images: List<MultipartFile>, folder: String): List<String>{
        return cloudinaryService.uploadMultipleFiles(images, folder)
    }

}