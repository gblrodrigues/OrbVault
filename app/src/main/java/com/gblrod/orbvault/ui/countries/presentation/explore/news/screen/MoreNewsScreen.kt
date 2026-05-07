package com.gblrod.orbvault.ui.countries.presentation.explore.news.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.gblrod.orbvault.R
import com.gblrod.orbvault.navigation.Routes
import com.gblrod.orbvault.ui.shared.components.ScreenHeader
import com.gblrod.orbvault.ui.countries.presentation.explore.news.components.NewsItems
import com.gblrod.orbvault.ui.countries.presentation.explore.news.model.NewItem
import com.gblrod.orbvault.ui.theme.BlueActions
import com.gblrod.orbvault.ui.theme.OrangeActions
import com.gblrod.orbvault.ui.theme.PinkActions
import com.gblrod.orbvault.ui.theme.YellowActions

@Composable
fun MoreNewsScreen(
    navHostController: NavHostController,
    colorCustom: Color
) {
    val primaryValue = stringResource(id = R.string.news_label_explore)
    val secondValue = stringResource(id = R.string.news_label_description)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        ScreenHeader(
            primaryValue = primaryValue,
            secondValue = secondValue,
            colorCustom = colorCustom,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        val newsItems = listOf(
            NewItem(
                label = stringResource(id = R.string.news_country_region_description),
                value = stringResource(id = R.string.news_country_region_label),
                icon = Icons.Default.Flag,
                onClick = {},
                backgroundColor = OrangeActions.copy(alpha = 0.15f),
                iconColor = OrangeActions
            ),
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
                onClick = {
                    navHostController.navigate(route = Routes.Quiz.route)
                },
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

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            newsItems.chunked(size = 2).forEach { rowItems ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    rowItems.forEach { item ->
                        NewsItems(
                            modifier = Modifier.weight(1f),
                            icon = item.icon,
                            totalValue = item.value,
                            descValue = item.label,
                            onClick = item.onClick,
                            iconColor = item.iconColor,
                            backgroundColor = item.backgroundColor
                        )
                    }

                    if (rowItems.size == 1) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}