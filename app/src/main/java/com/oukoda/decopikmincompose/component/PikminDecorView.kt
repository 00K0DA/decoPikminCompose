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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oukoda.decopikmincompose.model.CostumeType
import com.oukoda.decopikmincompose.model.DecorType
import com.oukoda.decopikmincompose.model.PikminData
import com.oukoda.decopikmincompose.ui.theme.DecoPikminComposeTheme
import com.oukoda.decopikmincompose.ui.theme.completeColor
import com.oukoda.decopikmincompose.ui.theme.redPikminColor


@Composable
fun PikminDecorView(

) {
    var isExpand by remember {
        mutableStateOf(false)
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
                    color = completeColor,
                    shape = RoundedCornerShape(24.dp)
                )
                .clickable { isExpand = !isExpand }
                .height(48.dp),
            contentAlignment = Alignment.Center
        ) {
            Text("aaaa", fontSize = 20.sp)
        }
        val pikminDataList: List<PikminData> =
            CostumeType.getPikminList(CostumeType.Acorn).map {
                PikminData.newInstance(
                    decorType = DecorType.Forest,
                    costumeType = CostumeType.Acorn,
                    pikminType = it,
                    number = 0,
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
                PikminListView(
                    costumeType = CostumeType.Acorn,
                    pikminDataList = pikminDataList,
                    onClick = {pikminData, haveCount ->  })
                PikminListView(
                    costumeType = CostumeType.Acorn,
                    pikminDataList = pikminDataList,
                    onClick = {pikminData, haveCount ->  })
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun PikminDecorViewPreview() {
    DecoPikminComposeTheme {
        PikminDecorView()
    }
}