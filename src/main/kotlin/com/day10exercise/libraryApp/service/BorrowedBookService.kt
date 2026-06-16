package com.day10exercise.libraryApp.service

import com.day10exercise.libraryApp.exception.NotFoundException
import com.day10exercise.libraryApp.exception.BusinessException
import com.day10exercise.libraryApp.mapper.toResponse
import com.day10exercise.libraryApp.model.dto.BorrowBookRequest
import com.day10exercise.libraryApp.model.dto.BorrowedBookResponse
import com.day10exercise.libraryApp.model.entity.BorrowedBooks
import com.day10exercise.libraryApp.repository.BookRepository
import com.day10exercise.libraryApp.repository.BorrowedBookRepository
import com.day10exercise.libraryApp.repository.StudentRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.time.OffsetDateTime
import java.util.UUID

@Service
@Transactional
class BorrowedBookService(
    private val studentRepository: StudentRepository,
    private val bookRepository: BookRepository,
    private val borrowedBookRepository: BorrowedBookRepository
) {

    //FOR POST MAPPING "BORROW SIYA UG BOOK"
    fun borrowBook(request: BorrowBookRequest): BorrowedBookResponse {

        val student = studentRepository.findById(request.studentId).
        orElseThrow { NotFoundException("Student not found") }
        val book = bookRepository.findById(request.bookId)
            .orElseThrow { NotFoundException("Book not found") }

        if (book.isArchived) {
            throw BusinessException("Book is archived")
        }
        val borrowedCopies = borrowedBookRepository.countByBookIdAndReturnedAtIsNull(book.id!!)
        val availableCopies = book.totalCopies - borrowedCopies

        if (availableCopies <= 1) {
            throw BusinessException("Cannot borrow when only 1 copy remains")
        }

        val borrowedBook = BorrowedBooks(
            student = student,
            book = book
        )
        val saved = borrowedBookRepository.save(borrowedBook)
        return saved.toResponse()
    }


    // GET MAPPING TO GET SA MGA BOOKS
    fun getAllBorrowedBooks(): List<BorrowedBookResponse> =
        borrowedBookRepository.findAll()
            .map { it.toResponse() }


    // IT WILL SET STATUS SA BOOK INTO RETURNED
    fun returnBook(id: UUID): BorrowedBookResponse {

        val borrowedBook = borrowedBookRepository.findById(id)
            .orElseThrow { RuntimeException("Borrowed book not found") }

        if (borrowedBook.returnedAt != null) {
            throw RuntimeException("Book already returned")
        }

        borrowedBook.returnedAt = OffsetDateTime.now()

        val updated = borrowedBookRepository.save(borrowedBook)
        return updated.toResponse()
    }


}