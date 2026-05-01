package com.gblrod.orbvault.ui.countries.presentation.explore.news.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.gblrod.orbvault.R
import com.gblrod.orbvault.navigation.Routes
import com.gblrod.orbvault.ui.countries.presentation.explore.news.model.NewItem
import com.gblrod.orbvault.ui.theme.BlueActions
import com.gblrod.orbvault.ui.theme.PinkActions
import com.gblrod.orbvault.ui.theme.YellowActions

@Composable
fun ExploreActionsRow(
    navHostController: NavHostController
) {
    val newsItems = listOf(
        NewItem(
            label = stringResource(id = R.string.news_country_compare_description),
            value = stringResource(id = R.string.news_country_compare_label),
            icon = Icons.Default.BarChart,
            onClick = {},
            backgroundColor = BlueActions.copy(alpha = 0.15f),
            iconColor = BlueActions
        ),
        NewItem(
            label = stringResource(id = R.string.news_quiz_description),
            value = stringResource(id = R.string.news_quiz_label),
            icon = Icons.Default.Quiz,
            onClick = {},
            backgroundColor = PinkActions.copy(alpha = 0.15f),
            iconColor = PinkActions
        ),
        NewItem(
            label = stringResource(id = R.string.news_my_favorites_description),
            value = stringResource(id = R.string.news_my_favorites_label),
            icon = Icons.Default.FavoriteBorder,
            onClick = {
                navHostController.navigate(route = Routes.Favorites.route)
            },
            backgroundColor = YellowActions.copy(alpha = 0.15f),
            iconColor = YellowActions
        )
    )
    NewsGrid(items = newsItems)
}