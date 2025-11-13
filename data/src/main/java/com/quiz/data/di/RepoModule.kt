package com.quiz.data.di

import com.quiz.data.repositories.QuizRepositoryImpl
import com.quiz.domain.repository.QuizRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepoModule {
    @Binds
    @Singleton
    abstract fun providesQuizRepo(impl: QuizRepositoryImpl): QuizRepository
}