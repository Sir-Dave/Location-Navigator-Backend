package com.sirdave.locationnavigator.place

import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface PlaceRepository: JpaRepository<Place, Long>{

    @Query("SELECT p FROM Place p WHERE LOWER(p.name) LIKE LOWER(concat('%', :name,'%')) OR " +
            "LOWER(p.alias) LIKE LOWER(concat('%', :name,'%'))")
    fun searchPlaces(@Param("name") name: String, pageable: Pageable): List<Place>

    @Query("SELECT p FROM Place p WHERE p.placeCategory.name = ?1")
    fun getPlacesByCategory(category: String, pageable: Pageable): List<Place>


}