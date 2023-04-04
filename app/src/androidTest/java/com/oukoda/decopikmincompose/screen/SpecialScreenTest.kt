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

class SpecialScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun test_SpecialのCostumeカテゴリが全て表示されること() {
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
            val specialDecorGroup by mainViewModel.specialDecorGroup.collectAsState()
            val showSpecialDecorGroup by mainViewModel.showSpecialDecorGroups.collectAsState()
            val showSpecialCompleteCostume by mainViewModel.showCompleteSpecial.collectAsState()

            isLoading = isLoadingState

            SpecialScreen(
                specialDecorGroup,
                showSpecialDecorGroup,
                showSpecialCompleteCostume,
                { },
                { },
            )
        }

        composeTestRule.waitUntil(3000) { !isLoading }

        val costumeTypes = DecorType.Special.costumeTypes()

        val lazyColumnNode = composeTestRule.onNodeWithTag("special_screen_lazy_column")

        (costumeTypes.indices).forEach {
            val costumeType = costumeTypes[it]
            val costumeTypeNode =
                composeTestRule.onNodeWithText(composeTestRule.activity.getString(costumeType.stringId))

            // DecorTypeが表示されること
            costumeTypeNode.assertExists()

            // スクロールすることで、LazyColumn内の要素を更新する
            lazyColumnNode.performScrollToIndex(it)
        }
    }
}
