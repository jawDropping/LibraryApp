package com.day10exercise.libraryApp.repository

import com.day10exercise.libraryApp.model.entity.Student
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional
import java.util.UUID

interface StudentRepository : JpaRepository<Student, UUID> {

    @EntityGraph(attributePaths = ["user"])
    fun findByUser_Id(userId: UUID): Optional<Student>

    @EntityGraph(attributePaths = ["user"])
    override fun findAll(): List<Student>

    @EntityGraph(attributePaths = ["user"])
    override fun findById(id: UUID): Optional<Student>

    @EntityGraph(attributePaths = ["user"])
    fun findByUser_Email(email: String): Optional<Student>
}