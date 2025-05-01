package com.example.aklatopia.ui.theme


import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


private val AklatopiaColorScheme = lightColorScheme(
    primary = DarkBlue,
    onPrimary = Color.White,
    primaryContainer = OffWhite,
    onPrimaryContainer = DarkBlue,

    secondary = Green,
    onSecondary = Beige,

    tertiary = Red,
    onTertiary = Beige,

    error = Red,
    onError = Beige,

    background = Beige,
    onBackground = DarkBlue,

    surface = Beige,
    onSurface = DarkBlue,

    surfaceVariant = Yellow,
    onSurfaceVariant = DarkBlue,

    outline = DarkBlue

)

@Composable
fun AklatopiaTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = AklatopiaColorScheme,
        typography = Typography,
        content = content
    )
}