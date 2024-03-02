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
    val cardHeight: Float = 288f,
    val cardOverlap: Float = 124f,
    val crossPathHeight: Float = cardHeight + cardOverlap,
    val crossVisibleHeight: Float = cardHeight - cardOverlap,
    val cardSelectionAnimationDuration: Long = 1000,
    val cardCameraDistance: Float = 33f,
)

val movieList = listOf(
    MovieCard(1, "Title 1", "Description 1", image = R.drawable.clockwork_orange),
    MovieCard(1, "Title 1", "Description 1", image = R.drawable.bary),
    MovieCard(1, "Title 1", "Description 1", image = R.drawable.space_odyssy),
    MovieCard(1, "Title 1", "Description 1", image = R.drawable.metal_jacket),
    MovieCard(1, "Title 1", "Description 1", image = R.drawable.shining),
    MovieCard(1, "Title 1", "Description 1", image = R.drawable.paths_of_glory),
    MovieCard(1, "Title 1", "Description 1", image = R.drawable.i),
    MovieCard(1, "Title 1", "Description 1", image = R.drawable.spartacus),
    MovieCard(1, "Title 1", "Description 1", image = R.drawable.dr_strangelove),
    MovieCard(1, "Title 1", "Description 1", image = R.drawable.eyes_wide_shut),
    MovieCard(1, "Title 1", "Description 1", image = R.drawable.lolita),
)
