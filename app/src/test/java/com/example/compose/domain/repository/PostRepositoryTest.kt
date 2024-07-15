package com.example.compose.domain.repository

import com.example.compose.data.api.ApiService
import com.example.compose.data.model.Post
import com.example.compose.data.repository.PostRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class PostRepositoryTest {

    private val apiService: ApiService = mock()
    private val postRepository: PostRepository = PostRepositoryImpl(apiService)

    @Test
    fun `should return posts when API call is successful`() = runBlocking {
        val posts = listOf(Post(1, "Title", "Description"))
        whenever(apiService.getPosts()).thenReturn(posts)

        val result = postRepository.getPosts()

        assertEquals(posts, result)
    }

    @Test(expected = RuntimeException::class)
    fun `should throw exception when API call fails`(): Unit = runBlocking {
        whenever(apiService.getPosts()).thenThrow(RuntimeException("API error"))

        postRepository.getPosts()
    }
}