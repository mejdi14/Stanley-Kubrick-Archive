package com.example.stanley_kubrick_archive.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.stanley_kubrick_archive.R
import com.example.stanley_kubrick_archive.data.movieList
import com.example.stanley_kubrick_archive.utils.cardAnimationPosition

@Composable
fun CardDetailsScreen(index: Int, bounds: MutableState<Rect?>, onClose: () -> Unit) {
    val density = LocalDensity.current
    val cardSize = with(density) { bounds.value?.size }
    val cardPosition = with(density) {
        IntOffset(
            bounds.value?.left?.toInt() ?: 0,
            bounds.value?.top?.toInt() ?: 0
        )
    }
    val animationProgress = animateFloatAsState(
        targetValue = if (bounds.value != null) 1f else 0f,
        animationSpec = tween(3000), label = ""
    ).value

    val animatedX = cardAnimationPosition(cardPosition.x.toFloat(), 0f, animationProgress)
    val animatedY = cardAnimationPosition(cardPosition.y.toFloat(), 0f, animationProgress)

    Box(
        modifier = Modifier
            .graphicsLayer {
                this.translationX = animatedX
                this.translationY = animatedY
            }

            .height(270.dp)
            .fillMaxWidth()
            .graphicsLayer {
                rotationX = 0f
            }
            .clickable { onClose() },


        ) {
        Surface(

            modifier = Modifier
                .fillMaxSize().background(color = Color.Transparent)
        ) {
            Image(
                modifier = Modifier
                    .fillMaxSize(),
                painter = painterResource(id = movieList[index].image),
                contentDescription = "image",
                contentScale = ContentScale.Crop
            )
        }
    }
}