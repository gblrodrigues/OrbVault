package com.gblrod.orbvault.ui.shared.components.drawer

import android.app.Activity
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.AsyncImage
import com.gblrod.orbvault.R
import com.gblrod.orbvault.core.manager.LanguageManager
import com.gblrod.orbvault.core.utils.orDeviceDefault
import com.gblrod.orbvault.navigation.Routes
import com.gblrod.orbvault.ui.language.viewmodel.LanguageViewModel
import com.gblrod.orbvault.ui.shared.components.model.DrawerItem
import com.gblrod.orbvault.ui.shared.components.model.DrawerPreferenceItem
import com.gblrod.orbvault.ui.theme.ThemeOptions
import com.gblrod.orbvault.ui.theme.viewmodel.ThemeViewModel

@Composable
fun DrawerContent(
    navController: NavController,
    onItemClick: () -> Unit,
    themeViewModel: ThemeViewModel,
    languageViewModel: LanguageViewModel
) {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    var showThemeDialog by remember { mutableStateOf(false) }
    var showLanguageDialog by remember { mutableStateOf(false) }

    val theme = themeViewModel.theme.collectAsState().value ?: ThemeOptions.SYSTEM
    val language = languageViewModel.language.collectAsState().value

    val effectiveLanguage = language.orDeviceDefault()
    val activity = LocalActivity.current as Activity
    val context = LocalContext.current

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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = R.drawable.logo,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(50.dp)
                        .clip(shape = CircleShape)
                )
                Spacer(modifier = Modifier.width(12.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = stringResource(id = R.string.drawer_title),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = stringResource(id = R.string.drawer_sub_title),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        items.forEach { item ->
            NavigationDrawerItem(
                label = {
                    Text(
                        text = item.label,
                        style = MaterialTheme.typography.bodyLarge,
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
                },
                shape = RoundedCornerShape(16.dp)
            )
        }

        HorizontalDivider(
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Text(
            text = stringResource(id = R.string.drawer_category_preference),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        DrawerPreferenceItem(
            title = stringResource(id = R.string.drawer_item_themes),
            label =  stringResource(id = theme.label),
            icon = Icons.Default.Palette,
            contentDescription = stringResource(id = R.string.drawer_item_themes_cd),
            onClick = { showThemeDialog = true }
        )

        if (showThemeDialog) {
            ThemeMenu(
                selectedTheme = theme,
                onThemeSelected = { theme ->
                    themeViewModel.setTheme(theme = theme)
                },
                onDismiss = { showThemeDialog = false }
            )
        }

        DrawerPreferenceItem(
            title = stringResource(id = R.string.drawer_item_languages),
            label =  stringResource(id = effectiveLanguage.label),
            icon = Icons.Default.Language,
            contentDescription = stringResource(id = R.string.drawer_item_languages_cd),
            onClick = { showLanguageDialog = true }
        )

        if (showLanguageDialog) {
            LanguageMenu(
                selectedLanguage = effectiveLanguage,
                onLanguageSelected = { language ->
                    languageViewModel.setLanguage(language)

                    LanguageManager.persistLanguage(
                        context = context,
                        language = language
                    )
                    activity.recreate()
                },
                onDismiss = { showLanguageDialog = false }
            )
        }
    }
}