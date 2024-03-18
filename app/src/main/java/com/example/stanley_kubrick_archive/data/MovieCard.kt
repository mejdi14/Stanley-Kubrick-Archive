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
    val image: Int = R.drawable.clockwork_actor1,
    val cardHeight: Float = 288f,
    val cardOverlap: Float = 124f,
    val crossPathHeight: Float = cardHeight + cardOverlap,
    val crossVisibleHeight: Float = cardHeight - cardOverlap,
    val cardSelectionAnimationDuration: Long = 1000,
    val cardCameraDistance: Float = 33f,
    val listActors: List<MovieActor> = listOf(),
    val listDescription: List<MovieDescription> = listOf(),
    val id: String = UUID.randomUUID().toString(),
)

val movieList = listOf(
    MovieCard(
        "Title 1", "Description 1", image = R.drawable.clockwork_orange,
        listActors = clockWorkOrangeActors, listDescription = clockWorkOrangeDescription
    ),
    MovieCard(
        "Title 1",
        "Description 1",
        image = R.drawable.bary,
        listActors = baryActors,
        listDescription = baryDescription
    ),
    MovieCard(
        "Title 1",
        "Description 1",
        image = R.drawable.space_odyssy,
        listActors = spaceOdysseyActors,
        listDescription = spaceOdysseyDescription
    ),
    MovieCard(
        "Title 1",
        "Description 1",
        image = R.drawable.metal_jacket,
        listActors = metalJacketActors,
        listDescription = metalJacketDescription
    ),
    MovieCard(
        "Title 1",
        "Description 1",
        image = R.drawable.shining,
        listActors = shiningActors,
        listDescription = shiningDescription
    ),
    MovieCard(
        "Title 1",
        "Description 1",
        image = R.drawable.paths_of_glory,
        listActors = pathOfGloryActors,
        listDescription = pathOfGloryDescription
    ),
    MovieCard(
        "Title 1",
        "Description 1",
        image = R.drawable.dr_strangelove,
        listActors = strangeLoveActors,
        listDescription = strangeLoveDescription
    ),
    MovieCard(
        "Title 1",
        "Description 1",
        image = R.drawable.spartacus,
        listActors = spartacusActors,
        listDescription = spartacusDescription
    ),
    MovieCard(
        "Title 1",
        "Description 1",
        image = R.drawable.the_killing_poster,
        listActors = theKillingActors,
        listDescription = theKillingDescription
    ),
    MovieCard(
        "Title 1",
        "Description 1",
        image = R.drawable.eyes_wide_shut,
        listActors = eyesWideShutActors,
        listDescription = eyesWideShutDescription
    ),
    MovieCard(
        "Title 1",
        "Description 1",
        image = R.drawable.fear_and_desire,
        listActors = fearAndDesireActors,
        listDescription = eyesWideShutDescription
    ),
    MovieCard(
        "Title 1", "Description 1", image = R.drawable.lolita, listActors = lolitaActors,
        listDescription = lolitaDescription
    ),
)
