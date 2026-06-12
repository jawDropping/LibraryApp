package com.day10exercise.libraryApp.repository

import com.day10exercise.libraryApp.model.entity.Student
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface StudentRepository : JpaRepository<Student, UUID>