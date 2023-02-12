package com.oukoda.decopikmincompose.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.oukoda.decopikmincompose.model.dataclass.CostumeGroup
import com.oukoda.decopikmincompose.model.dataclass.DecorGroup
import com.oukoda.decopikmincompose.model.dataclass.PikminIdentifier
import com.oukoda.decopikmincompose.model.enumclass.CostumeType
import com.oukoda.decopikmincompose.model.enumclass.DecorType
import com.oukoda.decopikmincompose.model.room.entity.PikminRecord
import com.oukoda.decopikmincompose.ui.theme.DecoPikminComposeTheme

@Composable
fun DecorGroupView(
    decorGroup: DecorGroup,
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
            title = stringResource(id = decorGroup.decorType.stringId()),
            allCount = decorGroup.getCount(),
            hasCount = decorGroup.getHaveCount(),
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
            CostumeGroupViewHolder(decorGroup = decorGroup) { pikminRecord: PikminRecord, _: CostumeGroup ->
                onClick(pikminRecord)
            }
        }
    }
}

@Composable
private fun CostumeGroupViewHolder(
    decorGroup: DecorGroup,
    onClick: (pikminRecord: PikminRecord, updateCostumeGroup: CostumeGroup) -> Unit,
) {
    Card(
        elevation = 2.dp,
        modifier = Modifier.padding(top = 8.dp),
    ) {
        Column(
            modifier = Modifier.padding(vertical = 4.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            decorGroup.forEach { pikminIdentifiers ->
                CostumeGroupView(
                    pikminIdentifiers,
                    onClick = { costumeType: CostumeType, pikminIdentifier: PikminIdentifier ->
                        val pikminRecord =
                            pikminIdentifier.toPikminRecord(decorGroup.decorType, costumeType)
                        val newCostumeGroup = pikminIdentifiers.updatePikminData(pikminIdentifier)
                        onClick(pikminRecord, newCostumeGroup)
                    },
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PikminDecorViewPreview() {
    val decorType: DecorType = DecorType.Restaurant
    val pikminIdentifierLists = decorType.costumeTypes().map { costumeType ->
        CostumeGroup(
            costumeType = costumeType,
            pikminIdentifiers =
            costumeType.pikminTypes().map { pikminType ->
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
