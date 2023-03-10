package com.oukoda.decopikmincompose

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.oukoda.decopikmincompose.component.BottomBar
import com.oukoda.decopikmincompose.model.enumclass.BottomItems
import com.oukoda.decopikmincompose.model.viewmodel.MainViewModel
import com.oukoda.decopikmincompose.screen.NormalScreen
import com.oukoda.decopikmincompose.screen.SpecialScreen
import com.oukoda.decopikmincompose.ui.theme.DecoPikminComposeTheme
import com.oukoda.decopikmincompose.ui.theme.statusBarDarkColor
import com.oukoda.decopikmincompose.ui.theme.statusBarLightColor

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DecoPikminComposeTheme {
                val systemUiController = rememberSystemUiController()
                val statusBarColor =
                    if (isSystemInDarkTheme()) statusBarDarkColor else statusBarLightColor
                SideEffect {
                    systemUiController.setStatusBarColor(
                        color = statusBarColor,
                    )
                }
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
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

    val isLoading by mainViewModel.isLoading.collectAsState()

    val allDecors by mainViewModel.normalDecorGroups.collectAsState()
    val showDecors by mainViewModel.showNormalDecorGroups.collectAsState()
    val showCompleteDecor by mainViewModel.showComplete.collectAsState()

    val specialDecorGroup by mainViewModel.specialDecorGroup.collectAsState()
    val showSpecialDecorGroup by mainViewModel.showSpecialDecorGroups.collectAsState()
    val showSpecialCompleteCostume by mainViewModel.showCompleteSpecial.collectAsState()

    var route by remember {
        mutableStateOf(navController.currentDestination?.route ?: startDestination)
    }

    navController.addOnDestinationChangedListener { _, destination, _ ->
        destination.route?.let {
            route = it
        }
    }

    Scaffold(
        scaffoldState = rememberScaffoldState(),
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
                        composable(BottomItems.Special.route()) {
                            SpecialScreen(
                                allSpecialDecorGroup = specialDecorGroup,
                                specialDecorGroup = showSpecialDecorGroup,
                                showCompleteCostume = showSpecialCompleteCostume,
                                onClick = { mainViewModel.updatePikminRecord(it) },
                                onSwitchChanged = { mainViewModel.updateShowCompleteSpecial(it) },
                            )
                        }
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
            BottomBar(route) {
                navController.navigate(it.route()) {
                    launchSingleTop = true
                }
            }
        },
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DecoPikminComposeTheme {}
}
