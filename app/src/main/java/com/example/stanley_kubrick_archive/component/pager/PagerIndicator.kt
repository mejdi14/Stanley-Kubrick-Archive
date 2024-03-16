package com.example.stanley_kubrick_archive.component.pager

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PagerIndicator(pagerState: PagerState, pageCount: Int, modifier: Modifier = Modifier) {
    Row(modifier = modifier.height(30.dp)) {
        repeat(pageCount) { index ->
            val size = animateDpAsState(targetValue = if (pagerState.currentPage == index) 14.dp else 10.dp,
                label = "indicatorAnimation"
            ).value
            Box(
                modifier = Modifier
                    .size(size)
                    .padding(horizontal = 2.dp)
                    .background(
                        color = if (pagerState.currentPage == index) Color.White else Color.Gray.copy(alpha = 0.5f),
                        shape = RoundedCornerShape(percent = 100)
                    )
            )
        }
    }
}