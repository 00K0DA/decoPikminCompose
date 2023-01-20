package com.oukoda.decopikmincompose

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.oukoda.decopikmincompose.component.CostumeGroupView
import com.oukoda.decopikmincompose.model.dataclass.CostumeGroup
import com.oukoda.decopikmincompose.model.dataclass.PikminIdentifier
import com.oukoda.decopikmincompose.model.enumclass.CostumeType
import com.oukoda.decopikmincompose.model.enumclass.PikminStatusType
import com.oukoda.decopikmincompose.model.enumclass.PikminType
import com.oukoda.decopikmincompose.ui.theme.DecoPikminComposeTheme
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CostumeGroupViewTest {
    companion object {
        val TAG: String = CostumeGroupViewTest::class.simpleName!!
    }

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var pikminIdentifierTemplate: PikminIdentifier

    @Before
    fun setUp() {
        pikminIdentifierTemplate = PikminIdentifier.newInstance(PikminType.Red, 0)
    }

    @Test
    fun textLabelAndCallbackTest0per1() {
        val costumeGroup = CostumeGroup(CostumeType.Acorn, listOf(pikminIdentifierTemplate))
        textLabelAndCallbackTest(costumeGroup)
    }

    @Test
    fun textLabelAndCallbackTest1per1() {
        val costumeGroup = CostumeGroup(
            CostumeType.Acorn,
            listOf(pikminIdentifierTemplate.copy(pikminStatusType = PikminStatusType.AlreadyExists)),
        )
        textLabelAndCallbackTest(costumeGroup)
    }

    @Test
    fun textLabelAndCallbackTest0per7() {
        val costumeGroup = CostumeGroup(
            CostumeType.Acorn,
            PikminType.values().map {
                pikminIdentifierTemplate.copy(pikminType = it)
            },
        )
        textLabelAndCallbackTest(costumeGroup)
    }

    @Test
    fun textLabelAndCallbackTest7per7() {
        val costumeGroup = CostumeGroup(
            CostumeType.Acorn,
            PikminType.values().map {
                pikminIdentifierTemplate.copy(
                    pikminType = it,
                    pikminStatusType = PikminStatusType.AlreadyExists,
                )
            },
        )
        textLabelAndCallbackTest(costumeGroup)
    }

    private fun textLabelAndCallbackTest(
        costumeGroup: CostumeGroup,
    ) {
        val targetPikminIdentifier = costumeGroup[0]
        composeTestRule.setContent {
            DecoPikminComposeTheme {
                CostumeGroupView(costumeGroup) { costumeType, pikminIdentifier ->
                    Log.d(
                        TAG,
                        "Callback!! costumeType=$costumeType, pi=$pikminIdentifier",
                    )
                    assertEquals(costumeGroup.costumeType, costumeType)
                    assertEquals(targetPikminIdentifier.statusUpdate(), pikminIdentifier)
                }
            }
        }
        val activity = composeTestRule.activity
        val labelStringTemplate = activity.getString(R.string.pikmin_list_view_status)
        val labelString = labelStringTemplate.format(
            activity.getString(costumeGroup.costumeType.stringId()),
            costumeGroup.getHaveCount(),
            costumeGroup.count(),
        )
        Log.d(TAG, "textLabelAndCallbackTest: $labelString")
        val labelNode = composeTestRule.onNodeWithText(labelString)
        labelNode.assertExists()

        val pikminViewNode = composeTestRule.onNodeWithText(
            activity.getString(
                targetPikminIdentifier.pikminType.stringId(),
            ),
        )
        pikminViewNode.assertExists()
        pikminViewNode.performClick()
        composeTestRule.waitForIdle()
    }
}
