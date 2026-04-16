package com.gblrod.orbvault

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.gblrod.orbvault.components.TopBar
import com.gblrod.orbvault.navigation.NavigationGraph
import com.gblrod.orbvault.navigation.drawer.DrawerContent
import com.gblrod.orbvault.presentation.viewmodel.CountriesViewModel
import com.gblrod.orbvault.ui.theme.OrbVaultTheme
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
            OrbVaultTheme {
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
                    },
                    content = {
                        Scaffold(
                            modifier = Modifier.fillMaxSize(),
                            topBar = {
                                TopBar(
                                    onOpenDrawer = {
                                        scope.launch {
                                            if (drawerState.isClosed) drawerState.open() else drawerState.close()
                                        }
                                    }
                                )
                            }
                        ) { paddingValues ->
                            NavigationGraph(
                                navHostController = navHostController,
                                paddingValues = paddingValues,
                                countriesViewModel = countriesViewModel
                            )
                        }
                    }
                )
            }
        }
    }
}