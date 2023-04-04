package com.oukoda.decopikmincompose.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oukoda.decopikmincompose.R
import com.oukoda.decopikmincompose.model.dataclass.CostumeGroup
import com.oukoda.decopikmincompose.model.dataclass.PikminIdentifier
import com.oukoda.decopikmincompose.model.enumclass.CostumeType
import com.oukoda.decopikmincompose.ui.theme.DecoPikminComposeTheme

@Composable
fun CostumeGroupView(
    costumeGroup: CostumeGroup,
    onClick: (costumeType: CostumeType, pikminIdentifier: PikminIdentifier) -> Unit,
) {
    val costumeName = stringResource(id = costumeGroup.costumeType.stringId)
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text(
            text = stringResource(id = R.string.pikmin_list_view_status).format(
                costumeName,
                costumeGroup.getHaveCount(),
                costumeGroup.count(),
            ),
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            for (pikminData in costumeGroup) {
                PikminIdentifierView(
                    pikminIdentifier = pikminData,
                    onClick = { onClick(costumeGroup.costumeType, it) },
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PikminListViewPreview() {
    DecoPikminComposeTheme {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            val pikminIdentifierListInternals: List<PikminIdentifier> =
                CostumeType.Acorn.pikminTypes().map {
                    PikminIdentifier.newInstance(
                        pikminType = it,
                        number = 0,
                    )
                }
            val costumeGroup = CostumeGroup(CostumeType.Acorn, pikminIdentifierListInternals)
            CostumeGroupView(
                costumeGroup = costumeGroup,
                onClick = { _: CostumeType, _: PikminIdentifier -> },
            )
        }
    }
}
