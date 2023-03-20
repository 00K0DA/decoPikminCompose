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
import com.oukoda.decopikmincompose.component.DecorGroupView
import com.oukoda.decopikmincompose.model.dataclass.DecorGroup
import com.oukoda.decopikmincompose.model.room.entity.PikminRecord

@Composable
fun NormalScreen(
    allDecors: List<DecorGroup>,
    showDecors: List<DecorGroup>,
    showCompleteDecor: Boolean,
    onClick: (pikminRecord: PikminRecord) -> Unit,
    onSwitchChanged: (showCompleteDecor: Boolean) -> Unit,
) {
    val listState = rememberLazyListState()
    LazyColumn(
        modifier = Modifier.testTag("normal_screen_lazy_column"),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        state = listState,
    ) {
        item {
            Spacer(modifier = Modifier.height(16.dp))
            AllPikminInfoView(
                allDecorGroups = allDecors,
                showCompleteDecorType = showCompleteDecor,
            ) {
                onSwitchChanged(it)
            }
        }
        items(showDecors) { decor ->
            DecorGroupView(decor, onClick)
        }

        item {
            Spacer(modifier = Modifier.height(96.dp))
        }
    }
}
