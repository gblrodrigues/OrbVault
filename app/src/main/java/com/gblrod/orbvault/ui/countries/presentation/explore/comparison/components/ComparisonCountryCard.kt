package com.gblrod.orbvault.ui.countries.presentation.explore.comparison.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.gblrod.orbvault.R
import com.gblrod.orbvault.data.countries.remote.dto.CountriesDto
import com.gblrod.orbvault.ui.theme.BlueActions

@Composable
fun ComparisonCountryCard(
    country: CountriesDto?,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(150.dp)
            .height(150.dp)
            .clickable { onClick() }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (country == null) {
                IconButton(
                    onClick = { onClick() },
                    shape = CircleShape,
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = BlueActions
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = stringResource(id = R.string.comparison_select),
                    color = MaterialTheme.colorScheme.onSurface
                )

            } else {
                AsyncImage(
                    model = country.flags.png,
                    contentDescription = country.name.common,
                    modifier = Modifier
                        .size(56.dp)
                        .clip(shape = RoundedCornerShape(16.dp))
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = country.name.common,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}