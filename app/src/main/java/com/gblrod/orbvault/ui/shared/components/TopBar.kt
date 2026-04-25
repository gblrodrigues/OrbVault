package com.gblrod.orbvault.ui.shared.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.gblrod.orbvault.R
import com.gblrod.orbvault.data.local.entity.FavoriteCountryEntity
import com.gblrod.orbvault.navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    onOpenDrawer: () -> Unit,
    navHostController: NavHostController,
    favorites: List<FavoriteCountryEntity>
) {
    val currentBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    val isFavoriteScreen = currentRoute == Routes.Favorites.route
    val isPrincipalScreen = currentRoute == Routes.Home.route ||
            currentRoute == Routes.Explore.route ||
            currentRoute == Routes.Favorites.route

    val canGoBack = (isFavoriteScreen && favorites.isEmpty()) || !isPrincipalScreen

    TopAppBar(
        modifier = Modifier.height(76.dp),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent
        ),
        title = {
            Text(
                text = stringResource(id = R.string.topbar_title),
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        navigationIcon = {
            if (canGoBack) {
                IconButton(
                    onClick = {
                        if (navHostController.previousBackStackEntry != null) {
                            navHostController.popBackStack()
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.size(28.dp)
                    )
                }
            } else {
                IconButton(
                    onClick = { onOpenDrawer() }
                ) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = stringResource(id = R.string.cd_menu_topbar),
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
        }
    )
}
