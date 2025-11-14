package com.quiz.data.repositories

import com.quiz.domain.base.Either
import com.quiz.domain.base.GlobalException
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

abstract class BaseRepository {

    protected suspend fun <T> safeApiCall(
        apiCall: suspend () -> Response<T>
    ): Either<T> {
        return try {
            val response = apiCall()

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Either.success(body)
                } else {
                    Either.failure(GlobalException.Unknown("Empty response body"))
                }
            } else {
                Either.failure(GlobalException.Http(response.code(), response.message()))
            }
        } catch (e: IOException) {
            Either.failure(GlobalException.Network(e.message ?: "Network error"))
        } catch (e: Exception) {
            Either.failure(GlobalException.Unknown(e.message ?: "Unknown error"))
        }
    }
}
