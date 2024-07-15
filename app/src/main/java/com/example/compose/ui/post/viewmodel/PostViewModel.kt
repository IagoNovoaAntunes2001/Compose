package com.example.compose.ui.post.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compose.data.model.Post
import com.example.compose.data.uc.GetPostsUseCase
import com.example.compose.ui.util.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PostViewModel(private val getPostsUseCase: GetPostsUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Post>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Post>>> = _uiState

    internal fun fetchPosts() =
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val posts = getPostsUseCase()
                _uiState.value = UiState.Success(posts)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(
                    e.message ?: "Failed to load posts: Error fetching posts"
                )
            }
        }
}