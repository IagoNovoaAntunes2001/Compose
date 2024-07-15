package com.example.compose.domain.repository

import com.example.compose.data.api.ApiService
import com.example.compose.data.model.Post
import com.example.compose.data.repository.PostRepository

class PostRepositoryImpl(private val apiService: ApiService) : PostRepository {
    override suspend fun getPosts(): List<Post> = apiService.getPosts()
}