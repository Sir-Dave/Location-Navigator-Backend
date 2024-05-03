package com.sirdave.locationnavigator.category

import com.sirdave.locationnavigator.constants.CATEGORIES_ENDPOINT
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(path = [CATEGORIES_ENDPOINT])
class CategoryController(private val service: CategoryService) {

    @PostMapping
    fun createNewCategory(
        @RequestParam name: String,
    ): ResponseEntity<CategoryDto> {
        val category = service.createNewCategory(name = name)
        return ResponseEntity(category, HttpStatus.CREATED)
    }

    @PutMapping("/{id}")
    fun updateCategoryName(
        @PathVariable("id") id: Long,
        @RequestParam name: String,
    ): ResponseEntity<CategoryDto> {
        val category = service.updateCategoryName(
            id = id,
            name = name
        )
        return ResponseEntity(category, HttpStatus.OK)
    }

    @GetMapping
    fun getAllCategories(
        @RequestParam(required = false) name: String?,
    ): ResponseEntity<List<CategoryDto>> {
        val categories = if (name.isNullOrBlank()) service.getAllCategories()
        else listOf(service.findCategoryByName(name))
        return ResponseEntity(categories, HttpStatus.OK)
    }
}