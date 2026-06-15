package com.day10exercise.libraryApp.model.entity

import jakarta.persistence.*
import java.time.OffsetDateTime
import java.util.UUID

@Entity
@Table(name = "students")
class Student(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null,
    var firstName: String,
    var lastName: String,
    var email: String,
    var password: String,
    var role: String,
    var isActive: Boolean = true,
    var createdAt: OffsetDateTime = OffsetDateTime.now()
)