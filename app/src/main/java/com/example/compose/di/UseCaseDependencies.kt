package com.example.compose.di

import com.example.compose.data.repository.PostRepository
import com.example.compose.data.uc.GetPostsUseCase
import com.example.compose.domain.uc.GetPostsUseCaseImpl

fun provideGetPostUseCase(postRepository: PostRepository): GetPostsUseCase =
    GetPostsUseCaseImpl(postRepository)