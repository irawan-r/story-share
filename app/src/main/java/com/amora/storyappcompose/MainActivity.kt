package com.amora.storyappcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.amora.storyappcompose.ui.main.StoryAppMainScreen
import com.amora.storyappcompose.ui.theme.StoryAppComposeTheme
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		WindowCompat.setDecorFitsSystemWindows(window, false)
		Timber.plant(Timber.DebugTree())
		setContent {
			StoryAppComposeTheme {
				StoryAppMainScreen()
			}
		}
	}
}