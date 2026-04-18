package com.gblrod.orbvault.components

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
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
        containerColor = Color.Transparent,
        modifier = Modifier.navigationBarsPadding()
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
                        popUpTo(route = Routes.Home.route) {
                            saveState = true
                        }
                        launchSingleTop = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,
                    unselectedIconColor = Color.LightGray,
                    unselectedTextColor = Color.LightGray,
                    selectedTextColor = Color.White,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}