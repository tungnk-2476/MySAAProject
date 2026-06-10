package com.example.mysaaproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.mysaaproject.ui.AppRoot
import com.example.mysaaproject.ui.theme.MySAAProjectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MySAAProjectTheme {
                AppRoot()
            }
        }
    }
}
