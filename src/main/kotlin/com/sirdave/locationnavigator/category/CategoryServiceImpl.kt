package com.sirdave.locationnavigator.category

import com.sirdave.locationnavigator.exception.EntityExistsException
import com.sirdave.locationnavigator.exception.EntityNotFoundException
import org.springframework.stereotype.Service

@Service
class CategoryServiceImpl(private val repository: CategoryRepository): CategoryService{
    override fun createNewCategory(name: String): Category {
        val optionalCategory = repository.findCategoryByName(name)
        check(optionalCategory.isEmpty){
            EntityExistsException("Category with name $name already exists")
        }
        val category = Category(name = name)
        return repository.save(category)
    }

    override fun findCategoryByName(name: String): Category {
        return repository.findCategoryByName(name).orElseThrow{
            EntityNotFoundException("No category with name $name was found")
        }
    }

    override fun getAllCategory(): List<Category> {
        return repository.findAll()
    }

}