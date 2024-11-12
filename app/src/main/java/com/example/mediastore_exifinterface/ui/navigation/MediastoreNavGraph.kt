package com.example.mediastore_exifinterface.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.mediastore_exifinterface.ui.home.HomeDestination
import com.example.mediastore_exifinterface.ui.home.HomeScreen
import com.example.mediastore_exifinterface.ui.tags.ExifTagsEditDestination
import com.example.mediastore_exifinterface.ui.tags.ExifTagsEditScreen

/**
 * Provides Navigation graph for the application.
 */
@Composable
fun MediastoreNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {
        composable(route = HomeDestination.route) {
            HomeScreen(
                navigateToExifTagsEdit = {
                    navController.navigate(ExifTagsEditDestination.route)
                                         },
            )
        }
        composable(route = ExifTagsEditDestination.route) {
            ExifTagsEditScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }
    }
}