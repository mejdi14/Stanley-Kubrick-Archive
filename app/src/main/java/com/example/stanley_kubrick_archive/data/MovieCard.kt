package com.example.stanley_kubrick_archive.data

import androidx.compose.ui.graphics.Color
import com.example.stanley_kubrick_archive.R
import kotlin.random.Random

data class MovieCard(
    val id: Int,
    val title: String,
    val description: String,
    val color: Color = Color(
        Random.nextInt(256), Random.nextInt(256), Random.nextInt(256)
    ),
    val image: Int = R.drawable.clockwork,
    val cardHeight: Float = 270f,
    val cardOverlap: Float = 124f
)

val movieList = listOf(
    MovieCard(1, "Title 1", "Description 1", image = R.drawable.space_odyssey),
    MovieCard(1, "Title 1", "Description 1", image = R.drawable.clockwork_orange),
    MovieCard(1, "Title 1", "Description 1", image = R.drawable.lolita),
    MovieCard(1, "Title 1", "Description 1", image = R.drawable.glory),
    MovieCard(1, "Title 1", "Description 1", image = R.drawable.shining),
    MovieCard(1, "Title 1", "Description 1", image = R.drawable.barry_lyndon),
    MovieCard(1, "Title 1", "Description 1", image = R.drawable.dr),
    MovieCard(1, "Title 1", "Description 1", image = R.drawable.eyes),
    MovieCard(1, "Title 1", "Description 1", image = R.drawable.wide),
    MovieCard(1, "Title 1", "Description 1", image = R.drawable.strangelob),
    MovieCard(1, "Title 1", "Description 1", image = R.drawable.the_killing),
    MovieCard(1, "Title 1", "Description 1", image = R.drawable.killing),
    MovieCard(1, "Title 1", "Description 1", image = R.drawable.glory_paths),
    MovieCard(1, "Title 1", "Description 1", image = R.drawable.monkey),
    MovieCard(1, "Title 1", "Description 1", image = R.drawable.metal_jacket),
    MovieCard(1, "Title 1", "Description 1"),
    MovieCard(1, "Title 1", "Description 1"),
    MovieCard(1, "Title 1", "Description 1"),
)