@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.mediastore_exifinterface

import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mediastore_exifinterface.R.string
import com.example.mediastore_exifinterface.ui.navigation.MediastoreNavHost

/**
 * Top level composable that represents screens for the application.
 */
@Composable
fun MediastoreApp(navController: NavHostController = rememberNavController()) {
    MediastoreNavHost(navController = navController)
}

/**
 * App bar to display title and conditionally display the back navigation.
 */
@Composable
fun MediastoreTopAppBar(
    title: String,
    canNavigateBack: Boolean,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    navigateUp: () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        title = { Text(title) },
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Filled.ArrowBack,
                        contentDescription = stringResource(string.back_button)
                    )
                }
            }
        }
    )
}

