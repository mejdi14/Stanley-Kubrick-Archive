package com.example.stanley_kubrick_archive

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.stanley_kubrick_archive.data.Item
import com.example.stanley_kubrick_archive.ui.theme.StanleyKubrickArchiveTheme

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StanleyKubrickArchiveTheme {
                val items = listOf(
                    Item(1, "Title 1", "Description 1"),
                    Item(2, "Title 2", "Description 2"),
                    Item(2, "Title 2", "Description 2"),
                    Item(2, "Title 2", "Description 2"),
                    Item(2, "Title 2", "Description 2"),
                    Item(2, "Title 2", "Description 2"),
                    Item(2, "Title 2", "Description 2"),
                    Item(2, "Title 2", "Description 2"),
                    Item(2, "Title 2", "Description 2"),
                )
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.Black),
                    color = Color.Black
                ) {
                    HomeScreen(items)
                }
            }
        }
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun HomeScreen(items: List<Item>) {
        Scaffold(topBar = { GradientToolbar("hello") }) {
            ItemList(items = items)
        }
    }

    @Composable
    fun GradientToolbar(title: String) {
        val gradient = Brush.verticalGradient(
            colors = listOf(Color.Blue, Color.Green),
            startY = 0.0f,
            endY = Float.POSITIVE_INFINITY
        )
        TopAppBar(
            title = { Text(text = title) },
            modifier = Modifier.background(gradient) // Apply the gradient here
        )
    }

    @Composable
    private fun SimpleList() {
        LazyColumn(content = {

        })
    }
}

@Composable
fun ItemCard(item: Item, scale: Float) {
    Card(
        modifier = Modifier
            .height(230.dp)

    ) {
        Surface(
            color = item.color,
            modifier = Modifier
                .fillMaxSize()
        ){
            Box {

            }
        }
    }
}

@Composable
fun ItemList(items: List<Item>) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy((-50).dp),
    ) {
        itemsIndexed(items) { index, item ->
            val scale = 1f - (index * 0.02f).coerceAtMost(0.2f)
            ItemCard(item, scale = scale)
        }
    }
}
