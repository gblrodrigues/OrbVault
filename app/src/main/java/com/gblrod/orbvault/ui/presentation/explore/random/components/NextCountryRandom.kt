package com.gblrod.orbvault.ui.presentation.explore.random.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.gblrod.orbvault.R
import com.gblrod.orbvault.ui.theme.ButtonBackExploreScreen

@Composable
fun NextCountryRandom(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Spacer(modifier = Modifier.height(16.dp))

    Button(
        onClick = { onClick() },
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(
            width = 2.dp,
            color = MaterialTheme.colorScheme.onSurface
        ),
        colors = ButtonDefaults.buttonColors(
            containerColor = ButtonBackExploreScreen
        ),
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(0.5f),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.button_next_random_country),
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.width(8.dp))

            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}