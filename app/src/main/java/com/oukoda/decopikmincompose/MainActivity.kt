package com.oukoda.decopikmincompose

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.VisibleForTesting
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.oukoda.decopikmincompose.component.AllPikminInfoView
import com.oukoda.decopikmincompose.component.DecorGroupView
import com.oukoda.decopikmincompose.model.dataclass.DecorGroup
import com.oukoda.decopikmincompose.model.room.entity.PikminRecord
import com.oukoda.decopikmincompose.model.viewmodel.MainViewModel
import com.oukoda.decopikmincompose.ui.theme.DecoPikminComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val owner = LocalViewModelStoreOwner.current!!
            val mainViewModel: MainViewModel = viewModel(
                owner,
                "MainViewModel",
                MainViewModel.MainViewModelFactory(
                    LocalContext.current.applicationContext as Application,
                ),
            )
            mainViewModel.createDecors()
            MainScreen(mainViewModel)
        }
    }
}

@Composable
fun MainScreen(mainViewModel: MainViewModel) {
    val decors by mainViewModel.decorGroups.collectAsState()
    val isLoading = mainViewModel.isLoading.observeAsState().value!!
    DecoPikminComposeTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background,
        ) {
            Box() {
                CreatePikminDecorView(decors) { pikminRecord ->
                    mainViewModel.updatePikminRecord(pikminRecord)
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
        }
    }
}

@Composable
@VisibleForTesting
private fun CreatePikminDecorView(
    decors: List<DecorGroup>,
    onClick: (pikminRecord: PikminRecord) -> Unit,
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        itemsIndexed(decors) { index, decor ->
            if (index == 0) {
                Spacer(modifier = Modifier.height(16.dp))
                AllPikminInfoView(allDecorGroups = decors, showCompleteDecorType = true) {}
                Spacer(modifier = Modifier.height(16.dp))
            }
            DecorGroupView(decor, onClick)
            if (index == decors.size - 1) {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DecoPikminComposeTheme {
    }
}
