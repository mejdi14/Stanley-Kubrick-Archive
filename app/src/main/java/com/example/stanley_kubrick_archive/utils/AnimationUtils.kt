package com.example.stanley_kubrick_archive.utils

import android.content.Context
import android.util.TypedValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.stanley_kubrick_archive.data.MovieCard

fun cardAnimationPosition(start: Float, stop: Float, fraction: Float): Float {
    return (1 - fraction) * start + fraction * stop
}

@Composable
fun movieCardDimension(item: MovieCard, context: Context) =
    dpToPx((item.cardHeight - item.cardOverlap), context)

fun pxToDp(px: Float, context: Context): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        px,
        context.resources.displayMetrics
    )
}

@Composable
fun convertDpToPx(dp: Dp): Float {
    val density = LocalDensity.current
    return with(density) { dp.toPx() }
}

fun dpToPx(dp: Float, context: Context): Float {
    return dp * context.resources.displayMetrics.density
}

@Composable
 fun cardsPositionSwitchAfterSelection(
    movieCard: MovieCard,
    index: Int,
    compositeState: State<Pair<Int, Int>>,
    selectedCard: MutableState<Int?>
): Triple<Float, Int, Dp> {
    val itemHeightPx = movieCardDimension(movieCard, LocalContext.current)
    val animationDistanceY =
        ((index + 1) -
                (compositeState.value.first - (if (compositeState.value.second < movieCard.crossPathHeight) 1 else 0)))

    val targetOffset = if (selectedCard.value != null) (movieCard.cardHeight).dp else 0.dp
    return Triple(itemHeightPx, animationDistanceY, targetOffset)
}

 fun transitionYAfterSelectionAnimation(
    selectedCard: MutableState<Int?>,
    index: Int,
    compositeState: State<Pair<Int, Int>>,
    movieCard: MovieCard
) =
    if (selectedCard.value != null && selectedCard.value == index)
        (if (index == compositeState.value.first)
            (movieCard.crossVisibleHeight + compositeState.value.second + movieCard.cardCameraDistance)
        else
            -(((index - compositeState.value.first) * (movieCard.crossPathHeight)) - (movieCard.crossVisibleHeight + compositeState.value.second)) - ((index - compositeState.value.first) * 50))
    else 0f
