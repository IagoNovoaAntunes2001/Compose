package com.example.compose.domain.uc

import com.example.compose.data.repository.PostRepository
import com.example.compose.data.uc.GetPostsUseCase

class GetPostsUseCaseImpl(
    private val postRepository: PostRepository
) : GetPostsUseCase {
    override suspend fun invoke() = postRepository.getPosts()
}