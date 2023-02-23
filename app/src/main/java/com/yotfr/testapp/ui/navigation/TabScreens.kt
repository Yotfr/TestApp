package com.yotfr.testapp.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import com.yotfr.testapp.R
import com.yotfr.testapp.ui.screens.cameras.CameraScreen
import com.yotfr.testapp.ui.screens.doors.DoorsScreen

typealias ComposableFun = @Composable () -> Unit

sealed class TabScreens(@StringRes val title: Int, val screen: ComposableFun) {
    object CamerasScreen : TabScreens(title = R.string.cameras, screen = { CameraScreen() })
    object DoorsScreen : TabScreens(title = R.string.doors, screen = { DoorsScreen() })
}
