package com.example.gestionresiduos.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable


@Composable
fun <T> StateContainer(
    state: UiState<T>,
    loading: @Composable () -> Unit = { CircularProgressIndicator() },
    error: @Composable (String) -> Unit = { msg ->
        Text("Ups: $msg", color = MaterialTheme.colorScheme.error)
    },
    success: @Composable (T) -> Unit
) {
    AnimatedContent(
        targetState = state,
        transitionSpec = { fadeIn() togetherWith fadeOut() },
        label = "UiStateAnimatedContent"
    ) { s ->
        when (s) {
            is UiState.Loading -> loading()
            is UiState.Error   -> error(s.message)
            is UiState.Success -> success(s.data)
        }
    }
}