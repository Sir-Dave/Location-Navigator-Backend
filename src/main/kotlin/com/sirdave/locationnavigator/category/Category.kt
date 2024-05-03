package com.sirdave.locationnavigator.category

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
}
