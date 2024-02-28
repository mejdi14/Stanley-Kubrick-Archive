package com.example.stanley_kubrick_archive

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stanley_kubrick_archive.component.CardDetailsScreen
import com.example.stanley_kubrick_archive.data.MovieCard
import com.example.stanley_kubrick_archive.data.movieList
import com.example.stanley_kubrick_archive.ui.theme.StanleyKubrickArchiveTheme
import com.example.stanley_kubrick_archive.utils.movieCardDimension

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
                    ToolbarWithTextAndIcon("Archive", R.drawable.baseline_search_20)
                    Row(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .fillMaxWidth()
                            .height(90.dp)
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        Color.Black.copy(alpha = 1f)
                                    ),
                                    startY = 0f,
                                    endY = Float.POSITIVE_INFINITY
                                )
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {}
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
private fun HomeScreen(items: List<MovieCard>) {
    SimpleItemList(items = items)
}

@Composable
fun ToolbarWithTextAndIcon(title: String, iconId: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Black.copy(alpha = 0.7f),
                        Color.Transparent
                    )
                )
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            text = title,
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 16.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = "Toolbar Icon",
            modifier = Modifier
                .size(44.dp)
                .padding(end = 16.dp),
            tint = Color.White
        )
    }
}

fun dpToPx(dp: Float, context: Context): Float {
    return dp * context.resources.displayMetrics.density
}

@Composable
fun ItemCard(
    item: MovieCard,
    index: Int,
    listState: LazyListState,
    selectedCard: MutableState<Int?>,
    selectedItemBounds: MutableState<Rect?>
) {
    val itemHeightPx = movieCardDimension(item, LocalContext.current)
    val targetOffset = if (selectedCard.value != null) 200.dp else 0.dp
    val res = if((selectedCard.value ?: 0) == index) 0.dp else (if(index < (selectedCard.value ?: 0)  ) -targetOffset else targetOffset)
    val animatedOffset by animateDpAsState(
        targetValue = res,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ), label = "AnimatedOffset"
    )

    val dynamicRotation = if (index > listState.firstVisibleItemIndex) {
        20f * (index - listState.firstVisibleItemIndex) - (listState.firstVisibleItemScrollOffset / itemHeightPx) * 20f
    } else {
        0f
    }
    Card(
        modifier = Modifier
            .height((item.cardHeight).dp)
            .fillMaxWidth()
            .graphicsLayer {
                rotationX =
                    -dynamicRotation
                cameraDistance = 33f
            }
            .clickable {
                if (selectedCard.value == null) selectedCard.value = index else selectedCard.value =
                    null
            }
            .offset(y = animatedOffset)
            .onGloballyPositioned { coordinates ->
                if (selectedCard.value == index) {
                    selectedItemBounds.value = coordinates.boundsInRoot()
                }
            },
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Image(
                modifier = Modifier
                    .fillMaxSize(),
                painter = painterResource(id = item.image),
                contentDescription = "image",
                contentScale = ContentScale.FillBounds
            )
        }
    }
}

@Composable
fun SimpleItemList(items: List<MovieCard>) {
    val listState = rememberLazyListState()
    val selectedCard = remember { mutableStateOf<Int?>(null) }
    val selectedItemBounds = remember { mutableStateOf<Rect?>(null) }
    LazyColumn(
        modifier = Modifier.padding(horizontal = 30.dp),
        state = listState,
        verticalArrangement = Arrangement.spacedBy((-124).dp),
    ) {
        itemsIndexed(items) { index, item ->
            ItemCard(item, index, listState, selectedCard, selectedItemBounds)
        }
    }


}
