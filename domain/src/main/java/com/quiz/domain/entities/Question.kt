package com.quiz.domain.entities

class Question(
    val id: Long,
    val question: String,
    val options: List<String>,
    val correctOptionIndex: Int
)