package com.example.compose.data.repository

import com.example.compose.data.model.Post

interface PostRepository {
    suspend fun getPosts(): List<Post>
}