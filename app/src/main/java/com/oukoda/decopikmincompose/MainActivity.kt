package com.oukoda.decopikmincompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oukoda.decopikmincompose.component.PikminDecorView
import com.oukoda.decopikmincompose.model.viewmodel.MainViewModel
import com.oukoda.decopikmincompose.ui.theme.DecoPikminComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }

    }

    @Composable
    fun MainScreen() {
        val mainViewModel = MainViewModel()
        DecoPikminComposeTheme {
            // A surface container using the 'background' color from the theme
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colors.background,
            ) {
                Column() {
                    Spacer(modifier = Modifier.height(16.dp))
                    CreatePikminDecorView(mainViewModel)
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }

    @Composable
    @VisibleForTesting
    private fun CreatePikminDecorView(mainViewModel: MainViewModel) {
        val decors by mainViewModel.decors
        mainViewModel.createDecors()
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(decors) { decor ->
                PikminDecorView(decor)
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