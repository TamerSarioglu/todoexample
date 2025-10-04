package com.tamersarioglu.todoexample.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "todos")
data class Todo(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(nullable = false)
    val title: String,
    @Column(nullable = false)
    val description: String,
    @Column(nullable = false)
    val userName: String,
    @Column(nullable = false)
    val completed: Boolean = false,
)