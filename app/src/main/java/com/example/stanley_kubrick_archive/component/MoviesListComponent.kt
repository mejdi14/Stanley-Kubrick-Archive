package com.example.stanley_kubrick_archive.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.stanley_kubrick_archive.component.pager.MovieDescriptionPager
import com.example.stanley_kubrick_archive.component.pager.PagerIndicator
import com.example.stanley_kubrick_archive.data.MovieCard

@OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
@Composable
fun MoviesCardsList(items: List<MovieCard>, selectedCard: MutableState<Int?>) {
    val listState = rememberLazyListState()
    val pagerState = rememberPagerState()
    val animationInProgress = remember { mutableStateOf<Boolean>(false) }
    val userScrollEnabled = remember { mutableStateOf<Boolean>(true) }
    Box {
        LazyColumn(
            modifier = Modifier.padding(horizontal = 30.dp),
            state = listState,
            verticalArrangement = Arrangement.spacedBy((-124).dp),
            userScrollEnabled = userScrollEnabled.value,
        ) {
            itemsIndexed(items) { index, item ->
                MovieItem(
                    item,
                    index,
                    listState,
                    selectedCard,
                    animationInProgress,
                    userScrollEnabled,
                    pagerState
                )
            }
        }
        val density = LocalDensity.current
        AnimatedVisibility(
            visible = selectedCard.value != null,
            enter = fadeIn(
                animationSpec = tween(
                    durationMillis = 500,
                    delayMillis = 300
                )
            ),
            exit = slideOut(
                targetOffset = { fullSize ->
                    IntOffset(0, with(density) { fullSize.height.toDp().roundToPx() })
                },
                animationSpec = tween(durationMillis = 500)
            ) + fadeOut()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 400.dp)
            ) {
                MovieDescriptionPager(pagerState, Modifier.Companion.weight(1f), selectedCard.value)
                PagerIndicator(
                    pagerState = pagerState,
                    pageCount = 3,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}
