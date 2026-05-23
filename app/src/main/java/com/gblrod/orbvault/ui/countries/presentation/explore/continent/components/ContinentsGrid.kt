package com.gblrod.orbvault.ui.countries.presentation.explore.continent.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.gblrod.orbvault.ui.countries.presentation.explore.continent.model.ContinentItem

@Composable
fun ContinentsGrid(
    items: List<ContinentItem>
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(items) { item ->
            ContinentItems(
                image = item.image,
                color = item.color,
                descValue = item.label,
                totalValue = item.value,
                onClick = item.onClick
            )
        }
    }
}