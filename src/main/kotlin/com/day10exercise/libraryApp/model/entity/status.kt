package com.day10exercise.libraryApp.model.entity

import java.time.OffsetDateTime

fun BorrowedBooks.status() : BorrowStatus {
    val overdueDate = borrowedAt.plusHours(48)
    if (returnedAt != null){
        return BorrowStatus.RETURNED
    }


    return if (OffsetDateTime.now().isAfter(overdueDate)){
        BorrowStatus.OVERDUE
    }else{
        BorrowStatus.BORROWED
    }
}