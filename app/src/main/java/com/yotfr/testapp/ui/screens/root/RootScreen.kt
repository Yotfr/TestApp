@file:OptIn(ExperimentalMaterial3Api::class)

package com.yotfr.testapp.ui.screens.root

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.zIndex
import com.google.accompanist.pager.*
import com.yotfr.testapp.R
import com.yotfr.testapp.ui.navigation.TabScreens

@Composable
fun RootScreen() {
    val tabs = listOf(
        TabScreens.CamerasScreen,
        TabScreens.DoorsScreen
    )
    var selectedIndex by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            TopBar()
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(it)
        ) {
            Tabs(
                tabs = tabs,
                selectedIndex = selectedIndex,
                onTabClicked = { index ->
                    selectedIndex = index
                }
            )
            TabsContent(
                tabs = tabs,
                selectedIndex = selectedIndex
            )
        }
    }
}

@Composable
fun TopBar() {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.my_house),
                style = MaterialTheme.typography.titleLarge
            )
        }
    )
}

@Composable
fun Tabs(tabs: List<TabScreens>, selectedIndex: Int, onTabClicked: (index: Int) -> Unit) {
    TabRow(
        modifier = Modifier.fillMaxWidth(),
        selectedTabIndex = selectedIndex
    ) {
        tabs.forEachIndexed { index, tabScreens ->
            Tab(
                text = {
                    Text(
                        text = stringResource(id = tabScreens.title),
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                selected = selectedIndex == index,
                onClick = {
                    onTabClicked(index)
                },
                selectedContentColor = MaterialTheme.colorScheme.onBackground,
                unselectedContentColor = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@Composable
fun TabsContent(tabs: List<TabScreens>, selectedIndex: Int) {
    Box(modifier = Modifier.zIndex(-1f)) {
        tabs[selectedIndex].screen()
    }
}
