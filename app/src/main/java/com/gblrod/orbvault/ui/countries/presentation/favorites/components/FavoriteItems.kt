package com.gblrod.orbvault.ui.countries.presentation.favorites.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.gblrod.orbvault.R
import com.gblrod.orbvault.data.countries.local.room.model.FavoriteCountry
import com.gblrod.orbvault.ui.shared.components.ScreenHeader
import com.gblrod.orbvault.ui.countries.presentation.explore.viewmodel.CountryDetailsViewModel
import com.gblrod.orbvault.ui.shared.components.country.CountryCard

@Composable
fun FavoriteItems(
    modifier: Modifier = Modifier,
    viewModel: CountryDetailsViewModel,
    primaryValue: String,
    secondValue: String,
    colorCustom: Color,
    onClick: (String?) -> Unit,
    snackbarHostState: SnackbarHostState,
    onNavigateExplore: () -> Unit
) {
    val favorites by viewModel.favorites.collectAsState()
    var pendingRemoval by remember { mutableStateOf<Pair<FavoriteCountry, Int>?>(null) }

    val snackbarActionLabel = stringResource(id = R.string.snackbar_action_label)
    val snackbarMessage = pendingRemoval?.let {
        stringResource(id = R.string.snackbar_country_message_removed, it.first.name)
    }

    LaunchedEffect(key1 = pendingRemoval?.first?.code) {
        val (country, index) = pendingRemoval ?: return@LaunchedEffect
        snackbarHostState.currentSnackbarData?.dismiss()

        val result = snackbarHostState.showSnackbar(
            message = snackbarMessage ?: "",
            actionLabel = snackbarActionLabel,
            duration = SnackbarDuration.Short,
            withDismissAction = true
        )

        if (result == SnackbarResult.ActionPerformed) {
            viewModel.restoreFavorite(country, index)
        }

        pendingRemoval = null
    }

    if (favorites.isEmpty()) {
        EmptyFavoriteScreen(
            onNavigateExplore = { onNavigateExplore() }
        )
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            item {
                ScreenHeader(
                    primaryValue = primaryValue,
                    secondValue = secondValue,
                    colorCustom = colorCustom,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            items(
                items = favorites,
                key = { it.code }
            ) { country ->

                val dismissState = rememberSwipeToDismissBoxState()
                val color = lerp(
                    start = Color.LightGray,
                    stop = Color.Red,
                    fraction = 1f - dismissState.progress
                )
                LaunchedEffect(key1 = dismissState.targetValue) {
                    if (dismissState.targetValue == SwipeToDismissBoxValue.EndToStart) {
                        val index = favorites.indexOfFirst {
                            it.code == country.code
                        }

                        if (index != -1) {
                            pendingRemoval = country to index
                            viewModel.removeFavoriteByCode(country.code)
                        }

                        dismissState.snapTo(
                            targetValue = SwipeToDismissBoxValue.Settled
                        )
                    }
                }

                SwipeToDismissBox(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clip(shape = RoundedCornerShape(16.dp))
                        .animateItem(),
                    state = dismissState,
                    backgroundContent = {
                        if (dismissState.dismissDirection == SwipeToDismissBoxValue.EndToStart) {
                            Icon(
                                imageVector = Icons.Default.DeleteSweep,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(color = color)
                                    .wrapContentSize(align = Alignment.CenterEnd)
                                    .padding(8.dp)
                            )
                        }
                    },
                    enableDismissFromStartToEnd = false
                ) {
                    CountryCard(
                        flag = country.flagUrl,
                        nameCommon = country.name,
                        nameOfficial = country.official,
                        isFavorite = true,
                        onClick = {
                            viewModel.onCountrySelected(code = country.code)
                            onClick(country.code)
                        },
                        onFavoriteClick = {
                            val index = favorites.indexOfFirst { it.code == country.code }

                            pendingRemoval = country to index
                            viewModel.removeFavoriteByCode(country.code)
                        },
                        modifier = modifier
                    )
                }
            }
        }
    }
}