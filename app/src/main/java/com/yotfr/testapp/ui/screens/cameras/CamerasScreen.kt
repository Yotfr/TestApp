@file:OptIn(ExperimentalMaterialApi::class)

package com.yotfr.testapp.ui.screens.cameras

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
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
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            state?.rooms?.forEach {
                item {
                    RoomHeader(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                top = 16.dp,
                                start = 16.dp,
                                end = 16.dp
                            ),
                        roomName = it.name
                    )
                }
                items(it.cameras) { camera ->
                    CameraItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        camera = camera,
                        onFavorite = {
                            viewModel.onEvent(
                                CamerasEvent.FavoriteClicked(
                                    camera = camera
                                )
                            )
                        }
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
    onFavorite: () -> Unit
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
                        imageVector = Icons.Outlined.Favorite,
                        contentDescription = "delete action",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            )
        }
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp)
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
                        modifier = Modifier.align(Alignment.TopEnd),
                        imageVector = Icons.Outlined.Favorite,
                        contentDescription = "favorite"
                    )
                }
            }
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                text = camera.name
            )
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
            text = roomName
        )
    }
}
