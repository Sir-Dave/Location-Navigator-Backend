package com.sirdave.locationnavigator.category

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface CategoryRepository: JpaRepository<Category, Long> {

    @Query("SELECT c FROM Category c WHERE c.name = ?1")
    fun findCategoryByName(name: String): Optional<Category>
}