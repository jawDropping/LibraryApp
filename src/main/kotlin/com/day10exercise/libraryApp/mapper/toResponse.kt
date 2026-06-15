package com.day10exercise.libraryApp.mapper

import com.day10exercise.libraryApp.model.dto.BookResponse
import com.day10exercise.libraryApp.model.dto.BorrowedBookResponse
import com.day10exercise.libraryApp.model.entity.BorrowStatus
import com.day10exercise.libraryApp.model.entity.BorrowedBooks
import com.day10exercise.libraryApp.model.dto.CreateStudentRequest
import com.day10exercise.libraryApp.model.dto.StudentResponse
import com.day10exercise.libraryApp.model.dto.UpdateBookResponse
import com.day10exercise.libraryApp.model.entity.Books
import com.day10exercise.libraryApp.model.entity.Student
import com.day10exercise.libraryApp.model.entity.status
import kotlin.String

fun BorrowedBooks.toResponse() =
    BorrowedBookResponse(
        id = id!!,
        studentId = student.id!!,
        studentName = "${student.firstName} ${student.lastName}",
        bookId = book.id!!,
        bookTitle = book.title,
        borrowedAt = borrowedAt,
        returnedAt = returnedAt,
        borrowStatus = this.status()
    )

fun Student.toResponse(): StudentResponse =
    StudentResponse(
        id = id!!,
        firstName = firstName,
        lastName = lastName,
        email = email,
        isActive = isActive,
        createdAt = createdAt
    )

fun CreateStudentRequest.toEntity(): Student =
    Student(
        firstName = firstName,
        lastName = lastName,
        email = email,
        password = password,
        role = "STUDENT"
    )

fun Books.toResponse(borrowedCopies: Int, overdueCopies: Int) : BookResponse =
    BookResponse(
        id = id!!,
        title = title,
        author = author,
        isbn = isbn,
        genre = genre,
        totalCopies = totalCopies,
        borrowedCopies = borrowedCopies,
        overdueCopies = overdueCopies,
        availableCopies = totalCopies - borrowedCopies,
        isArchived = isArchived,
    )

fun Books.toResponse2() : UpdateBookResponse =
    UpdateBookResponse(
        title = title,
        author = author,
        genre = genre,
        isbn = isbn,
        totalCopies = totalCopies,
        isArchived = isArchived,
    )