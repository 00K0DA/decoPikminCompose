package com.oukoda.decopikmincompose.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.oukoda.decopikmincompose.component.SpecialCostumeGroupView
import com.oukoda.decopikmincompose.model.dataclass.DecorGroup
import com.oukoda.decopikmincompose.model.room.entity.PikminRecord

@Composable
fun SpecialScreen(
    specialDecorGroup: DecorGroup,
    onClick: (pikminRecord: PikminRecord) -> Unit,
) {
    val listState = rememberLazyListState()
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        state = listState,
    ) {
        itemsIndexed(specialDecorGroup) { index, costumeGroup ->
            if (index == 0) {
                Spacer(modifier = Modifier.height(32.dp))
            }
            SpecialCostumeGroupView(
                costumeGroup,
            ) {
                onClick(it)
            }
            if (index == specialDecorGroup.size - 1) {
                Spacer(modifier = Modifier.height(96.dp))
            }
        }
    }
}
