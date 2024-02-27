package com.sirdave.locationnavigator.place

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "places")
class Place (
    var name: String,
    var alias: String,
    var longitude: Double,
    var latitude: Double,

    @Enumerated(EnumType.STRING)
    var placeType: PlaceType,

    var createdAt: LocalDateTime = LocalDateTime.now(),
    var metadata: MutableMap<String, String> = hashMapOf()
){
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "place_sequence")
    @SequenceGenerator(name = "place_sequence", sequenceName = "place_sequence", allocationSize = 1)
    @Column(nullable = false, updatable = false)
    val id: Long? = null

    var imageUrls: List<String> = arrayListOf()

    var category: String? = null

    var updatedAt: LocalDateTime? = null
}