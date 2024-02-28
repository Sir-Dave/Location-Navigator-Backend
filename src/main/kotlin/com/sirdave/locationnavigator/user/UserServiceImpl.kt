package com.sirdave.locationnavigator.user

import com.sirdave.locationnavigator.exception.EntityNotFoundException
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class UserServiceImpl(private val userRepository: UserRepository) : UserService {

    override fun saveUser(user: User) {
        userRepository.save(user)
    }

    override fun findUserById(id: Long): User {
        return userRepository.findById(id)
            .orElseThrow { EntityNotFoundException("No user with id $id was found") }
    }

    override fun findUserByEmail(email: String): User {
        return userRepository.findByEmail(email)
            .orElseThrow { EntityNotFoundException("No user with email $email was found") }

    }

    override fun isUserDoesNotExist(email: String): Boolean {
        val userByEmail = userRepository.findByEmail(email)
        return userByEmail.isEmpty
    }
}