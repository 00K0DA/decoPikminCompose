package com.oukoda.decopikmincompose

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.oukoda.decopikmincompose.component.BottomBar
import com.oukoda.decopikmincompose.component.NormalScreen
import com.oukoda.decopikmincompose.model.enumclass.BottomItems
import com.oukoda.decopikmincompose.model.viewmodel.MainViewModel
import com.oukoda.decopikmincompose.ui.theme.DecoPikminComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}

@Composable
fun MainScreen() {
    val scaffoldState = rememberScaffoldState()
    val navController = rememberNavController()
    val startDestination = BottomItems.Normal.route()

    val owner = LocalViewModelStoreOwner.current!!
    val mainViewModel: MainViewModel = viewModel(
        owner,
        MainViewModel.KEY,
        MainViewModel.MainViewModelFactory(
            LocalContext.current.applicationContext as Application,
        ),
    )
    mainViewModel.createDecors()

    val allDecors by mainViewModel.decorGroups.collectAsState()
    val showDecors by mainViewModel.showDecorGroups.collectAsState()
    val isLoading = mainViewModel.isLoading.observeAsState().value!!
    val showCompleteDecor = mainViewModel.showComplete.observeAsState().value!!

    DecoPikminComposeTheme {
        Scaffold(
            scaffoldState = scaffoldState,
            content = {
                Box {
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it),
                        color = MaterialTheme.colors.background,
                    ) {
                        NavHost(
                            navController = navController,
                            startDestination = BottomItems.Normal.route(),
                        ) {
                            composable(BottomItems.Normal.route()) {
                                NormalScreen(
                                    allDecors,
                                    showDecors,
                                    showCompleteDecor,
                                    { mainViewModel.updatePikminRecord(it) },
                                    { mainViewModel.updateShowComplete(it) },
                                )
                            }
                            composable(BottomItems.Special.route()) { Text(text = "Special") }
                        }
                    }
                    AnimatedVisibility(
                        visible = isLoading,
                        exit = fadeOut(),
                    ) {
                        Box(
                            Modifier
                                .fillMaxSize()
                                .background(Color.DarkGray.copy(alpha = 0.9F)),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(text = "Loading", color = Color.White)
                        }
                    }
                }
            },
            bottomBar = {
                val route = if (navController.currentDestination != null) {
                    navController.currentDestination!!.route!!
                } else {
                    startDestination
                }
                BottomBar(route) {
                    navController.navigate(it.route()) { launchSingleTop = true }
                }
            },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DecoPikminComposeTheme {
    }
}
