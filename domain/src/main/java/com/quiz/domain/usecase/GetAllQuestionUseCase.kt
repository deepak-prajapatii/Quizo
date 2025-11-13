package com.quiz.domain.usecase

import com.quiz.domain.base.Either
import com.quiz.domain.entities.Question
import com.quiz.domain.repository.QuizRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllQuestionUseCase @Inject constructor(
    private val repository: QuizRepository
) : UseCase<List<Question>, Unit> {
    override fun execute(params: Unit): Flow<Either<List<Question>>> {
        return repository.getAllQuestions()
    }
}