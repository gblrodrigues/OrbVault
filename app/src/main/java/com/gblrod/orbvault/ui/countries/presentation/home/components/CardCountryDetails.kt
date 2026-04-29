package com.gblrod.orbvault.ui.countries.presentation.home.components

import androidx.compose.animation.animateContentSize
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
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.gblrod.orbvault.R
import com.gblrod.orbvault.data.countries.remote.dto.CountriesDto
import com.gblrod.orbvault.data.countries.mapper.getCurrency
import com.gblrod.orbvault.data.countries.mapper.getLanguage
import com.gblrod.orbvault.data.countries.mapper.getTimezones
import com.gblrod.orbvault.ui.countries.actions.MapOpener
import com.gblrod.orbvault.ui.countries.presentation.explore.viewmodel.CountryDetailsViewModel
import com.gblrod.orbvault.ui.countries.presentation.state.BordersUiState
import com.gblrod.orbvault.ui.shared.components.CountryOptionsMenu
import com.gblrod.orbvault.ui.shared.components.FavoriteButton
import com.gblrod.orbvault.ui.shared.components.InfoRow
import com.gblrod.orbvault.ui.shared.components.TopList
import com.gblrod.orbvault.ui.weather.WeatherBottomSheet
import com.gblrod.orbvault.ui.weather.viewmodel.WeatherViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun CardCountryDetails(
    country: CountriesDto,
    modifier: Modifier = Modifier,
    onFetchBorders: (CountriesDto) -> Unit,
    onCountryClick: (String) -> Unit,
    countryQuery: (String) -> Unit,
    bordersState: BordersUiState,
    countryDetailsViewModel: CountryDetailsViewModel
) {
    var showBorders by remember(key1 = country.cca3) { mutableStateOf(false) }
    var expanded by remember(key1 = country.cca3) { mutableStateOf(false) }
    val context = LocalContext.current

    val snackbarHostState = remember { SnackbarHostState() }
    val favorites by countryDetailsViewModel.favorites.collectAsState(initial = emptyList())
    val isFavorite = favorites.any { it.code == country.cca3 }

    val weatherViewModel: WeatherViewModel = koinViewModel()
    var showWeatherSheet by remember {
        mutableStateOf(false)
    }

    val latitude = country.latlng?.getOrNull(index = 0)
    val longitude = country.latlng?.getOrNull(index = 1)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .animateContentSize(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(12.dp)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
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

                Spacer(modifier = Modifier.width(16.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    TopList(
                        country = country.name.common,
                        official = country.name.official
                    )
                }
                FavoriteButton(
                    isFavorite = isFavorite,
                    modifier = Modifier.align(Alignment.Top),
                    onClick = { countryDetailsViewModel.toggleFavorite(country) }
                )

                IconButton(
                    onClick = { expanded = !expanded },
                    modifier = Modifier.align(Alignment.Top)
                ) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = null
                    )

                    CountryOptionsMenu(
                        modifier = modifier,
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        onCountryMap = {
                            MapOpener.openMap(context = context, countryName = country.name.common)
                            expanded = false
                        },
                        onCountryBorders = {
                            showBorders = !showBorders
                            if (showBorders) onFetchBorders(country)
                            expanded = false
                        },
                        onWeatherClick = {
                            if (latitude != null && longitude != null) {
                                weatherViewModel.fetchWeather(
                                    latitude = latitude,
                                    longitude = longitude
                                )
                                showWeatherSheet = true
                            }
                            expanded = false
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            HorizontalDivider()

            Spacer(modifier = Modifier.height(6.dp))

            InfoRow(
                label = stringResource(id = R.string.country_details_label_capital),
                value = country.capital?.firstOrNull() ?: "N/A"
            )
            InfoRow(
                label = stringResource(id = R.string.country_details_label_region),
                value = country.region
            )
            InfoRow(
                label = stringResource(id = R.string.country_details_label_sub_region),
                value = country.subregion ?: "N/A"
            )
            val languagesSize = country.languages?.size ?: 0
            InfoRow(
                label = if (languagesSize <= 1) stringResource(id = R.string.country_details_label_language)
                else stringResource(id = R.string.country_details_label_languages),
                value = getLanguage(country = country)
            )
            InfoRow(
                label = stringResource(id = R.string.country_details_label_currencies),
                value = getCurrency(country = country)
            )
            InfoRow(
                label = stringResource(id = R.string.country_details_label_population),
                value = "%,d".format(country.population)
            )
            InfoRow(
                label = stringResource(id = R.string.country_details_label_timezone),
                value = getTimezones(country = country)
            )

            if (showBorders) {
                val shown = (bordersState as? BordersUiState.Success)?.neighbors?.size ?: 0
                val total = country.borders?.size ?: 0
                val value = shown.let {
                    if (shown < 1) stringResource(id = R.string.neighbors_empty)
                    else "$it de $total"
                }

                val label = if (total <= 1) stringResource(id = R.string.neighbors_one_label)
                else stringResource(id = R.string.neighbors_more_label)

                InfoRow(
                    label = label,
                    value = value
                )

                Spacer(modifier = Modifier.height(12.dp))

                BorderCountriesRow(
                    bordersState = bordersState,
                    onCountryClick = onCountryClick,
                    countryQuery = countryQuery
                )
            }
        }
    }

    SnackbarHost(
        hostState = snackbarHostState,
        modifier = Modifier.padding(16.dp)
    ) { data ->
        Snackbar(
            snackbarData = data,
            shape = RoundedCornerShape(16.dp)
        )
    }

    if (showWeatherSheet) {
        WeatherBottomSheet(
            country = country,
            weatherViewModel = weatherViewModel,
            countryDetailsViewModel = countryDetailsViewModel,
            onDismiss = {
                showWeatherSheet = false
            }
        )
    }
}