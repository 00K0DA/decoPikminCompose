package com.oukoda.decopikmincompose.component

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.oukoda.decopikmincompose.model.enumclass.BottomItems

@Composable
fun BottomBar(nowRoute: String, onTap: (BottomItems) -> Unit) {
    BottomNavigation(elevation = 10.dp) {
        for (bottomItem in BottomItems.values()) {
            BottomNavigationItem(
                selected = nowRoute == bottomItem.route(),
                onClick = { onTap(bottomItem) },
                label = { Text(stringResource(id = bottomItem.stringId())) },
                icon = {
                    Icon(
                        imageVector = bottomItem.imageVector(),
                        contentDescription = bottomItem.route(),
                    )
                },
            )
        }
    }
}
