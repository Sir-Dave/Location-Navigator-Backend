package com.sirdave.locationnavigator.user

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "users")
class User (
    var firstName: String,
    var lastName: String,
    var email: String,
    var password: String,
    var dateJoined: LocalDateTime,
    var role: String,
    var authorities: Array<String>,
    var isActive: Boolean,
    var isNotLocked: Boolean){

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
    @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize = 1)
    @Column(nullable = false, updatable = false)
    val id: Long? = null
}