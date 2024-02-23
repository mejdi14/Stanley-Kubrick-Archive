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
    val cardHeight: Float = 285f,
    val cardOverlap: Float = 124f
)

val movieList = listOf(
    MovieCard(1, "Title 1", "Description 1", image = R.drawable.h),
    MovieCard(1, "Title 1", "Description 1", image = R.drawable.a),
    MovieCard(1, "Title 1", "Description 1", image = R.drawable.f),
    MovieCard(1, "Title 1", "Description 1", image = R.drawable.metal_jacket),
    MovieCard(1, "Title 1", "Description 1", image = R.drawable.i),
    MovieCard(1, "Title 1", "Description 1", image = R.drawable.spar),
    MovieCard(1, "Title 1", "Description 1", image = R.drawable.lol),
    MovieCard(1, "Title 1", "Description 1", image = R.drawable.shining),
    MovieCard(1, "Title 1", "Description 1", image = R.drawable.dr),
    MovieCard(1, "Title 1", "Description 1", image = R.drawable.eys),
    MovieCard(1, "Title 1", "Description 1", image = R.drawable.glory_paths),
)