package com.gblrod.orbvault.ui.countries.presentation.explore.random.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.gblrod.orbvault.R
import com.gblrod.orbvault.ui.shared.components.ActionButton
import com.gblrod.orbvault.ui.theme.ButtonNextRandomCountry
import com.gblrod.orbvault.ui.theme.ButtonReturnRandomCountry

@Composable
fun ButtonCountryRandom(
    onNextCountry: () -> Unit,
    onReturnCountry: () -> Unit,
    previewReturnCountry: Boolean
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ActionButton(
            modifier = Modifier.weight(1f),
            text = stringResource(id = R.string.button_return_random_country),
            icon = Icons.AutoMirrored.Filled.ArrowBack,
            onClick = onReturnCountry,
            color = ButtonReturnRandomCountry,
            horizontalArrangement = Arrangement.Start,
            previewReturnCountry = previewReturnCountry,
            border = if (!previewReturnCountry) {
                BorderStroke(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            } else null
        )

        ActionButton(
            modifier = Modifier.weight(1f),
            text = stringResource(id = R.string.button_next_random_country),
            icon = Icons.AutoMirrored.Filled.ArrowForward,
            onClick = onNextCountry,
            color = ButtonNextRandomCountry,
            horizontalArrangement = Arrangement.End,
            iconAtEnd = true
        )
    }
}