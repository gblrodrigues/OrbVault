package com.gblrod.orbvault.ui.weather.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.gblrod.orbvault.data.dto.countries.CountriesDto
import com.gblrod.orbvault.ui.shared.components.FavoriteButton
import com.gblrod.orbvault.ui.shared.components.TopList

@Composable
fun WeatherHeader(
    country: CountriesDto,
    isFavorite: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = country.flags.png,
            contentDescription = null,
            modifier = Modifier
                .size(72.dp)
                .clip(shape = RoundedCornerShape(16.dp))
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            TopList(
                country = country.name.common,
                official = country.name.official
            )
        }
        FavoriteButton(
            modifier = Modifier.align(Alignment.Top),
            isFavorite = isFavorite,
            onClick = { onClick() }
        )
    }
}