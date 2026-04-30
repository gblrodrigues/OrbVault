package com.gblrod.orbvault.ui.main

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.gblrod.orbvault.core.manager.LanguageManager
import com.gblrod.orbvault.navigation.NavigationGraph
import com.gblrod.orbvault.navigation.drawer.DrawerContent
import com.gblrod.orbvault.navigation.mapRouteToNavigationUiState
import com.gblrod.orbvault.ui.countries.presentation.explore.viewmodel.CountryDetailsViewModel
import com.gblrod.orbvault.ui.countries.presentation.explore.viewmodel.ExploreViewModel
import com.gblrod.orbvault.ui.countries.presentation.home.viewmodel.CountriesViewModel
import com.gblrod.orbvault.ui.language.viewmodel.LanguageViewModel
import com.gblrod.orbvault.ui.shared.components.BottomBar
import com.gblrod.orbvault.ui.shared.components.TopBar
import com.gblrod.orbvault.ui.theme.ThemeConfigDefault
import com.gblrod.orbvault.ui.theme.viewmodel.ThemeViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun attachBaseContext(newBase: Context) {
        val language = LanguageManager.getStoredLanguage(newBase)
        val locale = LanguageManager.resolveLocale(language)
        val context = LanguageManager.applyLocale(newBase, locale)
        super.attachBaseContext( context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val splashScreen = installSplashScreen()
        setContent {
            val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
            val scope = rememberCoroutineScope()

            val navHostController = rememberNavController()
            val countriesViewModel: CountriesViewModel = koinViewModel()
            val exploreViewModel: ExploreViewModel = koinViewModel()
            val countryDetailsViewModel: CountryDetailsViewModel = koinViewModel()
            val themeViewModel: ThemeViewModel = koinViewModel()
            val languageViewModel: LanguageViewModel = koinViewModel()

            val theme by themeViewModel.theme.collectAsState()

            val snackbarHostState = remember { SnackbarHostState() }

            val navBackStackEntry by navHostController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            val navigationUiState = mapRouteToNavigationUiState(route = currentRoute)

            splashScreen.setKeepOnScreenCondition {
                theme == null
            }

            if (theme != null) {
                ThemeConfigDefault(
                    themeOption = theme!!
                ) {
                    ModalNavigationDrawer(
                        drawerState = drawerState,
                        drawerContent = {
                            ModalDrawerSheet {
                                DrawerContent(
                                    navController = navHostController,
                                    themeViewModel = themeViewModel,
                                    languageViewModel = languageViewModel,
                                    onItemClick = {
                                        scope.launch {
                                            drawerState.close()
                                        }
                                    }
                                )
                            }
                        }
                    ) {
                        Scaffold(
                            modifier = Modifier.fillMaxSize(),
                            topBar = {
                                if (navigationUiState.showTopBar) {
                                    TopBar(
                                        onOpenDrawer = {
                                            scope.launch {
                                                if (drawerState.isClosed) drawerState.open()
                                                else drawerState.close()
                                            }
                                        },
                                        navHostController = navHostController,
                                        navigationUiState = navigationUiState
                                    )
                                }
                            },
                            bottomBar = {
                                if (navigationUiState.showBottomBar) {
                                    BottomBar(navHostController = navHostController)
                                }
                            },
                            snackbarHost = {
                                SnackbarHost(
                                    hostState = snackbarHostState
                                )
                            }
                        ) { paddingValues ->
                            Box(
                                modifier = Modifier.fillMaxSize()
                            ) {
                                NavigationGraph(
                                    navHostController = navHostController,
                                    paddingValues = paddingValues,
                                    countriesViewModel = countriesViewModel,
                                    exploreViewModel = exploreViewModel,
                                    countryDetailsViewModel = countryDetailsViewModel,
                                    snackbarHostState = snackbarHostState
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}