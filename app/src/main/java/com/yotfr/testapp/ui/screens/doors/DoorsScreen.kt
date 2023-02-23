@file:OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)

package com.yotfr.testapp.ui.screens.doors

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.yotfr.testapp.ui.screens.doors.event.DoorsEvent
import com.yotfr.testapp.ui.screens.doors.model.DoorUi
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
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            state?.let {
                items(it.doors) { door ->
                    DoorsItem(
                        door = door,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
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
            color = MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
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
                        Text(text = "Done")
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
    onEdit: () -> Unit
) {
    RevealSwipe(
        modifier = modifier,
        directions = setOf(
            RevealDirection.EndToStart
        ),
        backgroundCardEndColor = MaterialTheme.colorScheme.background,
        hiddenContentEnd = {
            IconButton(
                onClick = onEdit,
                content = {
                    Icon(
                        imageVector = Icons.Outlined.Edit,
                        contentDescription = "edit action",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            )
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
                modifier = Modifier
                    .fillMaxWidth()
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
