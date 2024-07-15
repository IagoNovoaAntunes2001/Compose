package com.example.compose.data.uc

import com.example.compose.data.model.Post

interface GetPostsUseCase {
    suspend operator fun invoke(): List<Post>
}