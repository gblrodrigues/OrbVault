package com.gblrod.orbvault.ui.countries.presentation.explore.random.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun ButtonCountryRandomDetails(
    modifier: Modifier = Modifier,
    value: String,
    icon: ImageVector,
    onClick: () -> Unit,
    horizontalArrangement: Arrangement.Horizontal,
    colors: Color,
    previewReturnCountry: Boolean = true,
    iconAtEnd: Boolean = false
) {
    Button(
        onClick = onClick,
        enabled = previewReturnCountry,
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(
            width = 2.dp,
            color = MaterialTheme.colorScheme.onSurface
        ),
        colors = ButtonDefaults.buttonColors(
            containerColor = colors
        ),
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = horizontalArrangement,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (!iconAtEnd) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.width(8.dp))
            }

            Text(
                text = value,
                color = MaterialTheme.colorScheme.onSurface
            )

            if (iconAtEnd) {
                Spacer(modifier = Modifier.width(8.dp))

                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}