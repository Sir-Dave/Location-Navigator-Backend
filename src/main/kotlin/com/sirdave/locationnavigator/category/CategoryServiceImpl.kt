package com.sirdave.locationnavigator.category

import com.sirdave.locationnavigator.exception.EntityExistsException
import com.sirdave.locationnavigator.exception.EntityNotFoundException
import com.sirdave.locationnavigator.mapper.toCategoryDto
import org.springframework.stereotype.Service

@Service
class CategoryServiceImpl(private val repository: CategoryRepository): CategoryService{
    override fun createNewCategory(name: String): CategoryDto {
        val optionalCategory = repository.findCategoryByName(name)

        check(optionalCategory.isEmpty){
            throw EntityExistsException("Category with name $name already exists")
        }
        val category = Category(name = name)
        return repository.save(category).toCategoryDto()
    }

    override fun findCategoryByName(name: String): CategoryDto {
        return repository.findCategoryByName(name).orElseThrow{
            EntityNotFoundException("No category with name $name was found")
        }.toCategoryDto()
    }

    override fun getAllCategories(): List<CategoryDto> {
        return repository.findAll().map { it.toCategoryDto() }
    }

    override fun updateCategoryName(id: Long, name: String): CategoryDto {
        val category = repository.findById(id).orElseThrow{
            EntityNotFoundException("No category with id $id was found")
        }

        val existingCategory = repository.findCategoryByName(name)
        check(existingCategory.isEmpty){
            throw EntityExistsException("Category with name $name already exists")
        }
        category.name = name
        return repository.save(category).toCategoryDto()
    }

}