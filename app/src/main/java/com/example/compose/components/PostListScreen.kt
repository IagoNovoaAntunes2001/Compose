package com.example.compose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.compose.data.model.Post
import com.example.compose.ui.post.viewmodel.PostViewModel
import com.example.compose.ui.util.UiState

@ExperimentalMaterial3Api
@Composable
fun PostListScreen(viewModel: PostViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Post List") })
        }
    ) { paddingValues ->
        when (uiState) {
            is UiState.Loading -> showLoadingState()
            is UiState.Success -> showSuccessState(
                paddingValues,
                uiState as UiState.Success<List<Post>>
            )

            is UiState.Error -> showErrorState(
                paddingValues,
                (uiState as UiState.Error).message
            )
        }
    }
}

@Composable
fun showErrorState(paddingValues: PaddingValues, errorMessage: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Error: $errorMessage",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun showLoadingState() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Loading...",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Gray
        )
    }
}

@Composable
fun showSuccessState(paddingValues: PaddingValues, uiState: UiState.Success<List<Post>>) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(paddingValues)
    ) {
        items((uiState).data) { post ->
            PostItem(post)
        }
    }
}