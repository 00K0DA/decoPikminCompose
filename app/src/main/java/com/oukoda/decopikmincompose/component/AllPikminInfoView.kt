package com.oukoda.decopikmincompose.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oukoda.decopikmincompose.model.dataclass.CostumeGroup
import com.oukoda.decopikmincompose.model.dataclass.DecorGroup
import com.oukoda.decopikmincompose.model.dataclass.PikminIdentifier
import com.oukoda.decopikmincompose.model.enumclass.DecorType
import com.oukoda.decopikmincompose.ui.theme.DecoPikminComposeTheme

@Composable
fun AllPikminInfoView(
    allDecorGroups: List<DecorGroup>,
    showCompleteDecorType: Boolean,
    onChanged: (showCompleteDecorType: Boolean) -> Unit,
) {
    val allPikminCount = allDecorGroups.sumOf { it.getCount() }
    val alreadyHaveCount = allDecorGroups.sumOf { it.getHaveCount() }
    val haveCountPercentage: Float = alreadyHaveCount.toFloat() / allPikminCount
    Box(
        Modifier
            .clip(RoundedCornerShape(24.dp))
            .padding(horizontal = 16.dp)
            .border(
                width = 2.dp,
                color = Color.Gray,
                shape = RoundedCornerShape(24.dp),
            ),
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(start = 16.dp)
                .padding(vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            InfoRowWithText(
                label = "所持数: ",
                data = "%d/%d".format(alreadyHaveCount, allPikminCount),
            )
            InfoRowWithText(
                label = "所持率: ",
                data = "%.2f".format(haveCountPercentage),
            )
            InfoRowWithCheckbox(
                label = "コンプ済みは表示しない",
                isSelected = showCompleteDecorType,
                onChanged = { onChanged(it) },
            )
        }
    }
}

@Composable
private fun InfoRowWithText(label: String, data: String) {
    InfoRowInternal(label = label, valueWidget = { Text(text = data) })
}

@Composable
private fun InfoRowWithCheckbox(
    label: String,
    isSelected: Boolean,
    onChanged: (isSelected: Boolean) -> Unit,
) {
    InfoRowInternal(
        label = label,
        valueWidget = {
            Checkbox(
                modifier = Modifier.height(4.dp),
                checked = isSelected,
                onCheckedChange = {
                    onChanged(!isSelected)
                },
            )
        },
    )
}

@Composable
private fun InfoRowInternal(label: String, valueWidget: @Composable () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(modifier = Modifier.width(240.dp), text = label)
        valueWidget()
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
        AllPikminInfoView(listOf(decorGroup), true) { }
    }
}