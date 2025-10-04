package com.tamersarioglu.todoexample.service

import org.springframework.security.core.userdetails.UserDetails

interface UserDetailsService {
    fun loadUserByUsername(username: String): UserDetails
}