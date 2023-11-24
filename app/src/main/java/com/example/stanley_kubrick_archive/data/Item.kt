package com.example.stanley_kubrick_archive.data

import androidx.compose.ui.graphics.Color
import kotlin.random.Random

data class Item(
    val id: Int, val title: String, val description: String, val color: Color = Color(
        Random.nextInt(256), Random.nextInt(256), Random.nextInt(256)
    )
)