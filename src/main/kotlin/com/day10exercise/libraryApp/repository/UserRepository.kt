package com.day10exercise.libraryApp.repository

import com.day10exercise.libraryApp.model.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface UserRepository : JpaRepository<User, UUID> {

    // Used during Login and Jwt loading
    fun findByEmail(email: String): User?

    // Used during Registration/Updates to prevent duplicate accounts
    fun existsByEmail(email: String): Boolean
}