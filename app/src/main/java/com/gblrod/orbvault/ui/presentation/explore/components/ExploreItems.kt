package com.gblrod.orbvault.ui.presentation.explore.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.gblrod.orbvault.ui.shared.components.InfoRow
import com.gblrod.orbvault.ui.shared.components.TopList
import com.gblrod.orbvault.data.dto.CountriesDto
import com.gblrod.orbvault.ui.presentation.explore.viewmodel.CountryDetailsViewModel
import com.gblrod.orbvault.ui.presentation.explore.viewmodel.ExploreViewModel
import com.gblrod.orbvault.ui.presentation.state.ExploreUiState

@Composable
fun ExploreItems(
    modifier: Modifier = Modifier,
    exploreViewModel: ExploreViewModel,
    countryDetailsViewModel: CountryDetailsViewModel,
    primaryValue: String,
    secondValue: String,
    thirdValue: String,
    fourthValue: (CountriesDto) -> String,
    colorCustom: Color,
    onClick: (CountriesDto) -> Unit,
    labelWidth: Dp = 120.dp
) {
    val uiState by exploreViewModel.exploreUiState.collectAsState()
    val countries = (uiState as? ExploreUiState.Success)?.countries ?: emptyList()
    var showSheet by remember { mutableStateOf(false) }
    var selectedCountry by remember { mutableStateOf<CountriesDto?>(null) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        item {
            Column {
                Text(
                    text = primaryValue,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = secondValue,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorCustom
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
        }

        itemsIndexed(countries) { index, country ->
            var favorite by remember { mutableStateOf(false) }

            Card(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .clickable {
                        selectedCountry = country
                        showSheet = true
                        onClick(country)
                    },
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
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
                                official = country.name.official,
                                index = index + 1
                            )
                        }
                        IconButton(
                            onClick = { favorite = !favorite },
                            modifier = Modifier.align(Alignment.Top)
                        ) {
                            Icon(
                                imageVector = if (favorite) Icons.Default.Star else Icons.Default.StarBorder,
                                contentDescription = null,
                                tint = if (favorite) Color.Yellow else MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    HorizontalDivider()

                    Spacer(modifier = Modifier.height(4.dp))

                    InfoRow(
                        label = thirdValue,
                        value = fourthValue(country),
                        labelWidth = labelWidth
                    )
                }
            }
        }
    }

    if (showSheet && selectedCountry != null) {
        CountryBottomSheet(
            countryDetailsViewModel = countryDetailsViewModel,
            showBottomSheet = true,
            onDismiss = {
                showSheet = false
                selectedCountry = null
            }
        )
    }
}