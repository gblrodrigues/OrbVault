package com.gblrod.orbvault.ui.shared.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.gblrod.orbvault.R
import com.gblrod.orbvault.navigation.Routes
import com.gblrod.orbvault.navigation.bottom.BottomItem

@Composable
fun BottomBar(navHostController: NavHostController) {
    val currentRoute = navHostController.currentBackStackEntryAsState().value?.destination?.route
    val items = listOf(
        BottomItem(
            label = stringResource(id = R.string.bottom_bar_home),
            icon = Icons.Default.Home,
            route = Routes.Home.route
        ),
        BottomItem(
            label = stringResource(id = R.string.bottom_bar_explore),
            icon = Icons.Default.Explore,
            route = Routes.Explore.route
        ),
        BottomItem(
            label = stringResource(id = R.string.bottom_bar_favorites),
            icon = Icons.Default.Star,
            route = Routes.Favorites.route
        )
    )
    NavigationBar(
        modifier = Modifier
            .navigationBarsPadding()
            .height(62.dp),
        containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(6.dp)
    ) {
        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                label = {
                    Text(
                        text = item.label
                    )
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label
                    )
                },
                onClick = {
                    navHostController.navigate(item.route) {
                        popUpTo(navHostController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.onSurface,
                    unselectedIconColor = Color.Gray,
                    unselectedTextColor = Color.Gray,
                    selectedTextColor = MaterialTheme.colorScheme.onSurface,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}