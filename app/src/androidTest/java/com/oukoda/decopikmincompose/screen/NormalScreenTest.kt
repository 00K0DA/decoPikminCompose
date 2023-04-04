package com.oukoda.decopikmincompose.screen

import android.app.Application
import androidx.activity.ComponentActivity
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performScrollToIndex
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.oukoda.decopikmincompose.model.enumclass.DecorType
import com.oukoda.decopikmincompose.model.viewmodel.MainViewModel
import org.junit.Rule
import org.junit.Test

class NormalScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun test_Special以外のカテゴリが全て表示されること() {
        var isLoading = true
        composeTestRule.setContent {
            val owner = LocalViewModelStoreOwner.current!!
            val mainViewModel: MainViewModel = viewModel(
                owner,
                MainViewModel.KEY,
                MainViewModel.MainViewModelFactory(
                    LocalContext.current.applicationContext as Application,
                ),
            )
            mainViewModel.createDecors()
            val isLoadingState by mainViewModel.isLoading.collectAsState()
            val allDecors by mainViewModel.normalDecorGroups.collectAsState()
            val showDecors by mainViewModel.showNormalDecorGroups.collectAsState()
            val showCompleteDecor by mainViewModel.showComplete.collectAsState()

            isLoading = isLoadingState

            NormalScreen(
                allDecors,
                showDecors,
                showCompleteDecor,
                { },
                { },
            )
        }

        composeTestRule.waitUntil(3000) { !isLoading }

        val decorTypes = DecorType.values().filter { decorType -> decorType != DecorType.Special }

        val lazyColumnNode = composeTestRule.onNodeWithTag("normal_screen_lazy_column")

        (decorTypes.indices).forEach {
            val decorType = decorTypes[it]
            val decorTypeNode =
                composeTestRule.onNodeWithText(composeTestRule.activity.getString(decorType.stringId))
            val specialDecorNode =
                composeTestRule.onNodeWithText(composeTestRule.activity.getString(DecorType.Special.stringId))

            // DecorTypeが表示されること
            decorTypeNode.assertExists()

            // Specialが表示されないこと
            specialDecorNode.assertDoesNotExist()

            // スクロールすることで、LazyColumn内の要素を更新する
            lazyColumnNode.performScrollToIndex(it)
        }
    }
}
