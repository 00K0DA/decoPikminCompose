package com.oukoda.decopikmincompose.component

import android.util.Log
import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.oukoda.decopikmincompose.R
import com.oukoda.decopikmincompose.model.CostumeType
import com.oukoda.decopikmincompose.model.DecorType
import com.oukoda.decopikmincompose.model.PikminData
import com.oukoda.decopikmincompose.model.PikminDataList
import com.oukoda.decopikmincompose.ui.theme.DecoPikminComposeTheme

@Composable
fun PikminListView(
    pikminDataList: PikminDataList,
    onClick: (pikminData: PikminData) -> Unit
) {
    var pikminDataListInternal by remember {
        mutableStateOf(pikminDataList)
    }

    PikminListViewInternal(
        pikminDataList = pikminDataListInternal,
        onClick = { updatePikminData ->
            pikminDataListInternal = pikminDataListInternal.updatePikminData(updatePikminData)
            onClick(updatePikminData)
        },
    )
}

@Composable
@VisibleForTesting
private fun PikminListViewInternal(
    pikminDataList: PikminDataList,
    onClick: (pikminData: PikminData) -> Unit
) {
    val costumeName = stringResource(id = pikminDataList.costumeType.getCostumeTextId())
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.pikmin_list_view_status).format(
                costumeName,
                pikminDataList.getHaveCount(),
                pikminDataList.count()
            )
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            for (pikminData in pikminDataList) {
                PikminView(
                    pikminData = pikminData,
                    onClick = { onClick(it) }
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
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            val pikminDataListInternal: List<PikminData> =
                CostumeType.getPikminList(CostumeType.Acorn).map {
                    PikminData.newInstance(
                        decorType = DecorType.Forest,
                        pikminType = it,
                        number = 0,
                    )
                }
            val pikminDataList = PikminDataList(CostumeType.Acorn, pikminDataListInternal)
            PikminListView(
                pikminDataList = pikminDataList,
                onClick = {
                    Log.d("pikminViewList", "PikminListViewPreview: $it")
                })
        }
    }
}