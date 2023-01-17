package com.oukoda.decopikmincompose.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oukoda.decopikmincompose.model.dataclass.CostumeGroup
import com.oukoda.decopikmincompose.model.dataclass.DecorGroup
import com.oukoda.decopikmincompose.model.dataclass.PikminIdentifier
import com.oukoda.decopikmincompose.model.enumclass.CostumeType
import com.oukoda.decopikmincompose.model.enumclass.DecorType
import com.oukoda.decopikmincompose.model.room.entity.PikminRecord
import com.oukoda.decopikmincompose.ui.theme.DecoPikminComposeTheme
import com.oukoda.decopikmincompose.ui.theme.completeColor
import com.oukoda.decopikmincompose.ui.theme.progressColor

@Composable
fun DecorGroupView(
    decorGroup: DecorGroup,
    onClick: (pikminRecord: PikminRecord) -> Unit,
) {
    var isExpand by remember {
        mutableStateOf(false)
    }

    var pikminCostumeListInternal by remember {
        mutableStateOf(decorGroup)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .border(
                    width = 2.dp,
                    color = if (pikminCostumeListInternal.isCompleted()) completeColor else progressColor,
                    shape = RoundedCornerShape(24.dp),
                )
                .clickable { isExpand = !isExpand }
                .height(48.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                stringResource(id = pikminCostumeListInternal.decorType.getDecorText()),
                fontSize = 20.sp,
            )
        }
        AnimatedVisibility(
            visible = isExpand,
            enter = expandVertically(
                expandFrom = Alignment.Top,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioLowBouncy,
                    stiffness = Spring.StiffnessMedium,
                    visibilityThreshold = IntSize.VisibilityThreshold,
                ),
            ),
            exit = shrinkVertically(),
        ) {
            Column {
                pikminCostumeListInternal.forEach { pikminDataList ->
                    CostumeGroupView(
                        pikminDataList,
                        onClick = { costumeType: CostumeType, pikminIdentifier: PikminIdentifier ->
                            val newPikminIdentifiers =
                                pikminDataList.updatePikminData(pikminIdentifier)
                            pikminCostumeListInternal =
                                pikminCostumeListInternal.updatePikminIdentifiers(
                                    newPikminIdentifiers,
                                )
                            onClick(
                                pikminIdentifier.toPikminRecord(
                                    decorGroup.decorType,
                                    costumeType,
                                ),
                            )
                        },
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PikminDecorViewPreview() {
    val decorType: DecorType = DecorType.Restaurant
    val pikminIdentifierLists = decorType.getCostumes().map { costumeType ->
        CostumeGroup(
            costumeType = costumeType,
            pikminIdentifiers =
            costumeType.getPikminList().map { pikminType ->
                PikminIdentifier.newInstance(pikminType, 0)
            },
        )
    }
    val decorGroup =
        DecorGroup(decorType = decorType, costumeGroups = pikminIdentifierLists)
    DecoPikminComposeTheme {
        DecorGroupView(decorGroup) {}
    }
}
