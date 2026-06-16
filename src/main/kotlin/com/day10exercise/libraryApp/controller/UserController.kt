package com.day10exercise.libraryApp.controller

import com.day10exercise.libraryApp.model.dto.UpdateAccountRequest
import com.day10exercise.libraryApp.model.dto.UserResponse
import com.day10exercise.libraryApp.model.dto.UserUpdateRequest
import com.day10exercise.libraryApp.service.UserService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/v1/users")
class UserController(
    private val userService: UserService
) {

    @GetMapping
    fun getAllUsers(): ResponseEntity<List<UserResponse>> {
        return ResponseEntity.ok(userService.getAllUsers())
    }

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: UUID): ResponseEntity<UserResponse> {
        return ResponseEntity.ok(userService.getUserById(id))
    }

    @PutMapping("/{id}")
    fun updateUserProfile(
        @PathVariable id: UUID,
        @RequestBody request: UserUpdateRequest
    ): ResponseEntity<UserResponse> {
        return ResponseEntity.ok(userService.updateUser(id, request))
    }

    @PatchMapping("/{id}/account")
    fun updateAccountCredentials(
        @PathVariable id: UUID,
        @Valid @RequestBody request: UpdateAccountRequest
    ): ResponseEntity<UserResponse> {
        return ResponseEntity.ok(userService.updateAccountCredentials(id, request))
    }

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: UUID): ResponseEntity<Void> {
        userService.deleteUser(id)
        return ResponseEntity.noContent().build()
    }
}