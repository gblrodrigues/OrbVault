package com.gblrod.orbvault.ui.presentation.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.gblrod.orbvault.R
import com.gblrod.orbvault.data.mapper.getCurrency
import com.gblrod.orbvault.data.mapper.getLanguage
import com.gblrod.orbvault.data.mapper.getTimezones
import com.gblrod.orbvault.ui.presentation.state.CountriesUiState
import com.gblrod.orbvault.ui.presentation.home.viewmodel.CountriesViewModel

@Composable
fun CardCountryDetails(
    countriesViewModel: CountriesViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by countriesViewModel.countriesUiState.collectAsState()
    val country = (uiState as CountriesUiState.Success).country
    var favorite by remember { mutableStateOf(false) }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        content = {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    content = {
                        AsyncImage(
                            model = country.flags.png,
                            contentDescription = null,
                            modifier = Modifier
                                .size(72.dp)
                                .clip(shape = RoundedCornerShape(12.dp))
                        )

                        Spacer(modifier = Modifier.width(16.dp))

                        Column(
                            modifier = Modifier.weight(1f),
                            content = {
                                Text(
                                    text = country.name.common,
                                    fontSize = 21.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = country.name.official,
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        )
                        IconButton(
                            onClick = { favorite = !favorite },
                            modifier = Modifier.align(Alignment.Top),
                            content = {
                                Icon(
                                    imageVector = if (favorite) Icons.Default.Star else Icons.Default.StarBorder,
                                    contentDescription = null,
                                    tint = if (favorite) Color.Yellow else MaterialTheme.colorScheme.onSurface
                                )
                            }
                        )
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                HorizontalDivider()

                Spacer(modifier = Modifier.height(6.dp))

                InfoRow(
                    label = stringResource(id = R.string.info_row_label_capital),
                    value = country.capital.firstOrNull() ?: "N/A"
                )
                InfoRow(
                    label = stringResource(id = R.string.info_row_label_region),
                    value = country.region
                )
                InfoRow(
                    label = stringResource(id = R.string.info_row_label_sub_region),
                    value = country.subregion ?: "N/A"
                )
                InfoRow(
                    label = stringResource(id = R.string.info_row_label_languages),
                    value = getLanguage(country = country)
                )
                InfoRow(
                    label = stringResource(id = R.string.info_row_label_currencies),
                    value = getCurrency(country = country)
                )
                InfoRow(
                    label = stringResource(id = R.string.info_row_label_population),
                    value = "%,d".format(country.population)
                )
                InfoRow(
                    label = stringResource(id = R.string.info_row_label_timezone),
                    value = getTimezones(country = country)
                )
            }
        }
    )
}