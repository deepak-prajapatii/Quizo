package com.quiz.data.repositories

import com.quiz.data.remote.responsebody.QuestionDTO
import com.quiz.data.remote.service.QuizService
import com.quiz.domain.base.Either
import com.quiz.domain.entities.Question
import com.quiz.domain.repository.QuizRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.Dispatcher
import javax.inject.Inject

class QuizRepositoryImpl @Inject constructor(
    private val quizService: QuizService
) : QuizRepository, BaseRepository() {
    override fun getAllQuestions(): Flow<Either<List<Question>>> {
        return flow {
            val response = safeApiCall {
                quizService.getAllQuestions()
            }
            if (response is Either.Success) {
                emit(Either.success(QuestionDTO.convertListTo(response.data)))
            } else if (response is Either.Failure) {
                emit(Either.failure(response.exception))
            }
        }.flowOn(Dispatchers.IO)
    }
}