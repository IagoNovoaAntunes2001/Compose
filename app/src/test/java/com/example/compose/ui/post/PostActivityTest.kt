package com.example.compose.ui.post

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.compose.components.PostListScreen
import com.example.compose.data.model.Post
import com.example.compose.ui.post.viewmodel.PostViewModel
import com.example.compose.ui.util.UiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalMaterial3Api
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class PostActivityTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<PostActivity>()

    private val mockViewModel: PostViewModel = mock()
    private val uiStateFlow: StateFlow<UiState<List<Post>>> = MutableStateFlow(UiState.Loading)

    @Test
    fun `should display loading spinner when uiState is Loading`() = runTest {
        whenever(mockViewModel.uiState).thenReturn(uiStateFlow)

        composeTestRule.setContent {
            PostListScreen(viewModel = mockViewModel)
        }

        composeTestRule.onNodeWithText("Loading...").assertIsDisplayed()
    }

    @Test
    fun `should display posts when uiState is Success`() = runTest {
        val posts = listOf(Post(1, "Title", "Description"))
        val successState = UiState.Success(posts)
        whenever(mockViewModel.uiState).thenReturn(MutableStateFlow(successState))

        composeTestRule.setContent {
            PostListScreen(viewModel = mockViewModel)
        }

        composeTestRule.onNodeWithText("Title").assertIsDisplayed()
        composeTestRule.onNodeWithText("Description").assertIsDisplayed()
    }

    @Test
    fun `should display error message when uiState is Error`() = runTest {
        val exception = Exception("Error fetching posts")
        val errorState = UiState.Error(exception.message.toString())
        whenever(mockViewModel.uiState).thenReturn(MutableStateFlow(errorState))

        composeTestRule.setContent {
            PostListScreen(viewModel = mockViewModel)
        }

        composeTestRule.onNodeWithText("Error: Error fetching posts").assertIsDisplayed()
    }
}