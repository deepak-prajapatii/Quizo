package com.quiz.domain.usecase

import com.quiz.domain.base.Either
import kotlinx.coroutines.flow.Flow

interface UseCase<out ReturnType, in Params> {
    fun execute(params: Params): Flow<Either<ReturnType>>
}