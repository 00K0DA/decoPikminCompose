package com.oukoda.decopikmincompose.component

import android.util.Log
import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oukoda.decopikmincompose.model.dataclass.PikminIdentifier
import com.oukoda.decopikmincompose.model.enumclass.PikminStatusType
import com.oukoda.decopikmincompose.model.enumclass.PikminType
import com.oukoda.decopikmincompose.ui.theme.DecoPikminComposeTheme

private val pikminIdentifierViewWidth: Dp = 40.dp
private val roundedCornerShape: Dp = 10.dp

@Composable
fun PikminIdentifierView(
    pikminIdentifier: PikminIdentifier,
    onClick: (pikminIdentifier: PikminIdentifier) -> Unit,
) {
    Column(
        modifier = Modifier.width(pikminIdentifierViewWidth),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(roundedCornerShape))
                .background(pikminIdentifier.pikminType.color())
                .clickable { onClick(pikminIdentifier.statusUpdate()) },
            contentAlignment = Alignment.Center,
        ) {
            PikminTypeText(pikminType = pikminIdentifier.pikminType)
        }
        PikminStatusText(pikminStatusType = pikminIdentifier.pikminStatusType)
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
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            for (pikminType in PikminType.values()) {
                val pikminIdentifier = PikminIdentifier.newInstance(
                    pikminType = pikminType,
                    number = 0,
                )
                PikminIdentifierView(
                    pikminIdentifier = pikminIdentifier,
                    onClick = { Log.d("PikminViewPreview", "PikminViewPreview: onClick") },
                )
            }
        }
    }
}
