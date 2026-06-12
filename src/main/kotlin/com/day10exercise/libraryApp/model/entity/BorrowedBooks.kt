package com.day10exercise.libraryApp.model.entity

import jakarta.persistence.*
import java.time.OffsetDateTime
import java.util.UUID

@Entity
@Table(name = "borrowed_books")
class BorrowedBooks(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    var student: Student,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    var book: Books,
    var borrowedAt: OffsetDateTime = OffsetDateTime.now(),
    var returnedAt: OffsetDateTime?= null,
)