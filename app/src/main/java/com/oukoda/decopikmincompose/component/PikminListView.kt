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
import com.oukoda.decopikmincompose.model.dataclass.PikminData
import com.oukoda.decopikmincompose.model.dataclass.PikminDataList
import com.oukoda.decopikmincompose.model.enumclass.CostumeType
import com.oukoda.decopikmincompose.ui.theme.DecoPikminComposeTheme

@Composable
fun PikminListView(
    pikminDataList: PikminDataList,
    onClick: (pikminData: PikminData) -> Unit,
) {
    val costumeName = stringResource(id = pikminDataList.costumeType.getCostumeTextId())
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = stringResource(id = R.string.pikmin_list_view_status).format(
                costumeName,
                pikminDataList.getHaveCount(),
                pikminDataList.count(),
            ),
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            for (pikminData in pikminDataList) {
                PikminView(
                    pikminData = pikminData,
                    onClick = { onClick(it) },
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
            val pikminDataListInternal: List<PikminData> =
                CostumeType.Acorn.getPikminList().map {
                    PikminData.newInstance(
                        pikminType = it,
                        number = 0,
                    )
                }
            val pikminDataList = PikminDataList(CostumeType.Acorn, pikminDataListInternal)
            PikminListView(
                pikminDataList = pikminDataList,
                onClick = {},
            )
        }
    }
}
