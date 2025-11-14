package com.quiz.data.remote.responsebody

import com.quiz.domain.entities.Question
import kotlin.String

data class QuestionDTO(
    val id: Long,
    val question: String,
    val options: List<String>,
    val correctOptionIndex: Int
) {
    companion object {
        fun convertTo(questionDTO: QuestionDTO): Question {
            return Question(
                id = questionDTO.id,
                question = questionDTO.question,
                options = questionDTO.options,
                correctOptionIndex = questionDTO.correctOptionIndex
            )
        }

        fun convertListTo(questions: List<QuestionDTO>): List<Question> {
            return questions.map { convertTo(it) }
        }
    }
}