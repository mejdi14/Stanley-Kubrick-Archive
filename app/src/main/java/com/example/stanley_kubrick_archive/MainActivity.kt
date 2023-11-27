package com.example.stanley_kubrick_archive

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.example.stanley_kubrick_archive.data.Item
import com.example.stanley_kubrick_archive.ui.theme.StanleyKubrickArchiveTheme

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
                    modifier = Modifier.fillMaxSize().background(color = Color.Black),
                    color = Color.Black
                ) {
                    ItemList(items = items)
                }
            }
        }
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
