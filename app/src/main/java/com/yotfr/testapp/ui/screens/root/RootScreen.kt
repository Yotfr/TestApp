@file:OptIn(ExperimentalMaterial3Api::class)

package com.yotfr.testapp.ui.screens.root

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.google.accompanist.pager.*
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
            modifier = Modifier.padding(it)
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
            Text(text = "My home")
        }
    )
}

@Composable
fun Tabs(tabs: List<TabScreens>, selectedIndex: Int, onTabClicked: (index: Int) -> Unit) {
    TabRow(
        modifier = Modifier.fillMaxWidth(),
        selectedTabIndex = selectedIndex,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                modifier = Modifier.tabIndicatorOffset(
                    tabPositions[selectedIndex]
                )
            )
        }
    ) {
        tabs.forEachIndexed { index, tabScreens ->
            Tab(
                text = {
                    Text(text = tabScreens.title)
                },
                selected = selectedIndex == index,
                onClick = {
                    onTabClicked(index)
                }
            )
        }
    }
}

@Composable
fun TabsContent(tabs: List<TabScreens>, selectedIndex: Int) {
    tabs[selectedIndex].screen()
}
