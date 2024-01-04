package com.example.stanley_kubrick_archive

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.stanley_kubrick_archive.data.Item
import com.example.stanley_kubrick_archive.ui.theme.StanleyKubrickArchiveTheme
import kotlinx.coroutines.currentCoroutineContext

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StanleyKubrickArchiveTheme {
                val items = listOf(
                    Item(1, "Title 1", "Description 1"),
                    Item(1, "Title 1", "Description 1"),
                    Item(1, "Title 1", "Description 1"),
                    Item(1, "Title 1", "Description 1"),
                    Item(1, "Title 1", "Description 1"),
                    Item(1, "Title 1", "Description 1"),
                    Item(1, "Title 1", "Description 1"),
                    Item(1, "Title 1", "Description 1"),
                    Item(1, "Title 1", "Description 1"),
                    Item(1, "Title 1", "Description 1"),
                    Item(1, "Title 1", "Description 1"),
                    Item(1, "Title 1", "Description 1"),
                    Item(1, "Title 1", "Description 1"),
                    Item(1, "Title 1", "Description 1"),
                    Item(1, "Title 1", "Description 1"),
                    Item(1, "Title 1", "Description 1"),
                    Item(1, "Title 1", "Description 1"),
                )
                Box(
                    modifier = Modifier.background(color = Color.Black).fillMaxSize()

                ) {
                    HomeScreen(items)
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(90.dp) // Set the height of the toolbar
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Black.copy(alpha = 0.7f), // Start color of the gradient
                                        Color.Transparent // End color of the gradient
                                    )
                                )
                            )
                    )
                }
            }
        }
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun HomeScreen(items: List<Item>) {
        //Scaffold(topBar = { GradientToolbar("ARCHIVE") }) {
            SimpleItemList(items = items)
    }

    @Composable
    fun GradientToolbar(title: String) {
        val gradient = Brush.verticalGradient(
            colors = listOf(Color.Black, Color.Transparent),
            startY = 0.0f,
            endY = Float.POSITIVE_INFINITY
        )
        TopAppBar(
            title = { Text(text = title, color = Color.White) },
            modifier = Modifier.background(gradient),

            )
    }
}

fun dpToPx(dp: Float, context: Context): Float {
    return dp * context.resources.displayMetrics.density
}

@Composable
fun ItemCard(
    item: Item,
    scale: Float,
    offset: Dp,
    rotationDegrees: Float,
    index: Int,
    scrollOffset: Int,
    listState: LazyListState
) {
    Log.d(
        "TAG",
        "ItemCard: ${
            pxToDp(
                listState.firstVisibleItemScrollOffset.toFloat(),
                LocalContext.current
            )
        }"
    )
    val itemHeightPx = dpToPx(270f, LocalContext.current)
    val dynamicRotation = if (index > listState.firstVisibleItemIndex) {
        20f * (index - listState.firstVisibleItemIndex) - (listState.firstVisibleItemScrollOffset / itemHeightPx) * 20f
    } else {
        0f
    }
    Card(
        modifier = Modifier
            .height(270.dp)
            .fillMaxWidth()
            .graphicsLayer {
                rotationX =
                    -dynamicRotation
                cameraDistance = 33f
            }


    ) {
        Surface(
            color = item.color,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Image(
                modifier = Modifier
                    .fillMaxSize(),
                painter = painterResource(id = R.drawable.clockwork),
                contentDescription = "image",
                contentScale = ContentScale.FillWidth
            )
        }
    }
}


fun pxToDp(px: Float, context: Context): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        px,
        context.resources.displayMetrics
    )
}


@Composable
fun ItemList(items: List<Item>) {
    val listState = rememberLazyListState()
    val scrollOffset = remember { listState.firstVisibleItemScrollOffset }

    LazyColumn(
        state = listState,
        verticalArrangement = Arrangement.spacedBy((-190).dp),
    ) {
        itemsIndexed(items) { index, item ->
            val scale = 1f - (index * 0.02f).coerceAtMost(0.2f)
            val offset = (-100 * index).dp
            val rotationDegrees = calculateRotation(index, scrollOffset)
            ItemCard(item, scale = scale, offset, rotationDegrees, index, scrollOffset, listState)
        }
    }


}

@Composable
fun SimpleItemList(items: List<Item>) {
    val listState = rememberLazyListState()
    val scrollOffset = remember { listState.firstVisibleItemScrollOffset }

    LazyColumn(
        modifier = Modifier.padding(horizontal = 30.dp),
        state = listState,
        verticalArrangement = Arrangement.spacedBy((-100).dp),
    ) {
        itemsIndexed(items) { index, item ->
            Log.d("TAG", "SimpleItemList: ${listState.firstVisibleItemScrollOffset}")
            val scale = 1f - (index * 0.02f).coerceAtMost(0.2f)
            val offset = (-50 * index).dp
            val rotationDegrees = calculateRotation(index, scrollOffset)
            ItemCard(item, scale = scale, offset, rotationDegrees, index, scrollOffset, listState)
        }
    }


}

fun calculateRotation(index: Int, scrollOffset: Int): Float {
    // Your logic to calculate rotation based on index and scroll position
    // Example: increasing rotation with each item and adjusting based on scroll
    return (index * -30f) - scrollOffset * 0.1f
}

fun getScreenHeightInDp(context: Context): Int {
    val displayMetrics: DisplayMetrics = context.resources.displayMetrics
    val screenHeightInPixels = displayMetrics.heightPixels
    val densityDpi = displayMetrics.densityDpi
    return (screenHeightInPixels / (densityDpi / 160f)).toInt()
}