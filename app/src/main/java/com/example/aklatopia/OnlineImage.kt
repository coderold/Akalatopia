package com.example.aklatopia

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.rememberAsyncImagePainter

@Composable
fun OnlineImage(
    imageUrl: String,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.Crop
) {
    Image(
        painter = rememberAsyncImagePainter(model = imageUrl),
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = contentScale
    )
}