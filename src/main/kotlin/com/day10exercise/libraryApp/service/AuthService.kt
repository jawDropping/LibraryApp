package com.day10exercise.libraryApp.service

import com.day10exercise.libraryApp.model.dto.AuthResponse
import com.day10exercise.libraryApp.model.dto.LoginRequest
import com.day10exercise.libraryApp.model.dto.RegisterRequest
import com.day10exercise.libraryApp.model.entity.Role
import com.day10exercise.libraryApp.model.entity.User
import com.day10exercise.libraryApp.repository.UserRepository
import com.day10exercise.libraryApp.security.JwtService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JwtService,
    private val authenticationManager: AuthenticationManager,
    private val customUserDetailsService: CustomUserDetailsService
) {

    fun register(registerRequest: RegisterRequest): AuthResponse {
        val mappedRoles = registerRequest.roles?.mapNotNull {
            runCatching { Role.valueOf(it.uppercase()) }.getOrNull()
        }?.toMutableSet() ?: mutableSetOf(Role.STUDENT)

        val user = User(
            email = registerRequest.email,
            password = passwordEncoder.encode(registerRequest.password),
            firstName = registerRequest.firstName,
            lastName = registerRequest.lastName,
            roles = mappedRoles
        )

        userRepository.save(user)

        val userDetails = customUserDetailsService.loadUserByUsername(user.email)
        val token = jwtService.generateToken(userDetails)

        return AuthResponse(token)
    }

    fun login(request: LoginRequest): AuthResponse {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                request.email,
                request.password),
        )

        val userDetails = customUserDetailsService.loadUserByUsername(request.email)
        val token = jwtService.generateToken(userDetails)

        return AuthResponse(token)
    }

}