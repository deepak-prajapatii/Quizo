package com.quiz.data.remote.responsebody

data class QuestionDTO(
    val id: Long,
    val question: String,
    val options: List<String>,
    val correctOptionIndex: Long
)