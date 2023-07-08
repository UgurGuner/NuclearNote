package com.example.nuclearnote.presentation.util

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun StatusBarColor(color: Int) {

    val activity = LocalContext.current as? Activity ?: return

    activity.window.statusBarColor = color

    activity.window.navigationBarColor = color

}