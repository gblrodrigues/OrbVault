package com.gblrod.orbvault.ui.countries.presentation.explore.comparison.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.gblrod.orbvault.data.countries.remote.dto.CountriesDto
import com.gblrod.orbvault.ui.theme.BlueActions

@Composable
fun CountrySelectionItem(
    country: CountriesDto,
    onClick: () -> Unit,
    onCountrySelected: Boolean,
    isDisabled: Boolean
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = { onClick() },
        enabled = !isDisabled,
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 6.dp),
        border = if (onCountrySelected) BorderStroke(
            width = 2.dp,
            color = BlueActions
        ) else null
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = country.flags.png,
                contentDescription = country.name.common,
                modifier = Modifier
                    .size(48.dp)
                    .clip(shape = RoundedCornerShape(16.dp))
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = country.name.common,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}