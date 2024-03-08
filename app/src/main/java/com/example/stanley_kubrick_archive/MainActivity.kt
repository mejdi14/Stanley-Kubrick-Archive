package com.example.stanley_kubrick_archive

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.platform.LocalDensity
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


@OptIn(ExperimentalComposeUiApi::class)
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
            userScrollEnabled = userScrollEnabled.value
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
        AnimatedVisibility(visible = selectedCard.value != null) {
            val stateList = rememberLazyListState()
            val subListScrollEnabled = remember { mutableStateOf<Boolean>(false) }
            val coroutineScope = rememberCoroutineScope()
            var initialPadding by remember {
                mutableStateOf(
                    MovieCard(
                        1,
                        "",
                        ""
                    ).crossPathHeight + 90f
                )
            }
            val list = mutableListOf<String>()
            for (i in 1..100) {
                list.add("hello")
            }
            Box(modifier = Modifier
                .pointerInput(Unit) {
                    detectVerticalSwipeGestures(
                        onSwipeUp = {
                            if ((initialPadding + it) >= 0)
                                initialPadding += it
                            else
                                subListScrollEnabled.value = true
                        },
                        onSwipeDown = {
                            if (listState.firstVisibleItemIndex == 0 && listState.firstVisibleItemScrollOffset == 0)
                                initialPadding += it
                        },
                    )
                }
                .padding(top = (initialPadding).dp)
            ) {
                LazyColumn(modifier = Modifier
                    .pointerInput(Unit) {
                        detectVerticalDragGestures { change, dragAmount ->
                            if (((initialPadding + dragAmount) >= 0) && ((initialPadding + dragAmount) < 500)) {
                                subListScrollEnabled.value = false
                                initialPadding += dragAmount
                            } else
                                subListScrollEnabled.value = true
                            coroutineScope.launch {
                                // Your action here, e.g., refreshing content
                                Log.d("TAG", "subListScrollEnabled: ")
                            }
                        }
                        detectVerticalSwipeGestures(
                            onSwipeDown = {
                                Log.d("TAG", "onSwipeDown: ")
                            },
                            onSwipeUp = {
                                Log.d("TAG", "onSwipeUp: ")
                            }
                        )

                    }
                    .pointerInteropFilter { motionEvent ->
                        if (listState.firstVisibleItemIndex == 0) {
                            // The logic inside here will depend on how you're detecting the swipe down gesture.
                            // For simplicity, let's say any downward motion triggers the action:
                            // You can refine this by tracking motion events more precisely.
                            coroutineScope.launch {
                                // Your action here, e.g., refreshing content
                            }
                        }
                        false // Return false to indicate the event was not consumed, allowing for normal scrolling behavior
                    },
                    userScrollEnabled = subListScrollEnabled.value,
                    state = stateList
                ) {
                    items(list) {
                        Text("Hello this is the line $it", color = Color.White)
                    }
                }
            }
        }
    }
}

suspend fun Modifier.pointerInput(listState: LazyListState, detectSwipeDownGesture: suspend () -> Unit) = this.then(
    Modifier.pointerInput(listState) {
        awaitPointerEventScope {
            while (true) {
                val event = awaitPointerEvent()
                val dragAmount = event.changes.first().positionChange().y
                if (dragAmount > 0) { // Detecting swipe down
                }
            }
        }
    }
)

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


@Composable
fun AdjustablePaddingLazyColumn(list: List<String>) {
    val stateList = rememberLazyListState()
    val density = LocalDensity.current

    // Mutable state to track and update padding dynamically
    var topPadding by remember { mutableStateOf(200.dp) }
    val minTopPadding = 0.dp
    val maxTopPadding = 200.dp

    // Convert dp values to pixels for comparison and calculation
    val maxTopPaddingPx = with(density) { maxTopPadding.toPx() }
    val minTopPaddingPx = with(density) { minTopPadding.toPx() }

    // Custom NestedScrollConnection to intercept scroll events
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y

                // Use the captured density to convert Dp to Px and vice versa
                val newPaddingPx = with(density) { topPadding.toPx() } - delta
                topPadding = with(density) {
                    newPaddingPx.coerceIn(with(density) { minTopPadding.toPx() },
                        with(density) { maxTopPadding.toPx() }).toDp()
                }

                // Adjust logic as needed to determine when to absorb scroll
                return if (topPadding > minTopPadding) Offset.Zero else available
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(nestedScrollConnection)
            .background(Color.Black) // Just to make the text visible assuming a dark theme
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = topPadding),
            state = stateList
        ) {
            items(list) {
                Text("Hello this is the line $it", color = Color.White)
            }
        }
    }
}

@Composable
fun ShrinkingPaddingLazyColumn() {
    val listState = rememberLazyListState()
    val maxTopPadding = 200.dp
    val minTopPadding = 0.dp

    // Calculate the dynamic top padding based on scroll position
    val topPadding by remember {
        derivedStateOf {
            // Calculate the ratio of scroll position to the total padding amount
            val scrollOffset = listState.firstVisibleItemScrollOffset.toFloat()
            val firstVisibleItemIndex = listState.firstVisibleItemIndex

            if (firstVisibleItemIndex > 0) {
                // If we've scrolled past the first item, no more padding is needed
                minTopPadding
            } else {
                // Calculate dynamic padding
                max(minTopPadding, maxTopPadding - (scrollOffset / 1000).dp)
            }
        }
    }

    BoxWithConstraints {
        LazyColumn(
            state = listState,
            contentPadding = PaddingValues(top = topPadding),
            modifier = Modifier.padding(top = topPadding)
        ) {
            items(100) { index ->
                // Your item content
                Text(text = "Item $index")
            }
        }
    }
}
