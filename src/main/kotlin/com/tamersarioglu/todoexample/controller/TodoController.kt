package com.tamersarioglu.todoexample.controller

import com.tamersarioglu.todoexample.model.Todo
import com.tamersarioglu.todoexample.repository.TodoRepository
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/todos")
class TodoController(
    private val todoRepository: TodoRepository
) {

    @PostMapping
    fun addTodo(@RequestBody request: TodoRequest, auth: Authentication): ResponseEntity<Todo> {
        val todo = Todo(title = request.title, userName = auth.name, description = request.description)
        return ResponseEntity.ok(todoRepository.save(todo))
    }

    @GetMapping
    fun getTodos(auth: Authentication): List<Todo> {
        return todoRepository.findByUserName(auth.name)
    }
}

data class TodoRequest(val title: String, val description: String)