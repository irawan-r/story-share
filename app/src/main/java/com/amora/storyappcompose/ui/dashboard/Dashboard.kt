package com.amora.storyappcompose.ui.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.amora.storyappcompose.model.StoryItem
import com.amora.storyappcompose.ui.DataState
import com.amora.storyappcompose.ui.theme.purple200
import com.amora.storyappcompose.ui.theme.white87

@Composable
fun Dashboard(
    viewModel: DashboardViewModel,
    navController: NavController
) {

    val listStories by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .statusBarsPadding()
                    .height(58.dp),
                backgroundColor = purple200,
                elevation = 6.dp,
                title = {
                    Text(
                        "Home", color = white87,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
            )
        },
        content = { paddingValues ->
            val modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
            Column(
                modifier = modifier
                    .padding(16.dp)
            ) {
                LazyColumn(modifier = modifier) {
                    if (listStories is DataState.Success) {
                        val list = (listStories as DataState.Success<List<StoryItem>>).data as List<StoryItem>
                        if (list.isNotEmpty()) {
                            items(list.size) {

                            }
                        }
                    }
                }
            }
        }
    )

    ConstraintLayout {
        val (body, progress) = createRefs()
    }
}