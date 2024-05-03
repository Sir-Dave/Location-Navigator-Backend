package com.sirdave.locationnavigator.category

interface CategoryService {
    fun createNewCategory(name: String): Category
    fun findCategoryByName(name: String): Category
    fun getAllCategory(): List<Category>
}