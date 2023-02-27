package com.oukoda.decopikmincompose.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.oukoda.decopikmincompose.model.dataclass.CostumeGroup
import com.oukoda.decopikmincompose.model.enumclass.DecorType
import com.oukoda.decopikmincompose.model.room.entity.PikminRecord

@Composable
fun SpecialCostumeGroupView(
    costumeGroup: CostumeGroup,
    onClick: (pikminRecord: PikminRecord) -> Unit,
) {
    var isExpand by rememberSaveable {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ExpandTitleView(
            title = stringResource(id = costumeGroup.costumeType.stringId()),
            allCount = costumeGroup.count(),
            hasCount = costumeGroup.getHaveCount(),
            isExpand = isExpand,
        ) {
            isExpand = !isExpand
        }
        AnimatedVisibility(
            modifier = Modifier.padding(horizontal = 4.dp),
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
            Card(
                elevation = 2.dp,
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, bottom = 4.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    for (pikminData in costumeGroup) {
                        PikminIdentifierView(
                            pikminIdentifier = pikminData,
                            onClick = {
                                onClick(
                                    it.toPikminRecord(DecorType.Special, costumeGroup.costumeType),
                                )
                            },
                        )
                    }
                }
            }
        }
    }
}
