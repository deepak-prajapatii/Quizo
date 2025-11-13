package com.quiz.domain.repository

import com.quiz.domain.base.Either
import com.quiz.domain.entities.Question
import kotlinx.coroutines.flow.Flow

interface QuizRepository {
    fun getAllQuestions(): Flow<Either<List<Question>>>
}