package com.nkoyo.componentidentifier.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.nkoyo.componentidentifier.ui.screens.MainPreviewScreen

/**
 * The Composable that handles the navigation and routing through
 * all the destinations in the app
 */

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun NavHostWrapper(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = Route.MainPreview
){
    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        composable(Route.MainPreview){
            MainPreviewScreen(
                navController = navController,
            )
        }
    }
}

object Route {
    const val MainPreview = "MainPreview"
}