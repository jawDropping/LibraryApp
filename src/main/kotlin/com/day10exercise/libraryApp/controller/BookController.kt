package com.day10exercise.libraryApp.controller

import com.day10exercise.libraryApp.model.dto.*
import com.day10exercise.libraryApp.service.BookService
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/books")
class BookController(
    private val bookService: BookService
) {

    @GetMapping
    fun getAllBooks() = bookService.getAllBooks()

    @GetMapping("/{id}")
    fun getBookByID(@PathVariable id: UUID) = bookService.bookByID(id)

    @PostMapping
    fun createBook(@RequestBody request: CreateBookRequest) = bookService.createBook(request)

    @DeleteMapping("/{id}")
    fun deleteBook(@PathVariable id: UUID) {
        bookService.deleteBook(id)
    }
    @PatchMapping("/{id}")
    fun updateBook(@PathVariable id: UUID, @RequestBody request: UpdateBookRequest): UpdateBookResponse {
        return bookService.updateBook(id, request)
    }
}