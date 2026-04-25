package com.gblrod.orbvault.ui.presentation.favorites.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.gblrod.orbvault.R
import com.gblrod.orbvault.ui.presentation.explore.components.CountryBottomSheet
import com.gblrod.orbvault.ui.presentation.explore.viewmodel.CountryDetailsViewModel

@Composable
fun FavoritesScreen(
    countryDetailsViewModel: CountryDetailsViewModel
) {
    val favorites by countryDetailsViewModel.favorites.collectAsState()
    var selectedCode by remember { mutableStateOf<String?>(null) }
    var showSheet by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = selectedCode) {
        selectedCode?.let {
            countryDetailsViewModel.fetchCountryByCode(it)
        }
    }

    LazyColumn {
        items(favorites) { country ->

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable {
                        selectedCode = country.code
                        showSheet = true
                    }
            ) {
                Row(modifier = Modifier.padding(16.dp)) {

                    AsyncImage(
                        model = country.flagUrl,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp)
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Column {
                        Text(text = country.name)
                        Text(text = country.region ?: "")
                    }
                }
            }
        }
    }

    if (showSheet && selectedCode != null) {
        CountryBottomSheet(
            countryDetailsViewModel = countryDetailsViewModel,
            showBottomSheet = true,
            onDismiss = {
                showSheet = false
                selectedCode = null
            }
        )
    }
}