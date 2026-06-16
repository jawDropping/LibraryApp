package com.day10exercise.libraryApp.mapper

import com.day10exercise.libraryApp.model.dto.BookResponse
import com.day10exercise.libraryApp.model.dto.BorrowedBookResponse
import com.day10exercise.libraryApp.model.entity.BorrowStatus
import com.day10exercise.libraryApp.model.entity.BorrowedBooks
import com.day10exercise.libraryApp.model.dto.CreateStudentRequest
import com.day10exercise.libraryApp.model.dto.StudentProfileDetails
import com.day10exercise.libraryApp.model.dto.StudentResponse
import com.day10exercise.libraryApp.model.dto.UpdateBookResponse
import com.day10exercise.libraryApp.model.dto.UserResponse
import com.day10exercise.libraryApp.model.entity.Books
import com.day10exercise.libraryApp.model.entity.Student
import com.day10exercise.libraryApp.model.entity.User
import com.day10exercise.libraryApp.model.entity.status
import org.hibernate.engine.transaction.internal.jta.JtaStatusHelper.isActive
import kotlin.String

fun BorrowedBooks.toResponse() =
    BorrowedBookResponse(
        id = id!!,
        studentId = student.id!!,
        studentName = "${student.user?.firstName} ${student.user?.lastName}",
        bookId = book.id!!,
        bookTitle = book.title,
        borrowedAt = borrowedAt,
        returnedAt = returnedAt,
        borrowStatus = this.status()
    )

fun Student.toResponse(explicitUser: User? = null): StudentResponse {
    val userTarget = explicitUser ?: this.user

    return StudentResponse(
        id = id!!,
        firstName = userTarget.firstName,
        lastName = userTarget.lastName,
        email = userTarget.email,
        isActive = isActive,
        createdAt = createdAt
    )
}

fun CreateStudentRequest.toEntity(user: User): Student =
    Student(
        user = user,
        isActive = true,
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

fun User.toResponse(student: Student? = null): UserResponse {
    val studentDetails = student?.let {
        StudentProfileDetails(
            studentId = it.id!!,
            isActive = it.isActive,
            createdAt = it.createdAt
        )
    }

    return UserResponse(
        id = this.id!!,
        firstName = this.firstName,
        lastName = this.lastName,
        email = this.email,
        roles = this.roles,
        studentProfile = studentDetails
    )
}