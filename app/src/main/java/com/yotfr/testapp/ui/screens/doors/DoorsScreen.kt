@file:OptIn(ExperimentalMaterialApi::class)

package com.yotfr.testapp.ui.screens.doors

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.yotfr.testapp.ui.screens.doors.event.DoorsEvent
import com.yotfr.testapp.ui.screens.doors.model.DoorUi

@Composable
fun DoorsScreen(
    viewModel: DoorsViewModel = hiltViewModel()
) {
    val state by viewModel.state.observeAsState()

    val pullRefreshState = rememberPullRefreshState(
        refreshing = state?.isLoading ?: false,
        onRefresh = {
            viewModel.onEvent(
                DoorsEvent.Swiped
            )
        }
    )

    Box(
        modifier = Modifier.pullRefresh(state = pullRefreshState)
    ) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            state?.let {
                items(it.doors) { door ->
                    DoorsItem(
                        door = door,
                        modifier = Modifier.fillMaxWidth()
                            .padding(16.dp)
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

@Composable
fun DoorsItem(
    door: DoorUi,
    modifier: Modifier
) {
    Box(
        modifier = modifier
    ) {
        ElevatedCard(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp)
        ) {
            if (door.snapshot != null) {
                AsyncImage(
                    modifier = Modifier.fillMaxWidth(),
                    model = door.snapshot,
                    contentDescription = "camera",
                    contentScale = ContentScale.FillWidth
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = door.name
                )
                Icon(
                    imageVector = Icons.Outlined.Lock,
                    contentDescription = "Lock state"
                )
            }
        }
    }
}
