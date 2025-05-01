package com.example.aklatopia.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.aklatopia.R

@Composable
fun Star(){
    Image(
        painter = painterResource(id = R.drawable.filled_star),
        contentDescription = null,
        modifier = Modifier
            .size(37.dp)
            .padding(5.dp)
    )
}

@Composable
fun EmptyStar(){
    Image(
        painter = painterResource(id = R.drawable.bordered_star),
        contentDescription = null,
        modifier = Modifier
            .size(37.dp)
            .padding(5.dp)
    )
}