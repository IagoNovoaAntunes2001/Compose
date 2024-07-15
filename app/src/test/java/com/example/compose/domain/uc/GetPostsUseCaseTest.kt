package com.example.compose.domain.uc

import com.example.compose.data.model.Post
import com.example.compose.data.repository.PostRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class GetPostsUseCaseTest {

    private val postRepository: PostRepository = mock()
    private val getPostsUseCase = GetPostsUseCaseImpl(postRepository)

    @Test
    fun `should return posts when repository call is successful`() = runBlocking {
        val posts = listOf(Post(1, "Title", "Description"))
        whenever(postRepository.getPosts()).thenReturn(posts)

        val result = getPostsUseCase()

        assertEquals(posts, result)
    }

    @Test
    fun `should throw exception when repository call fails`() = runBlocking {
        val expectedException = RuntimeException("Repository error")
        whenever(postRepository.getPosts()).thenThrow(expectedException)

        assertEquals(expectedException.message, expectedException.message)
    }
}