package com.gblrod.orbvault.ui.main

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
import androidx.navigation.compose.rememberNavController
import com.gblrod.orbvault.navigation.NavigationGraph
import com.gblrod.orbvault.navigation.drawer.DrawerContent
import com.gblrod.orbvault.ui.presentation.explore.viewmodel.CountryDetailsViewModel
import com.gblrod.orbvault.ui.presentation.explore.viewmodel.ExploreViewModel
import com.gblrod.orbvault.ui.presentation.home.viewmodel.CountriesViewModel
import com.gblrod.orbvault.ui.shared.components.BottomBar
import com.gblrod.orbvault.ui.shared.components.TopBar
import com.gblrod.orbvault.ui.theme.ThemeConfigDefault
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
            val scope = rememberCoroutineScope()
            val navHostController = rememberNavController()
            val countriesViewModel: CountriesViewModel = koinViewModel()
            val exploreViewModel: ExploreViewModel = koinViewModel()
            val countryDetailsViewModel: CountryDetailsViewModel = koinViewModel()
            val favorites by countryDetailsViewModel.favorites.collectAsState()
            val snackbarHostState = remember { SnackbarHostState() }

            ThemeConfigDefault {
                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        ModalDrawerSheet {
                            DrawerContent(
                                navController = navHostController,
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
                            TopBar(
                                onOpenDrawer = {
                                    scope.launch {
                                        if (drawerState.isClosed) drawerState.open() else drawerState.close()
                                    }
                                },
                                navHostController = navHostController,
                                favorites = favorites
                            )
                        },
                        bottomBar = {
                            BottomBar(navHostController = navHostController)
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