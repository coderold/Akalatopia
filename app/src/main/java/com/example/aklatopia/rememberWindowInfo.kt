package com.example.aklatopia

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun rememberWindowInfo(): WindowInfo{
    val configuration = LocalConfiguration.current
    return WindowInfo(
        screenWidthInfo = when {
            configuration.screenWidthDp < 600 -> WindowInfo.WindowType.Compact
            else -> WindowInfo.WindowType.Medium
        },
        screenHeightInfo = when {
            configuration.screenWidthDp < 600 -> WindowInfo.WindowType.Compact
            else -> WindowInfo.WindowType.Medium
        },
        screenWidth = configuration.screenWidthDp.dp,
        screenHeight = configuration.screenHeightDp.dp
    )
}

data class WindowInfo(
    val screenWidthInfo: WindowType,
    val screenHeightInfo: WindowType,
    val screenWidth: Dp,
    val screenHeight: Dp
    ){
    sealed class WindowType {
        data object Compact: WindowType()
        data object Medium: WindowType()
    }
}