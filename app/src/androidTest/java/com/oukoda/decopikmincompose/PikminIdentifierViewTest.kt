package com.oukoda.decopikmincompose

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.oukoda.decopikmincompose.component.PikminIdentifierView
import com.oukoda.decopikmincompose.model.dataclass.PikminIdentifier
import com.oukoda.decopikmincompose.model.enumclass.PikminStatusType
import com.oukoda.decopikmincompose.model.enumclass.PikminType
import com.oukoda.decopikmincompose.ui.theme.DecoPikminComposeTheme
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class PikminIdentifierViewTest {
    companion object {
        val TAG: String = PikminIdentifierViewTest::class.simpleName!!
    }

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun callbackUpdateNotHave() {
        callbackUpdate(pikminStatusType = PikminStatusType.NotHave)
    }

    @Test
    fun callbackUpdateAlready() {
        callbackUpdate(pikminStatusType = PikminStatusType.AlreadyExists)
    }

    @Test
    fun callbackUpdateGrowing() {
        callbackUpdate(pikminStatusType = PikminStatusType.Growing)
    }

    private fun callbackUpdate(pikminStatusType: PikminStatusType) {
        val pikminIdentifier = PikminIdentifier(PikminType.Red, 0, pikminStatusType)
        var callbackPikminIdentifier = pikminIdentifier.copy()
        composeTestRule.setContent {
            DecoPikminComposeTheme {
                PikminIdentifierView(pikminIdentifier = pikminIdentifier) {
                    callbackPikminIdentifier = it
                }
            }
        }
        val activity = composeTestRule.activity
        val labelString = activity.getString(pikminIdentifier.pikminType.stringId())
        val node = composeTestRule.onNodeWithText(labelString)
        node.assertIsDisplayed()
        node.performClick()
        assertEquals(pikminIdentifier.statusUpdate(), callbackPikminIdentifier)
    }
}
