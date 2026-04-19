package com.gblrod.orbvault.navigation.drawer

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.gblrod.orbvault.R
import com.gblrod.orbvault.navigation.Routes

@Composable
fun DrawerContent(
    navController: NavController,
    onItemClick: () -> Unit,
) {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    val items = listOf(
        DrawerItem(
            label = stringResource(id = R.string.drawer_item_home),
            icon = Icons.Default.Home,
            route = Routes.Home.route
        ),
        DrawerItem(
            label = stringResource(id = R.string.drawer_item_explore),
            icon = Icons.Default.Explore,
            route = Routes.Explore.route
        ),
        DrawerItem(
            label = stringResource(id = R.string.drawer_item_favorites),
            icon = Icons.Default.Star,
            route = Routes.Favorites.route
        )
    )

    Text(
        text = stringResource(id = R.string.drawer_title),
        fontSize = 24.sp,
        color = MaterialTheme.colorScheme.onSurface,
        modifier = Modifier.padding(16.dp)
    )

    HorizontalDivider()

    Spacer(modifier = Modifier.height(4.dp))

    items.forEach { item ->
        NavigationDrawerItem(
            label = {
                Text(
                    text = item.label,
                    fontSize = 17.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            },
            icon = {
                Icon(
                    imageVector = item.icon,
                    contentDescription = item.label,
                    tint = MaterialTheme.colorScheme.onSurface
                )
            },
            selected = currentRoute == item.route,
            onClick = {
                onItemClick()
                navController.navigate(item.route) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
    }
}