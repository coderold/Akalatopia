package com.example.aklatopia.assets

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.aklatopia.R

@Composable
fun BlueBackButton(navHostController: NavHostController, modifier: Modifier){
    Image(
        painter = painterResource(id = R.drawable.back_icon),
        contentDescription = "back",
        modifier = modifier
            .then(
                Modifier
                .clickable { navHostController.popBackStack() }
                .size(48.dp)
            )

    )
}

@Composable
fun BeigeBackButton(navHostController: NavHostController, modifier: Modifier){
    Image(
        painter = painterResource(id = R.drawable.beige_back_icon),
        contentDescription = "back",
        modifier = modifier
            .then(
                Modifier
                .clickable { navHostController.popBackStack() }
                .size(48.dp)
            )

    )
}
