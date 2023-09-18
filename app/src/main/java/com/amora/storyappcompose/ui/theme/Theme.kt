package com.amora.storyappcompose.ui.theme

import DarkTypography
import LightTypography
import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorPalette = darkColors(
	background = background,
	onBackground = background800,
	primary = purple200,
	primaryVariant = purple500,
	secondary = purple500,
	onPrimary = Color.White,
	onSecondary = Color.White
)

@SuppressLint("ConflictingOnColor")
private val LightColorPalette = lightColors(
	background = Color.White,
	onBackground = Color.White,
	surface = Color.White,
	primary = purple200,
	primaryVariant = purple500,
	secondary = purple500,
	onPrimary = Color.White,
	onSecondary = Color.White
)

@Composable
fun StoryAppComposeTheme(
	darkTheme: Boolean = isSystemInDarkTheme(),
	dynamicColor: Boolean = true,
	content: @Composable () -> Unit
) {
	val colors = if (darkTheme) {
		DarkColorPalette
	} else {
		LightColorPalette
	}

	val typography = if (darkTheme) {
		DarkTypography
	} else {
		LightTypography
	}

	androidx.compose.material.MaterialTheme(
		colors = colors,
		typography = typography,
		shapes = shapes,
		content = content
	)
}