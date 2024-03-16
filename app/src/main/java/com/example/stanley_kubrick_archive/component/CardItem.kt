package com.example.stanley_kubrick_archive.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.stanley_kubrick_archive.R
import com.example.stanley_kubrick_archive.data.MovieCard
import com.example.stanley_kubrick_archive.utils.cardsPositionSwitchAfterSelection
import com.example.stanley_kubrick_archive.utils.transitionYAfterSelectionAnimation
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(
    DelicateCoroutinesApi::class, ExperimentalFoundationApi::class,
    ExperimentalAnimationApi::class
)
@Composable
fun MovieItem(
    movieCard: MovieCard,
    index: Int,
    listState: LazyListState,
    selectedCard: MutableState<Int?>,
    animationInProgress: MutableState<Boolean>,
    userScrollEnabled: MutableState<Boolean>,
    pagerState: PagerState
) {
    val compositeState = remember {
        derivedStateOf {
            Pair(listState.firstVisibleItemIndex, listState.firstVisibleItemScrollOffset)
        }
    }

    val (itemHeightPx, animationDistanceY, targetOffset) = cardsPositionSwitchAfterSelection(
        movieCard,
        index,
        compositeState,
        selectedCard
    )
    val cardOffsetPosition =
        if ((selectedCard.value ?: -1) == index) 0.dp else (if (index < (selectedCard.value
                ?: 0)
        ) -(targetOffset * animationDistanceY) else (targetOffset * animationDistanceY))
    val animatedOffset by animateDpAsState(
        targetValue = cardOffsetPosition,
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
        animationSpec =
        tween(
            durationMillis = if (selectedCard.value != null || animationInProgress.value)
                movieCard.cardSelectionAnimationDuration.toInt()
            else
                0
        ),
        label = "CardRotationOnSelection"
    )


    val targetTransitionValue =
        transitionYAfterSelectionAnimation(selectedCard, index, compositeState, movieCard)

    val dynamicTransitionY by animateFloatAsState(
        targetValue = targetTransitionValue,
        animationSpec =
        tween(
            durationMillis = if (selectedCard.value != null || animationInProgress.value)
                movieCard.cardSelectionAnimationDuration.toInt()
            else
                0
        ),
        label = "CardRotationOnSelection"
    )

    val cardPadding = animateDpAsState(
        targetValue = if (pagerState.currentPage > 0) 8.dp else 0.dp,
        label = "cardPadding",
        animationSpec = tween<Dp>(
            durationMillis = 3000,
            easing = LinearEasing
        )
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .height((movieCard.cardHeight).dp)
            .fillMaxWidth()
            .graphicsLayer {
                rotationX =
                    -dynamicRotation
                cameraDistance = movieCard.cardCameraDistance
                translationY = dynamicTransitionY
            }
            .clickable {
                GlobalScope.launch(Dispatchers.Main) {
                    if (selectedCard.value == null) {
                        selectedCard.value = index
                        userScrollEnabled.value = false
                    } else {
                        selectedCard.value = null
                        animationInProgress.value = true
                        delay(movieCard.cardSelectionAnimationDuration)
                        animationInProgress.value = false
                        userScrollEnabled.value = true
                    }
                }
            }
            .offset(y = animatedOffset),
    ) {
        Column(
            modifier = Modifier
                .padding(all = cardPadding.value)

                .animateContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (pagerState.currentPage > 0 && selectedCard.value == index) {
                val modifier =
                    Modifier
                        .height(((movieCard.cardHeight / 2) - (4 * 3)).dp)
                        .padding(4.dp)
                        .width(((movieCard.cardHeight / 3) - (4 * 4)).dp)
                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn() + scaleIn(initialScale = 0.8f),
                    exit = fadeOut() + scaleOut(targetScale = 0.8f)
                ) {
                    Column {
                        Row {
                            ActorCardItem(imageRes = movieCard.listActors[0].image, modifier)
                            ActorCardItem(imageRes = movieCard.listActors[1].image, modifier)
                            ActorCardItem(imageRes = movieCard.listActors[2].image, modifier)
                        }
                        Row {
                            ActorCardItem(imageRes = movieCard.listActors[3].image, modifier)
                            ActorCardItem(imageRes = movieCard.listActors[4].image, modifier)
                            ActorCardItem(imageRes = movieCard.listActors[5].image, modifier)
                        }
                    }
                }
            } else {
                Card(
                    modifier = Modifier

                ) {
                    CardContent(movieCard, selectedCard)
                }
            }
        }
    }
}
