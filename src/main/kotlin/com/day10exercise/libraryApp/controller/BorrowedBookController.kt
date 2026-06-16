package com.day10exercise.libraryApp.controller

import com.day10exercise.libraryApp.model.dto.BorrowBookRequest
import com.day10exercise.libraryApp.service.BorrowedBookService
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/v1/borrowed-books")
class BorrowedBookController(
    private val borrowedBookService: BorrowedBookService
) {

    @GetMapping
    fun getAllBorrowedBooks() = borrowedBookService.getAllBorrowedBooks()

    @PostMapping
    fun borrowBook(@RequestBody request: BorrowBookRequest) = borrowedBookService.borrowBook(request)

    @PatchMapping("/{id}/return")
    fun returnBook(@PathVariable id: UUID) = borrowedBookService.returnBook(id)
}