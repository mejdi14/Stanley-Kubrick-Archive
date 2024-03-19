package com.example.stanley_kubrick_archive.component

import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.tween
import androidx.compose.animation.with
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.stanley_kubrick_archive.R
import com.example.stanley_kubrick_archive.data.MovieCard

@OptIn(ExperimentalAnimationApi::class, ExperimentalFoundationApi::class)
@Composable
fun CardContent(
    item: MovieCard,
    selectedCard: Boolean,
    pagerState: PagerState,
    pagerPreviousPosition: MutableState<Int>
) {
    Log.d("TAG", "CardContent: ${pagerPreviousPosition.value}")
    Log.d("TAG", "CardContent: ${pagerState.currentPage}")
    val coroutineScope = rememberCoroutineScope()
            var direction by remember{ mutableStateOf(AnimatedContentScope.SlideDirection.Left) }
       if(pagerPreviousPosition.value != pagerState.currentPage){
           direction = if(pagerPreviousPosition.value > pagerState.currentPage) AnimatedContentScope.SlideDirection.Right else AnimatedContentScope.SlideDirection.Left
       }
    Surface(modifier = Modifier.fillMaxSize()) {
        AnimatedContent(
            targetState = pagerState.currentPage,
            transitionSpec = {
                slideIntoContainer(
                    animationSpec = tween(durationMillis = 300, easing = EaseIn),
                    towards = direction
                ).with(
                    slideOutOfContainer(
                        animationSpec = tween(durationMillis = 300, easing = EaseIn),
                        towards = direction
                    )
                )
            }, label = ""
        ) { targetState ->
            pagerPreviousPosition.value = pagerState.currentPage
            if(selectedCard)
            when (targetState) {
                0 -> {
                    Image(
                        painter = painterResource(id = item.image),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.FillBounds
                    )
                }

                1 -> Image(
                    painter = painterResource(id = item.makingOfImage),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds
                )

                2 -> {
                    val modifier =
                        Modifier
                            .width((item.cardHeight / 3).dp)
                            .height((item.cardHeight / 2).dp)

                    Column {
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .background(color = Color.Black)) {
                            ActorCardItem(imageRes = item.listActors[0].image, modifier.weight(1f))
                            ActorCardItem(
                                imageRes = item.listActors[1].image,
                                modifier = modifier.weight(1f)
                            )
                            ActorCardItem(
                                imageRes = item.listActors[2].image,
                                modifier = modifier.weight(1f)
                            )
                        }
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .background(color = Color.Black)) {
                            ActorCardItem(
                                imageRes = item.listActors[3].image,
                                modifier = modifier.weight(1f)
                            )
                            ActorCardItem(
                                imageRes = item.listActors[4].image,
                                modifier = modifier.weight(1f)
                            )
                            ActorCardItem(
                                imageRes = item.listActors[5].image,
                                modifier = modifier.weight(1f)
                            )
                        }
                    }

                }

                else -> {
                    Image(
                        painter = painterResource(id = R.drawable.lolita),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.FillBounds
                    )
                }
            } else {
                Image(
                    painter = painterResource(id = item.image),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds
                )
            }
        }
    }
}

