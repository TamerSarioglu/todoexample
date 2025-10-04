package com.tamersarioglu.todoexample.controller

import com.tamersarioglu.todoexample.config.JwtUtil
import com.tamersarioglu.todoexample.model.User
import com.tamersarioglu.todoexample.repository.UserRepository
import com.tamersarioglu.todoexample.service.UserDetailsServiceImpl
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val authenticationManager: AuthenticationManager,
    private val jwtUtil: JwtUtil,
    private val userDetailsServiceImpl: UserDetailsServiceImpl
) {
    @PostMapping("/register")
    fun register(@RequestBody user: User): ResponseEntity<String> {
        if (userRepository.findByUsername(user.username) != null) {
            return ResponseEntity.badRequest().body("Username is already taken")
        }
        val encodedPassword = passwordEncoder.encode(user.password)
        userRepository.save(user.copy(password = encodedPassword))
        return ResponseEntity.ok("User registered successfully")
    }

    @PostMapping("/login")
    fun login(@RequestBody authRequest: AuthRequest): ResponseEntity<Map<String, String>> {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(authRequest.username, authRequest.password)
        )
        val userDetails = userDetailsServiceImpl.loadUserByUsername(authRequest.username)
        val jwt = jwtUtil.generateToken(userDetails)
        return ResponseEntity.ok(mapOf("token" to jwt))
    }
}

data class AuthRequest(val username: String, val password: String)