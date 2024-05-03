package com.sirdave.locationnavigator.category

import com.sirdave.locationnavigator.place.Place
import javax.persistence.*

@Entity
@Table(name = "categories")
class Category(
    val name: String
){
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_sequence")
    @SequenceGenerator(name = "category_sequence", sequenceName = "category_sequence", allocationSize = 1)
    @Column(nullable = false, updatable = false)
    val id: Long? = null


    @OneToMany(mappedBy = "placeCategory", cascade = [CascadeType.ALL])
    val places: MutableList<Place> = mutableListOf()

    fun addPlace(place: Place){
        places.add(place)
        place.placeCategory = this
    }

    fun removePlace(place: Place){
        places.remove(place)
        place.placeCategory =  null
    }

}
