package com.oukoda.decopikmincompose

import androidx.activity.ComponentActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.oukoda.decopikmincompose.component.PikminIdentifierView
import com.oukoda.decopikmincompose.model.dataclass.PikminIdentifier
import com.oukoda.decopikmincompose.model.enumclass.PikminStatusType
import com.oukoda.decopikmincompose.model.enumclass.PikminType
import com.oukoda.decopikmincompose.ui.theme.DecoPikminComposeTheme
import org.junit.Rule
import org.junit.Test

class PikminIdentifierViewTest {
    companion object {
        val TAG: String = PikminIdentifierViewTest::class.simpleName!!
    }

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun tapPikminIdentifierTest() {
        val activity = composeTestRule.activity
        var pikminIdentifier = PikminIdentifier(PikminType.Red, 0, PikminStatusType.NotHave)
        composeTestRule.setContent {
            var rememberPikminIdentifier by remember {
                mutableStateOf(pikminIdentifier)
            }
            DecoPikminComposeTheme {
                PikminIdentifierView(pikminIdentifier = rememberPikminIdentifier) {
                    rememberPikminIdentifier = it
                    pikminIdentifier = it
                }
            }
        }
        (0..10).forEach { _ ->
            val labelString = activity.getString(pikminIdentifier.pikminType.stringId)
            val node = composeTestRule.onNodeWithText(labelString)
            node.assertIsDisplayed()
            node.performClick()
        }
    }
}
