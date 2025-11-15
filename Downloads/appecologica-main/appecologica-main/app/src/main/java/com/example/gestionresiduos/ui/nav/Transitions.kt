package com.example.gestionresiduos.ui.nav

import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween

private const val DUR = 250 // duración base ms (ajústala a tu gusto)
private val EASING = FastOutSlowInEasing

// List -> Detail
fun slideInFromRight() = slideInHorizontally(
    animationSpec = tween(DUR, easing = EASING),
    initialOffsetX = { it }          // entra desde la derecha
) + fadeIn(tween(DUR, easing = EASING))

fun slideOutToLeft() = slideOutHorizontally(
    animationSpec = tween(DUR, easing = EASING),
    targetOffsetX = { -it }          // sale hacia la izquierda
) + fadeOut(tween(DUR, easing = EASING))

// Back (Detail -> List)
fun slideInFromLeft() = slideInHorizontally(
    animationSpec = tween(DUR, easing = EASING),
    initialOffsetX = { -it }         // entra desde la izquierda
) + fadeIn(tween(DUR, easing = EASING))

fun slideOutToRight() = slideOutHorizontally(
    animationSpec = tween(DUR, easing = EASING),
    targetOffsetX = { it }           // sale hacia la derecha
) + fadeOut(tween(DUR, easing = EASING))

// Para pantallas "modales" o simples
fun fadeScaleIn() = fadeIn(tween(DUR, easing = EASING)) + scaleIn(tween(DUR), initialScale = 0.96f)
fun fadeScaleOut() = fadeOut(tween(DUR, easing = EASING)) + scaleOut(tween(DUR), targetScale = 1.04f)