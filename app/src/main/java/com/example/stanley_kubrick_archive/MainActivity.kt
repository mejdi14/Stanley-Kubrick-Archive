package com.example.stanley_kubrick_archive

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.updateTransition
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.expandIn
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.PointerInputScope
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import com.example.stanley_kubrick_archive.component.BottomGradientLayer
import com.example.stanley_kubrick_archive.component.MovieItem
import com.example.stanley_kubrick_archive.component.ToolbarWithTextAndIcon
import com.example.stanley_kubrick_archive.data.MovieCard
import com.example.stanley_kubrick_archive.data.movieList
import com.example.stanley_kubrick_archive.ui.theme.StanleyKubrickArchiveTheme
import kotlinx.coroutines.launch
import kotlin.math.abs

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StanleyKubrickArchiveTheme {
                val items = movieList
                Box(
                    modifier = Modifier
                        .background(color = Color.Black)
                        .fillMaxSize()

                ) {
                    HomeScreen(items)
                    ToolbarWithTextAndIcon(
                        getString(R.string.toolbar_title),
                        R.drawable.baseline_search_20
                    )
                    BottomGradientLayer(Modifier.align(Alignment.BottomStart))
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
private fun HomeScreen(items: List<MovieCard>) {
    MoviesCardsList(items = items)
}


@OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
@Composable
fun MoviesCardsList(items: List<MovieCard>) {
    val listState = rememberLazyListState()
    val selectedCard = remember { mutableStateOf<Int?>(null) }
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
                    userScrollEnabled
                )
            }
        }
        //DetailsMovie(selectedCard, listState)
        //ExpandingCard()
        val pagerState = rememberPagerState()
        AnimatedVisibility(visible = selectedCard.value != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 300.dp)
            ) {
                HorizontalPager(
                    pageCount = 3,
                    state = pagerState,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {

                        page ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0xFF, 0x80 * (page + 1), 0x80)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "Page $page", color = Color.White)
                    }
                }
                PagerIndicator(
                    pagerState = pagerState,
                    pageCount = 3,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

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

@Composable
fun ExpandingCard() {
    var isExpanded by remember { mutableStateOf(false) }
    val transition = updateTransition(targetState = isExpanded, label = "cardTransition", )
    val cardSize = animateDpAsState(targetValue = if (isExpanded) 120.dp else 240.dp, label = "cardSize", animationSpec = tween<Dp>(
        durationMillis = 3000, // 3 seconds for a slower animation
        easing = LinearEasing // This is optional; adjust the easing function as needed
    ) )
    val cardPadding = animateDpAsState(targetValue = if (isExpanded) 8.dp else 0.dp, label = "cardPadding", animationSpec = tween<Dp>(
        durationMillis = 3000, // 3 seconds for a slower animation
        easing = LinearEasing // This is optional; adjust the easing function as needed
    ))

    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier
            .clickable { isExpanded = !isExpanded }
            .padding(all = cardPadding.value)

            .animateContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally) {

            if (isExpanded) {
                Row {
                    SmallCard(imageRes = R.drawable.clockwork_orange)
                SmallCard(imageRes = R.drawable.bary)
                }
                Row {
                    SmallCard(imageRes = R.drawable.shining)
                    SmallCard(imageRes = R.drawable.i)
                }
            } else {
                Card(modifier = Modifier.size(240.dp)) {
                    Image(
                        painter = painterResource(R.drawable.eyes_wide_shut),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }
}

@Composable
fun SmallCard(imageRes: Int) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .size(100.dp),

    ) {
        AnimatedVisibility(
            visible = true,
            enter = expandIn(),
            exit = shrinkOut()
        ) {
            Image(
                painter = painterResource(imageRes),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
    }
}

suspend fun PointerInputScope.detectVerticalSwipeGestures(
    onSwipeUp: (dragAmount: Float) -> Unit,
    onSwipeDown: (dragAmount: Float) -> Unit,
) {
    detectDragGestures { change, dragAmount ->
        change.consumeAllChanges()
        if (abs(dragAmount.y) > abs(dragAmount.x)) { // Detect vertical movement
            if (dragAmount.y < 0) {
                onSwipeUp(dragAmount.y)
            } else {
                onSwipeDown(dragAmount.y)
            }
        }
    }
}
