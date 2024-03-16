package com.example.stanley_kubrick_archive.component.pager

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.stanley_kubrick_archive.data.movieList

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun MovieDescriptionPager(pagerState: PagerState, modifier: Modifier, selectedCardValue: Int?) {
    HorizontalPager(
        pageCount = 3,
        state = pagerState,
        modifier = modifier
            .fillMaxWidth()
    ) {

            page ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp
                ),
            contentAlignment = Alignment.Center
        ) {
            DescriptionTextComposable(selectedCardValue, pagerState)
        }
    }
}
