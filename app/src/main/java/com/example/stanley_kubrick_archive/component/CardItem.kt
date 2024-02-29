package com.example.stanley_kubrick_archive.component

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.stanley_kubrick_archive.data.MovieCard
import com.example.stanley_kubrick_archive.utils.movieCardDimension

@Composable
fun CardItem(
    item: MovieCard,
    index: Int,
    listState: LazyListState,
    selectedCard: MutableState<Int?>,
    selectedItemBounds: MutableState<Rect?>
) {
    val compositeState = remember {
        derivedStateOf {
            Pair(listState.firstVisibleItemIndex, listState.firstVisibleItemScrollOffset)
        }
    }

    val itemHeightPx = movieCardDimension(item, LocalContext.current)
    val animationDistanceY =
        ((index + 1) - (compositeState.value.first - (if (compositeState.value.second < item.crossPathHeight) 1 else 0)))
    val targetOffset = if (selectedCard.value != null) (item.cardHeight).dp else 0.dp
    val res = if ((selectedCard.value ?: 0) == index) 0.dp else (if (index < (selectedCard.value
            ?: 0)
    ) -(targetOffset * animationDistanceY) else (targetOffset * animationDistanceY))
    val animatedOffset by animateDpAsState(
        targetValue = res,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioNoBouncy,
            stiffness = Spring.StiffnessLow
        ), label = "AnimatedOffset"
    )

    val dynamicRotation = if (index > compositeState.value.first) {
        20f * (index - compositeState.value.first) - (compositeState.value.second / itemHeightPx) * 20f
    } else {
        0f
    }

    val rotationTarget = remember { mutableStateOf(-dynamicRotation) }

    LaunchedEffect(key1 = selectedCard.value) {
        rotationTarget.value = if (selectedCard.value == index) {
            0f
        } else {
            -dynamicRotation
        }
    }

    val animatedAdditionalRotation by animateFloatAsState(
        targetValue = rotationTarget.value,
        animationSpec = tween(
            durationMillis = 800
        ), label = "CardFlatRotation"
    )
    Card(
        modifier = Modifier
            .height((item.cardHeight).dp)
            .fillMaxWidth()
            .graphicsLayer {
                rotationX = if((selectedCard.value == index))
                    animatedAdditionalRotation else -dynamicRotation
                cameraDistance = 33f
            }
            .clickable {
                if (selectedCard.value == null) selectedCard.value = index else selectedCard.value =
                    null
            }
            .offset(y = animatedOffset),
    ) {
        CardContent(item)
    }
}