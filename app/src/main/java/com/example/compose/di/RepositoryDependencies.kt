package com.example.compose.di

import com.example.compose.data.api.ApiService
import com.example.compose.data.repository.PostRepository
import com.example.compose.data.uc.GetPostsUseCase
import com.example.compose.domain.repository.PostRepositoryImpl
import com.example.compose.domain.uc.GetPostsUseCaseImpl

fun providePostRepository(apiService: ApiService): PostRepository =
    PostRepositoryImpl(apiService)