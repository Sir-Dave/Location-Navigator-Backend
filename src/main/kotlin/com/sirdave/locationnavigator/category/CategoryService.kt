package com.sirdave.locationnavigator.category

interface CategoryService {
    fun createNewCategory(name: String): CategoryDto
    fun findCategoryByName(name: String): CategoryDto
    fun getAllCategories(): List<CategoryDto>

    fun updateCategoryName(id: Long, name: String): CategoryDto
}