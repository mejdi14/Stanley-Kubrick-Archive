package com.example.stanley_kubrick_archive

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.stanley_kubrick_archive.component.BottomGradientLayer
import com.example.stanley_kubrick_archive.component.MovieItem
import com.example.stanley_kubrick_archive.component.ToolbarWithTextAndIcon
import com.example.stanley_kubrick_archive.data.MovieCard
import com.example.stanley_kubrick_archive.data.movieList
import com.example.stanley_kubrick_archive.ui.theme.StanleyKubrickArchiveTheme

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
                    ToolbarWithTextAndIcon(getString(R.string.toolbar_title), R.drawable.baseline_search_20)
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
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(top = 200.dp)){
            item{
                Text(text = "hello my friend")
            }
            item{
                Text(text = "hello my friend")
            }
            item{
                Text(text = "hello my friend")
            }
            item{
                Text(text = "hello my friend")
            }
            item{
                Text(text = "hello my friend")
            }
            item {
                Text(text = "hello my friend")
            }
        }
    }
}
