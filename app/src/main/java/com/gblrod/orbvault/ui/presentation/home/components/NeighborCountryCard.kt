package com.gblrod.orbvault.ui.presentation.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.gblrod.orbvault.data.dto.CountriesDto

@Composable
fun NeighborCountryCard(
    modifier: Modifier = Modifier,
    country: CountriesDto,
    onCountryClick: (String) -> Unit,
    countryQuery: (String) -> Unit,
) {
    Card(
        modifier = modifier
            .width(160.dp)
            .clickable {
                if (!country.cca3.isNullOrBlank()) {
                    onCountryClick(country.cca3)
                    countryQuery(country.name.common)
                }
            },
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = country.flags.png,
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .clip(shape = RoundedCornerShape(12.dp))
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = country.name.common,
                fontSize = 14.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}