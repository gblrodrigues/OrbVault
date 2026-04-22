package com.gblrod.orbvault.ui.shared.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowRight
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.Map
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gblrod.orbvault.R

@Composable
fun CountryOptionsMenu(
    modifier: Modifier = Modifier,
    expanded: Boolean = false,
    onDismissRequest: () -> Unit,
    onWeatherClick: () -> Unit,
    onCountryMap: () -> Unit,
    onCountryBorders: () -> Unit,
) {
    val items = listOf(
        CountryOptionsItem(
            title = stringResource(id = R.string.dropdown_menu_item_weather_title),
            subTitle = stringResource(id = R.string.dropdown_menu_item_weather_sub_title),
            leadingIcon = Icons.Default.Cloud,
            trailingIcon = Icons.AutoMirrored.Filled.ArrowRight,
            onClick = onWeatherClick
        ),
        CountryOptionsItem(
            title = stringResource(id = R.string.dropdown_menu_item_map_title),
            subTitle = stringResource(id = R.string.dropdown_menu_item_map_sub_title),
            leadingIcon = Icons.Default.Map,
            trailingIcon = Icons.AutoMirrored.Filled.ArrowRight,
            onClick = onCountryMap
        ),
        CountryOptionsItem(
            title = stringResource(id = R.string.dropdown_menu_item_neighbors_title),
            subTitle = stringResource(id = R.string.dropdown_menu_item_neighbors_sub_title),
            leadingIcon = Icons.Default.Flag,
            trailingIcon = Icons.AutoMirrored.Filled.ArrowRight,
            onClick = onCountryBorders
        )
    )

    DropdownMenu(
        modifier = modifier,
        expanded = expanded,
        onDismissRequest = onDismissRequest,
        shape = RoundedCornerShape(16.dp),
        tonalElevation = 6.dp
    ) {
        items.forEachIndexed { index, item ->
            DropdownMenuItem(
                text = {
                    Column {
                        Text(
                            text = item.title,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Text(
                            text = item.subTitle,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = 12.sp
                        )
                    }
                },
                leadingIcon = {
                    Icon(
                        imageVector = item.leadingIcon,
                        contentDescription = item.title,
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                },
                trailingIcon = {
                    Icon(
                        imageVector = item.trailingIcon,
                        contentDescription = item.title,
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                },
                onClick = item.onClick,
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
            )

            if (index < items.lastIndex) {
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 12.dp),
                    thickness = 0.5.dp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}