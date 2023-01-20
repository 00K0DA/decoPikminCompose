package com.oukoda.decopikmincompose

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.oukoda.decopikmincompose.component.DecorGroupView
import com.oukoda.decopikmincompose.model.dataclass.CostumeGroup
import com.oukoda.decopikmincompose.model.dataclass.DecorGroup
import com.oukoda.decopikmincompose.model.enumclass.DecorType
import com.oukoda.decopikmincompose.model.enumclass.PikminStatusType
import com.oukoda.decopikmincompose.model.enumclass.PikminType
import com.oukoda.decopikmincompose.ui.theme.DecoPikminComposeTheme
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class DecorGroupViewTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun clickPikminIdentifierTest1Group() {
        val decorType = DecorType.Airport
        assertEquals(1, decorType.costumeTypes().size)
        val costumeGroups = decorType.costumeTypes().map { costumeType ->
            CostumeGroup.newInstance(costumeType)
        }
        val decorGroup = DecorGroup(decorType, costumeGroups)
        val tapCount = decorGroup.sumOf { it.count() } * 5
        clickPikminIdentifierTest(decorGroup, tapCount)
    }

    @Test
    fun clickPikminIdentifierTest2Groups() {
        val decorType = DecorType.Forest
        assertEquals(2, decorType.costumeTypes().size)
        val costumeGroups = decorType.costumeTypes().map { costumeType ->
            CostumeGroup.newInstance(costumeType)
        }
        val decorGroup = DecorGroup(decorType, costumeGroups)
        val tapCount = decorGroup.sumOf { it.count() } * 5
        clickPikminIdentifierTest(decorGroup, tapCount)
    }

    @Test
    fun clickPikminIdentifierTestManyGroups() {
        val decorType = DecorType.Special
        val costumeGroups = decorType.costumeTypes().map { costumeType ->
            CostumeGroup.newInstance(costumeType)
        }
        val decorGroup = DecorGroup(decorType, costumeGroups)
        val tapCount = decorGroup.sumOf { it.count() } * 5
        clickPikminIdentifierTest(DecorGroup(decorType, costumeGroups), tapCount)
    }

    private fun clickPikminIdentifierTest(initialDecorGroup: DecorGroup, tapCount: Int) {
        var decorGroup = initialDecorGroup
        composeTestRule.setContent {
            DecoPikminComposeTheme {
                DecorGroupView(decorGroup = decorGroup) {
                    for (cg in decorGroup) {
                        if (cg.costumeType == it.costumeType) {
                            decorGroup =
                                decorGroup.updateCostumeGroup(cg.applyPikminRecords(listOf(it)))
                            break
                        }
                    }
                }
            }
        }

        val activity = composeTestRule.activity
        val decorTextNode =
            composeTestRule.onNodeWithText(activity.getString(decorGroup.decorType.stringId()))
        decorTextNode.assertExists()
        decorTextNode.performClick()

        val pikminIdentifierViews = PikminType.values().map { pikminType ->
            val views =
                composeTestRule.onAllNodesWithText(activity.getString(pikminType.stringId()))
            (0 until views.fetchSemanticsNodes().size).map { index ->
                views[index]
            }
        }.flatten()

        (0..tapCount).forEach { _ ->
            pikminIdentifierViews.random().performClick()
            composeTestRule.waitForIdle()

            if (decorGroup.isCompleted()) {
                composeTestRule
                    .onNodeWithText(activity.getString(PikminStatusType.NotHave.stringId()))
                    .assertDoesNotExist()
            }

            decorGroup.forEach { costumeGroup ->
                val pikminCountTextTemplate =
                    activity.getString(R.string.pikmin_list_view_status)
                val pikminCountText = pikminCountTextTemplate.format(
                    activity.getString(costumeGroup.costumeType.stringId()),
                    costumeGroup.getHaveCount(),
                    costumeGroup.count(),
                )
                val pikminCountTextNode = composeTestRule.onNodeWithText(pikminCountText)
                pikminCountTextNode.assertExists()
            }
        }
    }
}
