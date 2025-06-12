package com.sonifadhilah0132.dexlite

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.sonifadhilah0132.dexlite.ui.screen.MainScreen
import com.sonifadhilah0132.dexlite.ui.theme.DexLiteTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DexLiteTheme {
                MainScreen()
                }
            }
        }
    }