package com.example.stanley_kubrick_archive.utils

import android.content.Context
import android.util.TypedValue
import androidx.compose.runtime.Composable
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

fun dpToPx(dp: Float, context: Context): Float {
    return dp * context.resources.displayMetrics.density
}
