package com.example.aklatopia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
