package com.oukoda.decopikmincompose.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.oukoda.decopikmincompose.component.AllPikminInfoView
import com.oukoda.decopikmincompose.component.SpecialCostumeGroupView
import com.oukoda.decopikmincompose.model.dataclass.DecorGroup
import com.oukoda.decopikmincompose.model.room.entity.PikminRecord

@Composable
fun SpecialScreen(
    allSpecialDecorGroup: DecorGroup,
    specialDecorGroup: DecorGroup,
    showCompleteCostume: Boolean,
    onClick: (pikminRecord: PikminRecord) -> Unit,
    onSwitchChanged: (showCompleteCostume: Boolean) -> Unit,
) {
    val listState = rememberLazyListState()
    LazyColumn(
        modifier = Modifier.testTag("special_screen_lazy_column"),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        state = listState,
    ) {
        item {
            Spacer(modifier = Modifier.height(16.dp))
            AllPikminInfoView(
                allDecorGroups = listOf(allSpecialDecorGroup),
                showCompleteDecorType = showCompleteCostume,
            ) {
                onSwitchChanged(it)
            }
        }

        items(specialDecorGroup) { costumeGroup ->
            SpecialCostumeGroupView(
                costumeGroup,
            ) {
                onClick(it)
            }
        }

        item {
            Spacer(modifier = Modifier.height(96.dp))
        }
    }
}
