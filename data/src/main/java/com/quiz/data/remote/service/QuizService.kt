package com.quiz.data.remote.service

import com.quiz.data.remote.responsebody.QuestionDTO
import retrofit2.Response
import retrofit2.http.GET

interface QuizService {

    @GET("dr-samrat/53846277a8fcb034e482906ccc0d12b2/raw")
    fun getAllQuestions(): Response<List<QuestionDTO>>
}