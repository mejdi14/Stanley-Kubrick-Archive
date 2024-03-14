package com.example.stanley_kubrick_archive.component.pager

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PagerIndicator(pagerState: PagerState, pageCount: Int, modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        repeat(pageCount) { index ->
            Box(
                modifier = Modifier
                    .size(if (pagerState.currentPage == index) 10.dp else 8.dp)
                    .background(if (pagerState.currentPage == index) Color.Red else Color.Gray)
                    .padding(horizontal = 4.dp)
            )
        }
    }
}