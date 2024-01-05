package com.example.stanley_kubrick_archive.data

import androidx.compose.ui.graphics.Color
import com.example.stanley_kubrick_archive.R
import kotlin.random.Random

data class Item(
    val id: Int, val title: String, val description: String, val color: Color = Color(
        Random.nextInt(256), Random.nextInt(256), Random.nextInt(256)
    ), val image: Int = R.drawable.clockwork
)