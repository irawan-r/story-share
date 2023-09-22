package com.amora.storyappcompose.ui.dashboard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.amora.storyappcompose.model.StoryItem
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import com.amora.storyappcompose.model.Story
import com.amora.storyappcompose.ui.theme.StoryAppComposeTheme
import com.amora.storyappcompose.utils.NetworkImage

@Composable
fun Stories(
    modifier: Modifier,
    stories: List<StoryItem>
) {
    val listState = rememberLazyListState()
    LazyColumn(modifier = modifier, contentPadding = PaddingValues(4.dp)) {
        if (stories.isNotEmpty()) {
            items(
                items = stories,
                key = { item -> item.id.toString() },
                itemContent = { stories ->
                    StoryPoster(modifier, stories)
                }
            )
        }
    }
}

@Composable
private fun StoryPoster(
    modifier: Modifier = Modifier,
    poster: StoryItem,
    selectedPoster: (String?) -> Unit = {}
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clickable(
                onClick = { selectedPoster(poster.id) }
            ),
        color = MaterialTheme.colors.onBackground,
        elevation = 8.dp,
        shape = RoundedCornerShape(8.dp)
    ) {
        ConstraintLayout(modifier = Modifier.padding(8.dp)) {
            val (image, title, content) = createRefs()

            NetworkImage(
                modifier = Modifier
                    .constrainAs(image) {
                        centerVerticallyTo(parent)
                        end.linkTo(title.start)
                    }
                    .height(64.dp)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(4.dp)),
                url = poster.photoUrl
            )

            Text(
                modifier = Modifier
                    .constrainAs(title) {
                        start.linkTo(image.end)
                    }
                    .padding(horizontal = 12.dp),
                text = poster.name,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h2
            )

            Text(
                modifier = Modifier
                    .constrainAs(content) {
                        start.linkTo(image.end)
                        top.linkTo(title.bottom)
                    }
                    .padding(start = 12.dp, top = 4.dp),
                text = poster.description.toString(),
                style = MaterialTheme.typography.body2
            )
        }
    }
}

@Composable
@Preview(name = "Poster Light")
private fun PosterPreviewLight() {
    StoryAppComposeTheme(darkTheme = false) {
        StoryPoster(poster = Story.mock())
    }
}

@Composable
@Preview(name = "Poster Dark")
private fun PosterPreviewDark() {
    StoryAppComposeTheme(darkTheme = true) {
        StoryPoster(poster = Story.mock())
    }
}