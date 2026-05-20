package com.gblrod.orbvault.ui.countries.presentation.explore.comparison.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.gblrod.orbvault.data.countries.remote.dto.CountriesDto

@Composable
fun ComparisonHeader(
    country: CountriesDto
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = country.flags.png,
            contentDescription = country.name.common,
            modifier = Modifier
                .size(48.dp)
                .clip(shape = RoundedCornerShape(16.dp))
        )
        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = country.cca3,
            textAlign = TextAlign.Center
        )
    }
}