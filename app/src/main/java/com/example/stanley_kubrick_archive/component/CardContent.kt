package com.example.stanley_kubrick_archive.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.with
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.stanley_kubrick_archive.R
import com.example.stanley_kubrick_archive.data.MovieCard
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CardContent(item: MovieCard, isConditionTrue: Boolean) {
    // Image resources to toggle between based on the condition
    val currentImageResId by remember { derivedStateOf { if (isConditionTrue) R.drawable.bary else item.image } }

    Surface(modifier = Modifier.fillMaxSize()) {
        Crossfade(targetState = currentImageResId, animationSpec = androidx.compose.animation.core.tween(durationMillis = 1000)) { targetImage ->
            Image(
                painter = painterResource(id = targetImage),
                contentDescription = "image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )
        }
    }
}
// Function to animate the image change
private fun changeImage(
    newImageResId: Int,
    animatedProgress: Animatable<Float, *>,
    currentImage: MutableState<Int>,
    scope: CoroutineScope
) {
    scope.launch {
        // Animate from 0 to 1 (or screen width) and then change the image
        animatedProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 1000)
        )
        // Change the image and reset the animation
        currentImage.value = newImageResId
        animatedProgress.snapTo(0f) // Reset for the next transition if needed
    }
}
