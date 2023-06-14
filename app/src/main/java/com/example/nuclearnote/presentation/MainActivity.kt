package com.example.nuclearnote.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.nuclearnote.core.ui.theme.NuclearNoteAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            NuclearNoteAppTheme {



            }

        }

    }
}