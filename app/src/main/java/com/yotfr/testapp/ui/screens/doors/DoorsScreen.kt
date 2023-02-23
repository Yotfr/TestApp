@file:OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)

package com.yotfr.testapp.ui.screens.doors

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
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.yotfr.testapp.R
import com.yotfr.testapp.ui.screens.doors.event.DoorsEvent
import com.yotfr.testapp.ui.screens.doors.model.DoorUi
import com.yotfr.testapp.ui.util.elevation
import com.yotfr.testapp.ui.util.spacing
import de.charlex.compose.RevealDirection
import de.charlex.compose.RevealSwipe

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

    var showDialog by remember { mutableStateOf(false) }
    var doorState by remember { mutableStateOf<DoorUi?>(null) }

    if (showDialog) {
        doorState?.let {
            EditNameDialog(
                onOkPressed = { text, door ->
                    showDialog = false
                    viewModel.onEvent(
                        event = DoorsEvent.EditNameClicked(
                            door = door,
                            newName = text
                        )
                    )
                    doorState = null
                },
                onDismiss = {
                    showDialog = false
                    doorState = null
                },
                door = it
            )
        }
    }

    Box(
        modifier = Modifier.pullRefresh(state = pullRefreshState)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
                .padding(MaterialTheme.spacing.large)
        ) {
            state?.let {
                items(it.doors) { door ->
                    DoorsItem(
                        door = door,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                bottom = MaterialTheme.spacing.medium
                            ),
                        onFavorite = {
                            viewModel.onEvent(
                                event = DoorsEvent.FavoriteClicked(
                                    door = door
                                )
                            )
                        },
                        onEdit = {
                            showDialog = true
                            doorState = door
                        },
                        lockIcon = painterResource(id = R.drawable.ic_lock),
                        editIcon = painterResource(id = R.drawable.ic_edit),
                        favoriteIcon = painterResource(id = R.drawable.ic_star),
                        favoriteCamIcon = painterResource(id = R.drawable.ic_favorite)
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
fun EditNameDialog(
    door: DoorUi,
    onOkPressed: (text: String, door: DoorUi) -> Unit,
    onDismiss: () -> Unit
) {
    val textField = remember { mutableStateOf(door.name) }

    Dialog(onDismissRequest = {
        onDismiss()
    }) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surfaceColorAtElevation(MaterialTheme.elevation.extraSmall)
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.padding(MaterialTheme.spacing.standard),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth(),
                        colors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        placeholder = {
                            Text(text = "Enter new value")
                        },
                        value = textField.value,
                        onValueChange = {
                            textField.value = it
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { onOkPressed(textField.value, door) }) {
                        Text(text = "Change")
                    }
                }
            }
        }
    }
}

@Composable
fun DoorsItem(
    door: DoorUi,
    modifier: Modifier,
    onFavorite: () -> Unit,
    onEdit: () -> Unit,
    lockIcon: Painter,
    editIcon: Painter,
    favoriteIcon: Painter,
    favoriteCamIcon: Painter
) {
    RevealSwipe(
        modifier = modifier,
        directions = setOf(
            RevealDirection.EndToStart
        ),
        backgroundCardEndColor = MaterialTheme.colorScheme.background,
        maxRevealDp = 108.dp,
        hiddenContentEnd = {
            IconButton(
                onClick = onEdit,
                content = {
                    Icon(
                        painter = editIcon,
                        contentDescription = "edit action",
                        tint = Color.Unspecified
                    )
                }
            )
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
            elevation = CardDefaults.cardElevation(MaterialTheme.elevation.medium),
            colors = CardDefaults.elevatedCardColors(
                containerColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            if (door.snapshot != null) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    AsyncImage(
                        modifier = Modifier.fillMaxWidth(),
                        model = door.snapshot,
                        contentDescription = "camera",
                        contentScale = ContentScale.FillWidth
                    )
                    if (door.favorites) {
                        Icon(
                            modifier = Modifier.align(Alignment.TopEnd)
                                .padding(
                                    top = MaterialTheme.spacing.small,
                                    end = MaterialTheme.spacing.small
                                ),
                            painter = favoriteCamIcon,
                            contentDescription = "favorite",
                            tint = Color.Unspecified
                        )
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(72.dp)
                    .padding(
                        start = MaterialTheme.spacing.standard,
                        end = MaterialTheme.spacing.large
                    ),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    style = MaterialTheme.typography.titleMedium,
                    text = door.name
                )
                Icon(
                    painter = lockIcon,
                    contentDescription = "Lock state",
                    tint = Color.Unspecified
                )
            }
        }
    }
}
