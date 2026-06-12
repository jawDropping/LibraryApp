package com.day10exercise.libraryApp.repository

import com.day10exercise.libraryApp.model.entity.BorrowedBooks
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.OffsetDateTime
import java.util.UUID

interface BorrowedBookRepository : JpaRepository<BorrowedBooks, UUID> {

    fun countByBookIdAndReturnedAtIsNull(bookId: UUID): Long
    fun findByReturnedAtIsNull(): List<BorrowedBooks>

    fun findByBookId(bookId: UUID): List<BorrowedBooks>
}