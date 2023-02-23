package com.yotfr.testapp.ui.navigation

import androidx.compose.runtime.Composable
import com.yotfr.testapp.ui.screens.cameras.CameraScreen
import com.yotfr.testapp.ui.screens.doors.DoorsScreen

typealias ComposableFun = @Composable () -> Unit

sealed class TabScreens(val title: String, val screen: ComposableFun) {
    object CamerasScreen : TabScreens(title = "Cameras", screen = { CameraScreen() })
    object DoorsScreen : TabScreens(title = "Doors", screen = { DoorsScreen() })
}
