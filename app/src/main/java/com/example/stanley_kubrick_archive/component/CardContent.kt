package com.example.stanley_kubrick_archive.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.stanley_kubrick_archive.data.MovieCard


@Composable
fun CardContent(item: MovieCard) {
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

