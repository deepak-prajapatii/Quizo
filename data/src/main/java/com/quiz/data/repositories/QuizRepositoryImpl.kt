package com.quiz.data.repositories

import com.quiz.domain.base.Either
import com.quiz.domain.entities.Question
import com.quiz.domain.repository.QuizRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class QuizRepositoryImpl @Inject constructor(): QuizRepository {
    override fun getAllQuestions(): Flow<Either<List<Question>>> {
        TODO("Not yet implemented")
    }
}