package com.oukoda.decopikmincompose

import androidx.activity.ComponentActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.oukoda.decopikmincompose.component.SpecialCostumeGroupView
import com.oukoda.decopikmincompose.model.dataclass.CostumeGroup
import com.oukoda.decopikmincompose.model.enumclass.CostumeType
import com.oukoda.decopikmincompose.model.enumclass.PikminType
import com.oukoda.decopikmincompose.ui.theme.DecoPikminComposeTheme
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class SpecialCostumeGroupView {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun specialTextLabelAndCallbackTest1Pikmin() {
        textLabelAndCallbackTest(CostumeType.Mario)
    }

    @Test
    fun specialTextLabelAndCallbackTest3Pikmin() {
        textLabelAndCallbackTest(CostumeType.NewYear2023)
    }

    @Test
    fun specialTextLabelAndCallbackTest7Pikmin() {
        textLabelAndCallbackTest(CostumeType.FirstAnniversary)
    }

    private fun textLabelAndCallbackTest(
        costumeType: CostumeType,
    ) {
        val activity = composeTestRule.activity
        var costumeGroup = CostumeGroup.newInstance(costumeType)
        composeTestRule.setContent {
            DecoPikminComposeTheme {
                var rememberCostumeGroup by remember {
                    mutableStateOf(costumeGroup)
                }
                SpecialCostumeGroupView(rememberCostumeGroup) { pikminRecord ->
                    rememberCostumeGroup =
                        rememberCostumeGroup.updateByPikminRecords(listOf(pikminRecord))
                    costumeGroup = costumeGroup.updateByPikminRecords(listOf(pikminRecord))
                    Assert.assertEquals(costumeGroup.costumeType, costumeType)
                }
            }
        }

        val costumeGroupLabel = activity.getString(costumeType.stringId())
        composeTestRule.onNodeWithText(costumeGroupLabel).assertExists().performClick()

        val pikminIdentifierViews = PikminType.values().map { pikminType ->
            val views =
                composeTestRule.onAllNodesWithText(activity.getString(pikminType.stringId()))
            (0 until views.fetchSemanticsNodes().size).map { index ->
                views[index]
            }
        }.flatten()

        (0..5).forEach { _ ->
            pikminIdentifierViews.forEach { node ->
                val labelString =
                    activity.getString(R.string.expand_title_view_count_template).format(
                        costumeGroup.getHaveCount(),
                        costumeGroup.count(),
                    )
                val labelNode = composeTestRule.onNodeWithText(labelString)
                labelNode.assertExists()

                node.assertExists()
                node.performClick()
            }
        }
    }
}
