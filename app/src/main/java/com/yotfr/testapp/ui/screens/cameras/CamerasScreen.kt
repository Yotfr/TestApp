@file:OptIn(ExperimentalMaterialApi::class)

package com.yotfr.testapp.ui.screens.cameras

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.yotfr.testapp.R
import com.yotfr.testapp.ui.screens.cameras.event.CamerasEvent
import com.yotfr.testapp.ui.screens.cameras.model.CameraUi
import de.charlex.compose.RevealDirection
import de.charlex.compose.RevealSwipe

@Composable
fun CameraScreen(
    viewModel: CamerasViewModel = hiltViewModel()
) {
    val state by viewModel.state.observeAsState()

    val pullRefreshState = rememberPullRefreshState(
        refreshing = state?.isLoading ?: false,
        onRefresh = {
            viewModel.onEvent(
                CamerasEvent.Swiped
            )
        }
    )

    Box(
        modifier = Modifier.pullRefresh(state = pullRefreshState)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
                .padding(21.dp)
        ) {
            state?.rooms?.forEach {
                item {
                    RoomHeader(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp),
                        roomName = it.name
                    )
                }
                items(it.cameras) { camera ->
                    CameraItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp),
                        camera = camera,
                        onFavorite = {
                            viewModel.onEvent(
                                CamerasEvent.FavoriteClicked(
                                    camera = camera
                                )
                            )
                        },
                        favoriteIcon = painterResource(id = R.drawable.ic_star),
                        favoriteCamIcon = painterResource(id = R.drawable.ic_favorite),
                        recIcon = painterResource(id = R.drawable.ic_rec)
                    )
                }
            }
        }
        PullRefreshIndicator(
            refreshing = state?.isLoading ?: false,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}

@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun CameraItem(
    camera: CameraUi,
    modifier: Modifier,
    onFavorite: () -> Unit,
    recIcon: Painter,
    favoriteCamIcon: Painter,
    favoriteIcon: Painter
) {
    RevealSwipe(
        modifier = modifier,
        directions = setOf(
            RevealDirection.EndToStart
        ),
        backgroundCardEndColor = MaterialTheme.colorScheme.background,
        hiddenContentEnd = {
            IconButton(
                onClick = onFavorite,
                content = {
                    Icon(
                        painter = favoriteIcon,
                        contentDescription = "delete action",
                        tint = Color.Unspecified
                    )
                }
            )
        }
    ) {
        ElevatedCard(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(3.dp)
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                AsyncImage(
                    modifier = Modifier.fillMaxWidth(),
                    model = camera.snapshot,
                    contentDescription = "camera",
                    contentScale = ContentScale.FillWidth
                )
                if (camera.isFavorite) {
                    Icon(
                        modifier = Modifier.align(Alignment.TopEnd)
                            .padding(top = 8.dp, end = 8.dp),
                        painter = favoriteCamIcon,
                        contentDescription = "favorite",
                        tint = Color.Unspecified
                    )
                }
                if (camera.rec) {
                    Icon(
                        modifier = Modifier.align(Alignment.TopStart)
                            .padding(top = 8.dp, start = 8.dp),
                        painter = recIcon,
                        contentDescription = "rec",
                        tint = Color.Unspecified
                    )
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(72.dp)
                    .padding(
                        start = 16.dp,
                        end = 24.dp
                    ),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    fontSize = 17.sp,
                    text = camera.name
                )
            }
        }
    }
}

@Composable
fun RoomHeader(
    roomName: String,
    modifier: Modifier
) {
    Box(
        modifier = modifier
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = roomName,
            fontSize = 21.sp
        )
    }
}
