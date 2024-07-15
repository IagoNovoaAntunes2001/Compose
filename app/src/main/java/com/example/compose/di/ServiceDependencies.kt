package com.example.compose.di

import com.example.compose.data.api.ApiService
import com.example.compose.data.api.RetrofitInstance

fun provideApiService(): ApiService = RetrofitInstance.retrofit()