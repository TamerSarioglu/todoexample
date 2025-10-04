package com.tamersarioglu.todoexample.repository

import com.tamersarioglu.todoexample.model.Todo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TodoRepository: JpaRepository<Todo, Long> {
    fun findByUserName(username: String): List<Todo>
}