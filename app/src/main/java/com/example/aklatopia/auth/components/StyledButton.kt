package com.example.aklatopia.auth.components

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun RoutedButton(
    label: String,
    navHostController: NavHostController,
    route: String,
    color: Color,
    modifier: Modifier
){
    Button(
        onClick = { navHostController.navigate(route) },
        colors = ButtonDefaults.buttonColors(
            containerColor = color,
            contentColor = MaterialTheme.colorScheme.onTertiary
        ),
        modifier = modifier
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 20.sp
        )
    }
}
