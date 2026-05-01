package com.gblrod.orbvault.ui.countries.presentation.explore.news.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gblrod.orbvault.ui.countries.presentation.explore.news.model.NewItem

@Composable
fun NewsGrid(
    items: List<NewItem>
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(items) { item ->
            NewsItems(
                modifier = Modifier.width(150.dp),
                icon = item.icon,
                totalValue = item.value,
                descValue = item.label,
                onClick = item.onClick,
                backgroundColor = item.backgroundColor,
                iconColor = item.iconColor
            )
        }
    }
}