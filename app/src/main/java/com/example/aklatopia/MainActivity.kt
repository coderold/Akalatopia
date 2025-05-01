 package com.example.aklatopia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.aklatopia.navigation.StartingNav
import com.example.aklatopia.ui.theme.AklatopiaTheme

 class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AklatopiaTheme {
                StartingNav()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    StartingNav()
}