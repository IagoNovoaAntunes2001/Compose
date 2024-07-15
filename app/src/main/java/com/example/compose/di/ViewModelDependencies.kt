package com.example.compose.di

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.example.compose.data.uc.GetPostsUseCase
import com.example.compose.factory.PostViewModelFactory
import com.example.compose.ui.post.viewmodel.PostViewModel

fun providePostViewModelFactory(postsUseCase: GetPostsUseCase) =
    PostViewModelFactory(postsUseCase)

fun providePostViewModel(
    viewModelStoreOwner: ViewModelStoreOwner,
    postsUseCase: GetPostsUseCase
): PostViewModel =
    ViewModelProvider(
        viewModelStoreOwner,
        providePostViewModelFactory(postsUseCase)
    )[PostViewModel::class.java]