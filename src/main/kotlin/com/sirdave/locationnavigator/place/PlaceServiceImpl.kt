package com.sirdave.locationnavigator.place

import com.sirdave.locationnavigator.category.CategoryService
import com.sirdave.locationnavigator.exception.EntityNotFoundException
import com.sirdave.locationnavigator.mapper.toPlaceDto
import com.sirdave.locationnavigator.service.CloudinaryService
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDateTime

@Service
class PlaceServiceImpl(
    private val placeRepository: PlaceRepository,
    private val cloudinaryService: CloudinaryService,
    private val categoryService: CategoryService
): PlaceService{

    override fun createNewPlace(
        name: String,
        alias: String,
        longitude: Double,
        latitude: Double,
        images: List<MultipartFile>?,
        category: String
    ): PlaceDto {
        val placeCategory = categoryService.findCategoryByName(category)

        val place = Place(
            name = name,
            alias = alias,
            longitude = longitude,
            latitude = latitude
        )

        if (!images.isNullOrEmpty()){
            val imageUrls = uploadFiles(images, name)
            place.imageUrls.addAll(imageUrls)
        }
        place.addToCategory(placeCategory)

        return placeRepository.save(place).toPlaceDto()
    }

    override fun searchPlaces(name: String, pageNo: Int, pageSize: Int): List<PlaceDto> {
        val pageable = PageRequest.of(pageNo, pageSize)
        return placeRepository.searchPlaces(name, pageable).map { it.toPlaceDto() }
    }

    override fun getPlacesByCategory(category: String, pageNo: Int, pageSize: Int): List<PlaceDto> {
        val pageable = PageRequest.of(pageNo, pageSize)
        return placeRepository.getPlacesByCategory(category, pageable).map { it.toPlaceDto() }
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
        category: String?,
        images: List<MultipartFile>?
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

        if (category != null) {
            val placeCategory = categoryService.findCategoryByName(category)
            place.addToCategory(placeCategory)
        }

        if (!images.isNullOrEmpty()){
            val imageUrls = uploadFiles(images, place.name)
            place.imageUrls.addAll(imageUrls)
        }

        place.updatedAt = LocalDateTime.now()

        return placeRepository.save(place).toPlaceDto()
    }

    private fun uploadFiles(images: List<MultipartFile>, folder: String): List<String>{
        return cloudinaryService.uploadMultipleFiles(images, folder)
    }

}