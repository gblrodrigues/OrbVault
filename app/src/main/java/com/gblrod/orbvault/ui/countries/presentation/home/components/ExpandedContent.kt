package com.gblrod.orbvault.ui.countries.presentation.home.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import com.gblrod.orbvault.R
import com.gblrod.orbvault.data.countries.local.room.model.RecentCountry

@Composable
fun ExpandedContent(
    recentCountries: List<RecentCountry>,
    onCountryClick: (String) -> Unit
) {
    var showAll by remember { mutableStateOf(false) }
    val focus = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val visibleCountries = if (showAll) {
        recentCountries
    } else {
        recentCountries.take(n = 3)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.surface)
            .padding(horizontal = 16.dp)
            .animateContentSize()
    ) {
        RecentCountryHeader(
            title = R.string.home_search_recent_country_title,
            showAllItems = R.string.home_search_recent_country_show_all,
            showLessItems = R.string.home_search_recent_country_show_default,
            showRecentCountrySize = visibleCountries.size,
            showAll = showAll,
            onClick = {
                focus.clearFocus(force = true)
                keyboardController?.hide()
                showAll = !showAll
            }
        )
        Spacer(modifier = Modifier.height(12.dp))

        visibleCountries.forEach { country ->
            RecentCountryItem(
                country = country,
                onClick = { onCountryClick(country.code) }
            )
        }
    }
}