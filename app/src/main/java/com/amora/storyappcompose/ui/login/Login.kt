package com.amora.storyappcompose.ui.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.amora.storyappcompose.R
import com.amora.storyappcompose.model.LoginRequests
import com.amora.storyappcompose.ui.DataState
import com.amora.storyappcompose.ui.main.NavScreen
import com.amora.storyappcompose.ui.theme.purple200
import com.amora.storyappcompose.ui.theme.white87

@Composable
fun Login(
    viewModel: LoginViewModel,
    navController: NavController
) {
    val loginState by viewModel.state.collectAsState()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(loginState) {
        when (loginState) {
            is DataState.Success -> {
                navController.navigate(NavScreen.Home.route)
                val snackbarMessage = loginState?.message
                if (snackbarMessage != null) {
                    snackbarHostState.showSnackbar(snackbarMessage, duration = SnackbarDuration.Short)
                }
                viewModel.resetState()
            }

            is DataState.Error -> {
                val snackbarMessage = loginState?.message
                if (snackbarMessage != null) {
                    snackbarHostState.showSnackbar(snackbarMessage, duration = SnackbarDuration.Short)
                }
                viewModel.resetState()
            }

            else -> {
                // no op
            }
        }
    }

    ConstraintLayout {
        val (body) = createRefs()
        Scaffold(
            backgroundColor = Color.White,
            topBar = { StoryAppBar() },
            modifier = Modifier.constrainAs(body) {
                top.linkTo(parent.top)
            }
        ) { innerPadding ->
            val modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)

            Box(modifier = modifier.fillMaxSize()) {
                if (loginState is DataState.Loading) {
                    Column(
                        modifier = modifier.fillMaxHeight(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator()
                    }
                }

                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(vertical = 16.dp),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Show Snackbar
                    SnackbarHost(hostState = snackbarHostState,
                        modifier = Modifier.padding(16.dp),
                    ) { snackbar ->
                        Snackbar(
                            modifier = Modifier.padding(8.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Filled.Info,
                                    contentDescription = "Info",
                                    modifier = Modifier.size(24.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(text = snackbar.visuals.message)
                            }
                        }
                    }
                }

                Column(modifier = modifier) {
                    OutlinedTextField(
                        modifier = modifier
                            .padding(horizontal = 16.dp)
                            .padding(top = 8.dp),
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email") }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(
                        modifier = modifier
                            .padding(horizontal = 16.dp)
                            .padding(top = 8.dp),
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password") },
                        visualTransformation = PasswordVisualTransformation()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            when {
                                email.isEmpty() -> {
                                    viewModel.updateError("Email Kosong")
                                }

                                password.isEmpty() -> {
                                    viewModel.updateError("Password Kosong")
                                }

                                else -> {
                                    val loginRequest = LoginRequests()
                                    loginRequest.email = email
                                    loginRequest.password = password
                                    viewModel.login(loginRequest)
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {
                        Text("Masuk", color = white87)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun StoryAppBar() {
    TopAppBar(
        elevation = 6.dp,
        backgroundColor = purple200,
        modifier = Modifier
            .statusBarsPadding()
            .height(58.dp)
    ) {
        Text(
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.CenterVertically),
            text = stringResource(id = R.string.app_name),
            color = white87,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}