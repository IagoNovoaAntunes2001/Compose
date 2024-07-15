package com.example.compose.ui.post

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.example.compose.components.PostListScreen
import com.example.compose.di.provideApiService
import com.example.compose.di.provideGetPostUseCase
import com.example.compose.di.providePostRepository
import com.example.compose.di.providePostViewModel
import com.example.compose.ui.post.viewmodel.PostViewModel
import com.example.compose.ui.theme.ComposeTheme

@ExperimentalMaterial3Api
class PostActivity : ComponentActivity() {

    private val repository = providePostRepository(provideApiService())
    private val getPostsUseCase = provideGetPostUseCase(repository)
    private val postViewModel: PostViewModel by lazy {
        providePostViewModel(this, getPostsUseCase)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    PostListScreen(postViewModel)
                }
            }
        }
    }
}