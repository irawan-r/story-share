package com.amora.storyappcompose.ui.main

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.amora.storyappcompose.ui.dashboard.Dashboard
import com.amora.storyappcompose.ui.login.Login
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun StoryAppMainScreen() {
    val navController = rememberNavController()

    val colors = MaterialTheme.colors
    val systemUiController = rememberSystemUiController()

    var statusBarColor by remember { mutableStateOf(colors.primaryVariant) }
    var navigationBarColor by remember { mutableStateOf(colors.primaryVariant) }

    val animatedStatusBarColor by animateColorAsState(
        targetValue = statusBarColor, animationSpec = tween(), label = ""
    )

    val animatedNavigationBarColor by animateColorAsState(
        targetValue = statusBarColor,
        animationSpec = tween(), label = ""
    )
    NavHost(navController = navController, startDestination = NavScreen.Login.route) {
        composable(NavScreen.Login.route) {
            Login(
                viewModel = hiltViewModel(),
                navController = navController
            )

            LaunchedEffect(Unit) {
                statusBarColor = colors.primaryVariant
                navigationBarColor = colors.primaryVariant
            }
        }
        composable(NavScreen.Home.route) {
            Dashboard(
                viewModel = hiltViewModel(),
                navController = navController
            )

            LaunchedEffect(Unit) {
                statusBarColor = colors.primaryVariant
                navigationBarColor = colors.primaryVariant
            }
        }
    }

    LaunchedEffect(animatedStatusBarColor, animatedNavigationBarColor) {
        systemUiController.setStatusBarColor(animatedStatusBarColor)
        systemUiController.setNavigationBarColor(animatedNavigationBarColor)
    }
}

sealed class NavScreen(val route: String) {
    object Login : NavScreen("Login")

    object Home : NavScreen("Home")
}