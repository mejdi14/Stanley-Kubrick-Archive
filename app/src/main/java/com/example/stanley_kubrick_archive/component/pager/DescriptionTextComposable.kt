package com.example.stanley_kubrick_archive.component.pager

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import com.example.stanley_kubrick_archive.R
import com.example.stanley_kubrick_archive.data.movieList

@OptIn(ExperimentalFoundationApi::class)
@Composable
 fun DescriptionTextComposable(
    selectedCardValue: Int?,
    pagerState: PagerState
) {
    Text(modifier = Modifier.fillMaxSize(),
        text = if (movieList.isNotEmpty() && movieList[selectedCardValue
                ?: 0].listDescription.isNotEmpty()
        ) movieList[selectedCardValue
            ?: 0].listDescription[pagerState.currentPage].description else "",
        color = Color.White,
        style = TextStyle(
            fontFamily = myCustomFontFamily,
            fontSize = 16.sp
        )
    )
}

val myCustomFontFamily = FontFamily(
    Font(R.font.roboto_light)
)