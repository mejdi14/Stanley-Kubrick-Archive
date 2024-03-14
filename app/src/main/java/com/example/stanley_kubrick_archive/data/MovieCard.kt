package com.example.stanley_kubrick_archive.data

import androidx.compose.ui.graphics.Color
import com.example.stanley_kubrick_archive.R
import java.util.UUID
import kotlin.random.Random

data class MovieCard(
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
    val id: String = UUID.randomUUID().toString(),
)

val movieList = listOf(
    MovieCard("Title 1", "Description 1", image = R.drawable.clockwork_orange),
    MovieCard("Title 1", "Description 1", image = R.drawable.bary),
    MovieCard("Title 1", "Description 1", image = R.drawable.space_odyssy),
    MovieCard("Title 1", "Description 1", image = R.drawable.metal_jacket),
    MovieCard("Title 1", "Description 1", image = R.drawable.shining),
    MovieCard( "Title 1", "Description 1", image = R.drawable.paths_of_glory),
    MovieCard("Title 1", "Description 1", image = R.drawable.i),
    MovieCard("Title 1", "Description 1", image = R.drawable.spartacus),
    MovieCard( "Title 1", "Description 1", image = R.drawable.dr_strangelove),
    MovieCard( "Title 1", "Description 1", image = R.drawable.eyes_wide_shut),
    MovieCard( "Title 1", "Description 1", image = R.drawable.lolita),
)
