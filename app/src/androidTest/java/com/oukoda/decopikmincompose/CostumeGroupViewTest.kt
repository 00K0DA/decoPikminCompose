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
import com.oukoda.decopikmincompose.component.CostumeGroupView
import com.oukoda.decopikmincompose.model.dataclass.CostumeGroup
import com.oukoda.decopikmincompose.model.enumclass.CostumeType
import com.oukoda.decopikmincompose.model.enumclass.PikminType
import com.oukoda.decopikmincompose.ui.theme.DecoPikminComposeTheme
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class CostumeGroupViewTest {
    companion object {
        val TAG: String = CostumeGroupViewTest::class.simpleName!!
    }

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun textLabelAndCallbackTest3Pikmin() {
        textLabelAndCallbackTest(CostumeType.LeafHat)
    }

    @Test
    fun textLabelAndCallbackTest7Pikmin() {
        textLabelAndCallbackTest(CostumeType.Acorn)
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
                CostumeGroupView(rememberCostumeGroup) { costumeType, pikminIdentifier ->
                    rememberCostumeGroup = rememberCostumeGroup.updatePikminData(pikminIdentifier)
                    costumeGroup = costumeGroup.updatePikminData(pikminIdentifier)
                    assertEquals(costumeGroup.costumeType, costumeType)
                }
            }
        }
        val labelStringTemplate = activity.getString(R.string.pikmin_list_view_status)
        val pikminIdentifierViews = PikminType.values().map { pikminType ->
            val views =
                composeTestRule.onAllNodesWithText(activity.getString(pikminType.stringId))
            (0 until views.fetchSemanticsNodes().size).map { index ->
                views[index]
            }
        }.flatten()

        (0..5).forEach { _ ->
            pikminIdentifierViews.forEach { node ->
                val labelString = labelStringTemplate.format(
                    activity.getString(costumeGroup.costumeType.stringId),
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
