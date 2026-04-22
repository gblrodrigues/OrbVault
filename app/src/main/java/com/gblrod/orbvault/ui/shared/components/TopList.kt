package com.gblrod.orbvault.ui.shared.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp

@Composable
fun TopList(
    country: String,
    official: String,
    index: Int? = null
) {
    Column {
        Text(
            text = if (index != null) "${index}. $country" else country,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 19.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = official,
            fontSize = 13.sp,
            color = MaterialTheme.colorScheme.onSurface,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}