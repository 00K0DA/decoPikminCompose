package com.oukoda.decopikmincompose.component

import android.util.Log
import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oukoda.decopikmincompose.model.*
import com.oukoda.decopikmincompose.ui.theme.DecoPikminComposeTheme

@Composable
fun PikminView(
    pikminData: PikminData,
    onClick: (pikminData: PikminData) -> Unit,
) {
    var pikminStatusType by remember {
        mutableStateOf(pikminData.pikminStatusType)
    }

    PikminViewInternal(pikminType = pikminData.pikminType, pikminStatusType = pikminStatusType) {
        pikminStatusType = pikminStatusType.update()
        val newPikminData = pikminData.copy(pikminStatusType = pikminStatusType)
        onClick(newPikminData)
    }
}

private val pikminViewWidth: Dp = 40.dp
private val roundedCornerShape: Dp = 10.dp

@Composable
@VisibleForTesting
private fun PikminViewInternal(
    pikminType: PikminType,
    pikminStatusType: PikminStatusType,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier.width(pikminViewWidth),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(roundedCornerShape))
                .background(pikminType.color())
                .clickable { onClick() },
            contentAlignment = Alignment.Center
        ) {
            PikminTypeText(pikminType = pikminType)
        }
        PikminStatusText(pikminStatusType = pikminStatusType)
    }
}

@Composable
@VisibleForTesting
private fun PikminTypeText(pikminType: PikminType) {
    Text(
        text = stringResource(id = pikminType.stringId()),
        fontSize = 12.sp,
        color = Color.White,
    )
}

@Composable
@VisibleForTesting
private fun PikminStatusText(pikminStatusType: PikminStatusType) {
    Text(
        text = stringResource(id = pikminStatusType.stringId()),
        fontSize = 12.sp,
        color = pikminStatusType.stringColor(),
    )
}

@Preview(showBackground = true)
@Composable
private fun PikminViewPreview() {
    DecoPikminComposeTheme {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            for (pikminType in PikminType.values()) {
                val pikminData = PikminData.newInstance(
                    decorType = DecorType.Airport,
                    costumeType = CostumeType.ToyAirPlane,
                    pikminType = pikminType,
                    number = 0,
                )
                PikminView(
                    pikminData = pikminData,
                    onClick = { Log.d("aaa", "PikminViewPreview: onClick") }
                )
            }
        }
    }
}