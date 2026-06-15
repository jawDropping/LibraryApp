package com.day10exercise.libraryApp.security

import com.day10exercise.libraryApp.model.entity.Student
import com.day10exercise.libraryApp.repository.StudentRepository
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtFilter(
    private val jwtService: JwtService,
    private val studentRepository: StudentRepository
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {

        val authHeader = request.getHeader("Authorization")

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response)
            return
        }

        val token = authHeader.substring(7)
        val email = jwtService.extractEmail(token)

        if (SecurityContextHolder.getContext().authentication == null) {

            val student = studentRepository.findByEmail(email)

            if (student != null && jwtService.isValid(token, student)) {

                val auth = UsernamePasswordAuthenticationToken(
                    student.email,
                    null,
                    listOf(SimpleGrantedAuthority("ROLE_${student.role}"))
                )

                SecurityContextHolder.getContext().authentication = auth
            }
        }

        filterChain.doFilter(request, response)
    }
}