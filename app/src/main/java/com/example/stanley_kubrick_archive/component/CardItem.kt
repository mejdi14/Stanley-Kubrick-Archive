package com.example.stanley_kubrick_archive.component

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.stanley_kubrick_archive.data.MovieCard
import com.example.stanley_kubrick_archive.utils.movieCardDimension
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun CardItem(
    item: MovieCard,
    index: Int,
    listState: LazyListState,
    selectedCard: MutableState<Int?>,
    animationInProgress: MutableState<Boolean>
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


    val initialRotationValue = if (index > compositeState.value.first) {
        20f * (index - compositeState.value.first) - (compositeState.value.second / itemHeightPx) * 20f
    } else {
        0f
    }

    val targetRotationValue =
        if (selectedCard.value != null && selectedCard.value == index) 0f else initialRotationValue

    val dynamicRotation by animateFloatAsState(
        targetValue = targetRotationValue,
        animationSpec = tween(durationMillis = if (selectedCard.value != null || animationInProgress.value) 1000 else 0)
    )


    Card(
        modifier = Modifier
            .height((item.cardHeight).dp)
            .fillMaxWidth()
            .graphicsLayer {
                rotationX =
                    -dynamicRotation
                cameraDistance = 33f
            }
            .clickable {
                if (selectedCard.value == null) selectedCard.value = index else {
                    GlobalScope.launch(Dispatchers.Main) { // Use GlobalScope for simplicity in this example
                        selectedCard.value = null
                        animationInProgress.value = true
                        delay(1000)
                        animationInProgress.value = false
                    }
                }
            }
            .offset(y = animatedOffset),
    ) {
        CardContent(item)
    }
}