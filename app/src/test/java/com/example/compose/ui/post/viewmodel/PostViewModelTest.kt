package com.example.compose.ui.post.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.compose.data.model.Post
import com.example.compose.data.uc.GetPostsUseCase
import com.example.compose.ui.util.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Rule
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class PostViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val useCase: GetPostsUseCase = mock()
    private lateinit var viewModel: PostViewModel

    @BeforeEach
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        viewModel = PostViewModel(useCase)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should update StateFlow to Success when posts are fetched successfully`() = runTest {
        val posts = listOf(Post(1, "Title", "Description"))
        whenever(useCase.invoke()).thenReturn(posts)

        viewModel.fetchPosts()

        val stateFlow = viewModel.uiState
        val states = mutableListOf<UiState<List<Post>>>()

        stateFlow.take(2).collect { state ->
            states.add(state)
        }

        assertTrue(states[0] is UiState.Loading)

        val finalState = states[1]
        assertTrue(finalState is UiState.Success)
        assertEquals(posts, (finalState as UiState.Success).data)
    }

    @Test
    fun `should update StateFlow to Error when fetching posts fails`() = runTest {
        val exception = RuntimeException("Failed to load posts: Error fetching posts")
        whenever(useCase.invoke()).thenThrow(exception)

        viewModel.fetchPosts()

        val stateFlow = viewModel.uiState
        val states = mutableListOf<UiState<List<Post>>>()

        stateFlow.take(2).collect { state ->
            states.add(state)
        }

        assertTrue(states[0] is UiState.Loading)

        val finalState = states[1]
        assertTrue(finalState is UiState.Error)
        assertEquals(exception.message, (finalState as UiState.Error).message)
    }
}