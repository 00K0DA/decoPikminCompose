package com.oukoda.decopikmincompose

import androidx.activity.ComponentActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assertIsOff
import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.oukoda.decopikmincompose.component.AllPikminInfoView
import com.oukoda.decopikmincompose.model.dataclass.CostumeGroup
import com.oukoda.decopikmincompose.model.dataclass.DecorGroup
import com.oukoda.decopikmincompose.model.enumclass.DecorType
import com.oukoda.decopikmincompose.ui.theme.DecoPikminComposeTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AllPikminInfoViewTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var decorGroups: List<DecorGroup>

    @Before
    fun setUp() {
        decorGroups = DecorType.values().map { decorType ->
            DecorGroup(
                decorType,
                decorType.costumeTypes().map { costumeType ->
                    CostumeGroup.newInstance(costumeType)
                },
            )
        }
    }

    @Test
    fun textLabelTest() {
        val activity = composeTestRule.activity
        composeTestRule.setContent {
            DecoPikminComposeTheme {
                AllPikminInfoView(
                    allDecorGroups = decorGroups,
                    showCompleteDecorType = true,
                    onChanged = {},
                )
            }
        }

        val countTextTemplate = activity.getString(R.string.all_pikmin_info_view_have_count)
        val percentTextTemplate = activity.getString(R.string.all_pikmin_info_view_have_percent)

        composeTestRule.onNodeWithText(
            countTextTemplate.format(
                0,
                decorGroups.sumOf { it.getCount() },
            ),
        ).assertExists()

        composeTestRule.onNodeWithText(
            percentTextTemplate.format(0F),
        ).assertExists()
    }

    @Test
    fun checkBoxToggleTest() {
        var showCompleteDecorType = false
        composeTestRule.setContent {
            var rememberShowCompleteDecorType by remember { mutableStateOf(showCompleteDecorType) }
            DecoPikminComposeTheme {
                AllPikminInfoView(
                    allDecorGroups = decorGroups,
                    showCompleteDecorType = rememberShowCompleteDecorType,
                    onChanged = {
                        rememberShowCompleteDecorType = it
                        showCompleteDecorType = it
                    },
                )
            }
        }

        val checkBoxNode = composeTestRule.onNode(hasClickAction())
        checkBoxNode.assertExists()
        (0..4).forEach { _ ->
            checkBoxNode.performClick()
            composeTestRule.waitForIdle()
            if (showCompleteDecorType) {
                checkBoxNode.assertIsOn()
            } else {
                checkBoxNode.assertIsOff()
            }
        }
    }
}
