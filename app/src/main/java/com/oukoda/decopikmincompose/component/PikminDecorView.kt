package com.oukoda.decopikmincompose.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oukoda.decopikmincompose.model.dataclass.PikminCostumeList
import com.oukoda.decopikmincompose.model.dataclass.PikminData
import com.oukoda.decopikmincompose.model.dataclass.PikminDataList
import com.oukoda.decopikmincompose.model.enumclass.DecorType
import com.oukoda.decopikmincompose.ui.theme.DecoPikminComposeTheme
import com.oukoda.decopikmincompose.ui.theme.completeColor
import com.oukoda.decopikmincompose.ui.theme.progressColor


@Composable
fun PikminDecorView(
    pikminCostumeList: PikminCostumeList
) {
    var isExpand by remember {
        mutableStateOf(false)
    }

    var pikminCostumeListInternal by remember {
        mutableStateOf(pikminCostumeList)
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
                    shape = RoundedCornerShape(24.dp)
                )
                .clickable { isExpand = !isExpand }
                .height(48.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                stringResource(id = pikminCostumeListInternal.decorType.getDecorText()),
                fontSize = 20.sp
            )
        }
        AnimatedVisibility(
            visible = isExpand,
            enter = expandVertically(
                expandFrom = Alignment.Top,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioLowBouncy,
                    stiffness = Spring.StiffnessMedium,
                    visibilityThreshold = IntSize.VisibilityThreshold
                )
            ),
            exit = shrinkVertically()
        ) {
            Column() {
                pikminCostumeListInternal.forEach { pikminDataList ->
                    PikminListView(pikminDataList, onClick = {
                        val newPikminDataList = pikminDataList.updatePikminData(it)
                        pikminCostumeListInternal =
                            pikminCostumeListInternal.updatePikminDataList(newPikminDataList)

                    })
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun PikminDecorViewPreview() {
    val decorType: DecorType = DecorType.Restaurant
    val pikminDataLists = decorType.getCostumes().map { costumeType ->
        PikminDataList(costumeType = costumeType, pikminDataList =
        costumeType.getPikminList().map { pikminType ->
            PikminData.newInstance(pikminType, 0)
        })
    }
    val pikminCostumeList =
        PikminCostumeList(decorType = decorType, pikminDataLists = pikminDataLists)
    DecoPikminComposeTheme {
        PikminDecorView(pikminCostumeList)
    }
}