package com.day10exercise.libraryApp.repository

import com.day10exercise.libraryApp.model.entity.Books
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface BookRepository : JpaRepository<Books, UUID>