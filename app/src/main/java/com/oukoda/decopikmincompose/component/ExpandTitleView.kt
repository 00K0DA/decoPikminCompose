package com.oukoda.decopikmincompose.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oukoda.decopikmincompose.R
import com.oukoda.decopikmincompose.ui.theme.completeColor
import com.oukoda.decopikmincompose.ui.theme.progressColor

@Composable
fun ExpandTitleView(
    title: String,
    hasCount: Int,
    allCount: Int,
    isExpand: Boolean,
    onClick: () -> Unit,
) {
    val imageId = if (isExpand) R.drawable.expand_less else R.drawable.expand_more
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .clickable { onClick() },
        elevation = 8.dp,
    ) {
        Box {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(16.dp)
                        .background(if (allCount == hasCount) completeColor else progressColor),
                )
                Text(
                    modifier = Modifier
                        .padding(start = 4.dp),
                    text = stringResource(id = R.string.expand_title_view_count_template).format(
                        hasCount,
                        allCount,
                    ),
                    fontSize = 16.sp,
                )
            }
            Text(
                modifier = Modifier
                    .align(Alignment.Center),
                text = title,
                fontSize = 20.sp,
            )
            Image(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 8.dp),
                painter = painterResource(id = imageId),
                contentDescription = null,
                colorFilter = ColorFilter.tint(MaterialTheme.colors.onSurface),
            )
        }
    }
}
