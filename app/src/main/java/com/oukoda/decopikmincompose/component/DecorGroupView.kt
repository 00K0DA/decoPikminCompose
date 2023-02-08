package com.oukoda.decopikmincompose.component

import android.media.Image
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oukoda.decopikmincompose.R
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
    var isExpand by rememberSaveable {
        mutableStateOf(false)
    }

    var decorGroupInternal by remember {
        mutableStateOf(decorGroup)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        DecorTitleView(decorGroupInternal, isExpand) {
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
            CostumeGroupViewHolder(decorGroup = decorGroupInternal) { pikminRecord: PikminRecord, newCostumeGroup: CostumeGroup ->
                decorGroupInternal = decorGroupInternal.updateCostumeGroup(newCostumeGroup)
                onClick(pikminRecord)
            }
        }
    }
}

@Composable
private fun DecorTitleView(
    decorGroup: DecorGroup,
    isExpand: Boolean,
    onClick: () -> Unit,
) {
    val imageId = if (isExpand) R.drawable.expand_less else R.drawable.expand_more
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .clickable { onClick() },
        elevation = 8.dp,
    ) {
        Box {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(16.dp)
                        .background(if (decorGroup.isCompleted()) completeColor else progressColor),
                )
                Text(
                    modifier = Modifier
                        .padding(start = 4.dp),
                    text = "%d/%d".format(decorGroup.getHaveCount(), decorGroup.getCount()),
                    fontSize = 16.sp,
                )
            }
            Text(
                modifier = Modifier
                    .align(Alignment.Center),
                text = stringResource(id = decorGroup.decorType.stringId()),
                fontSize = 20.sp,
            )
            Image(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 8.dp),
                painter = painterResource(id = imageId),
                contentDescription = null,
                colorFilter = ColorFilter.tint(MaterialTheme.colors.onPrimary),
            )
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
