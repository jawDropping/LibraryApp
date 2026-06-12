package com.day10exercise.libraryApp.service

import com.day10exercise.libraryApp.mapper.toResponse
import com.day10exercise.libraryApp.mapper.toResponse2
import com.day10exercise.libraryApp.model.dto.*
import com.day10exercise.libraryApp.model.entity.Books
import com.day10exercise.libraryApp.model.entity.BorrowStatus
import com.day10exercise.libraryApp.model.entity.status
import com.day10exercise.libraryApp.repository.BookRepository
import com.day10exercise.libraryApp.repository.BorrowedBookRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class BookService(
    private val bookRepository: BookRepository,
    private val borrowedBookRepository: BorrowedBookRepository
) {

    //GET BOOKS THROUGH ITS IDs HELPER SERVICE
    fun getBookByID(id: UUID): Books {
    return bookRepository.findById(id).orElseThrow { RuntimeException("Book not found") }
    }

    fun bookByID(id: UUID): BookResponse {
        val book = getBookByID(id)
        val borrowedCopies =
            borrowedBookRepository
                .countByBookIdAndReturnedAtIsNull(book.id!!)
                .toInt()

        val overdueCopies =
            borrowedBookRepository
                .findByBookId(book.id!!)
                .count { it.status() == BorrowStatus.OVERDUE }

        return book.toResponse(
            borrowedCopies = borrowedCopies,
            overdueCopies = overdueCopies
        )
    }
    //DELETEE BOOK
    fun deleteBook(id: UUID) = bookRepository.deleteById(id)
    //GET ALL BOOKS
    fun getAllBooks(): List<BookResponse> {

        return bookRepository.findAll().map { book ->
            val borrowedCopies = borrowedBookRepository.countByBookIdAndReturnedAtIsNull(book.id!!).toInt()
            val overdueCopies = borrowedBookRepository
                .findByBookId(book.id!!)
                .count { it.status() == BorrowStatus.OVERDUE }

            BookResponse(
                id = book.id!!,
                title = book.title,
                author = book.author,
                isbn = book.isbn,
                genre = book.genre,
                totalCopies = book.totalCopies,
                availableCopies = book.totalCopies - borrowedCopies,
                borrowedCopies = borrowedCopies,
                overdueCopies = overdueCopies,
                isArchived = book.isArchived
            )
        }
    }
    //ADD A BOOK
    fun createBook(request: CreateBookRequest): BookResponse {
        val saved = bookRepository.save(
            Books(
                title = request.title,
                author = request.author,
                isbn = request.isbn,
                genre = request.genre,
                totalCopies = request.totalCopies,
                isArchived = false
            )
        )

        return bookByID(saved.id!!)
    }

    fun updateBook(id:UUID, request: UpdateBookRequest): UpdateBookResponse {
        val book = bookRepository.findById(id).orElseThrow { RuntimeException("Book not found") }
        request.title?.let { book.title = it }
        request.author?.let { book.author = it }
        request.isbn?.let { book.isbn = it }
        request.genre?.let { book.genre = it }
        request.totalCopies?.let { book.totalCopies = it }
        request.isArchived?.let { book.isArchived = it }

        bookRepository.save(book)

        return book.toResponse2()

    }



}