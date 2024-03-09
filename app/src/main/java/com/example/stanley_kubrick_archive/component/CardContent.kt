package com.example.stanley_kubrick_archive.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.with
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.stanley_kubrick_archive.R
import com.example.stanley_kubrick_archive.data.MovieCard

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CardContent(item: MovieCard, selectedCard: MutableState<Int?>) {
    Surface(modifier = Modifier.fillMaxSize()) {
        AnimatedContent(
            targetState = selectedCard.value,
            transitionSpec = {
                slideIntoContainer(
                    animationSpec = tween(durationMillis = 3000, easing = EaseIn),
                    towards = AnimatedContentScope.SlideDirection.Down
                ).with(slideOutOfContainer(
                    animationSpec = tween(durationMillis = 3000, easing = EaseIn),
                    towards = AnimatedContentScope.SlideDirection.Down
                ))
            }, label = ""
        ) { targetState ->
            when (targetState) {
                null -> Image(
                    painter = painterResource(id = R.drawable.clockwork_orange),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds
                )
                else -> Image(
                    painter = painterResource(id = item.image),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds
                )
            }
        }
    }
}

