package com.gblrod.orbvault.ui.shared.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun ScreenHeader(
    modifier: Modifier = Modifier,
    primaryValue: String,
    secondValue: String,
    colorCustom: Color
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = primaryValue,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = secondValue,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = colorCustom
        )
    }
}