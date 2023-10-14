package com.arvind.moviesapp.screen.movie_details.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arvind.moviesapp.R
import com.arvind.moviesapp.domain.models.MoviesDetails
import com.arvind.moviesapp.screen.destinations.VideoPlayerScreenDestination
import com.arvind.moviesapp.ui.theme.AppBarExpendedHeight
import com.arvind.moviesapp.ui.theme.GrapeFruitColor
import com.arvind.moviesapp.ui.theme.PrimaryGray
import com.arvind.moviesapp.ui.theme.primaryPurpleColor
import com.arvind.moviesapp.utils.*
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import timber.log.Timber
import java.util.*

@Composable
fun MovieInfo(
    scrollState: LazyListState,
    releaseDate: String,
    overview: String,
    navigator: DestinationsNavigator,
    runTime: Int,
    languages: String,
    popularity: Int,
    budget: String,
    revenue: String,
    details: Resource<MoviesDetails>,
    movieId: Int,
) {

    LazyColumn(
        contentPadding = PaddingValues(top = AppBarExpendedHeight),
        state = scrollState
    ) {
        item {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 10.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text =
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(color = PrimaryGray)) {
                            append(Date().convertDate(releaseDate))
                        }
                        withStyle(style = SpanStyle(color = Color.White)) {
                            append(" • ")
                        }
                        withStyle(style = SpanStyle(color = PrimaryGray)) {
                            append(languages.uppercase(Locale.ROOT))
                        }
                        withStyle(style = SpanStyle(color = Color.White)) {
                            append(" • ")
                        }
                        withStyle(style = SpanStyle(color = PrimaryGray)) {
                            append(runTime.hourMinutes())
                        }
                    },
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(10.dp))

                LazyRow(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth(),
                    content = {
                        details.data?.genres?.forEach {
                            item {
                                Text(
                                    text = if (it == details.data.genres.last()) it?.name.toString() else it?.name + ", ",
                                    style = MaterialTheme.typography.body1,
                                    color = PrimaryGray,
                                    maxLines = 1
                                )
                            }
                        }
                    })
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text =
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Color.White)) {
                            append("Popularity: ")
                        }
                        withStyle(style = SpanStyle(color = PrimaryGray)) {
                            append(popularity.toString())
                        }
                        withStyle(style = SpanStyle(color = Color.White)) {
                            append(" Budget: ")
                        }
                        withStyle(style = SpanStyle(color = PrimaryGray)) {
                            append(getFormatedNumber(budget.toLong()))
                        }
                        withStyle(style = SpanStyle(color = Color.White)) {
                            append(" Revenue: ")
                        }
                        withStyle(style = SpanStyle(color = PrimaryGray)) {
                            append(getFormatedNumber(revenue.toLong()))
                        }

                    },
                    fontSize = 14.sp,
                    maxLines = 1,
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = {
                            navigator.navigate(VideoPlayerScreenDestination(movieId))
                        },
                        shape = RoundedCornerShape(size = 16.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = GrapeFruitColor),
                        modifier = Modifier.weight(1f),
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(20.dp),
                            imageVector = Icons.Filled.PlayArrow,
                            tint = Color.White,
                            contentDescription = stringResource(id = R.string.play)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Play",
                            color = Color.White,
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                }

                Spacer(modifier = Modifier.height(10.dp))
                ExpandableText(text = overview)
            }

        }
    }
}


@Composable
fun ExpandableText(
    text: String,
    modifier: Modifier = Modifier,
    minimizedMaxLines: Int = 3,
) {
    var cutText by remember(text) { mutableStateOf<String?>(null) }
    var expanded by remember { mutableStateOf(false) }
    val textLayoutResultState = remember { mutableStateOf<TextLayoutResult?>(null) }
    val seeMoreSizeState = remember { mutableStateOf<IntSize?>(null) }
    val seeMoreOffsetState = remember { mutableStateOf<Offset?>(null) }

    // getting raw values for smart cast
    val textLayoutResult = textLayoutResultState.value
    val seeMoreSize = seeMoreSizeState.value
    val seeMoreOffset = seeMoreOffsetState.value

    LaunchedEffect(text, expanded, textLayoutResult, seeMoreSize) {
        val lastLineIndex = minimizedMaxLines - 1
        if (!expanded && textLayoutResult != null && seeMoreSize != null
            && lastLineIndex + 1 == textLayoutResult.lineCount
            && textLayoutResult.isLineEllipsized(lastLineIndex)
        ) {
            var lastCharIndex =
                textLayoutResult.getLineEnd(lastLineIndex, visibleEnd = true) + 1
            var charRect: Rect
            do {
                lastCharIndex -= 1
                charRect = textLayoutResult.getCursorRect(lastCharIndex)
            } while (
                charRect.left > textLayoutResult.size.width - seeMoreSize.width
            )
            seeMoreOffsetState.value =
                Offset(charRect.left, charRect.bottom - seeMoreSize.height)
            cutText = text.substring(startIndex = 0, endIndex = lastCharIndex)
        }
    }

    Box(modifier) {
        Text(
            color = Color.LightGray,
            text = cutText ?: text,
            fontSize = 13.sp,
            modifier = Modifier
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    expanded = false
                },
            maxLines = if (expanded) Int.MAX_VALUE else minimizedMaxLines,
            overflow = TextOverflow.Ellipsis,
            onTextLayout = { textLayoutResultState.value = it },
        )
        if (!expanded) {
            val density = LocalDensity.current
            Text(
                color = primaryPurpleColor,
                text = "... Read more",
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                onTextLayout = { seeMoreSizeState.value = it.size },
                modifier = Modifier
                    .then(
                        if (seeMoreOffset != null)
                            Modifier.offset(
                                x = with(density) { seeMoreOffset.x.toDp() },
                                y = with(density) { seeMoreOffset.y.toDp() },
                            )
                        else Modifier
                    )
                    .clickable(
                        interactionSource = MutableInteractionSource(),
                        indication = null
                    ) {
                        expanded = true
                        cutText = null
                    }
                    .alpha(if (seeMoreOffset != null) 1f else 0f)
            )
        }
    }
}


@Composable
@Preview(showBackground = true)
fun FilmInfoPreview() {

}