package com.example.mediastore_exifinterface.ui.tags

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mediastore_exifinterface.MediastoreTopAppBar
import com.example.mediastore_exifinterface.R
import com.example.mediastore_exifinterface.ui.AppViewModelProvider
import com.example.mediastore_exifinterface.ui.home.image_is_loaded
import com.example.mediastore_exifinterface.ui.navigation.NavigationDestination

object ExifTagsEditDestination : NavigationDestination {
    override val route = "exif_tags_edit"
    override val titleRes = R.string.exif_tags_edit
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExifTagsEditScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ExifTagsEditViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {

    Scaffold(
        topBar = {
            MediastoreTopAppBar(
                title = stringResource(ExifTagsEditDestination.titleRes),
                canNavigateBack = true,
                navigateUp = onNavigateUp
            )
        },
        modifier = modifier,
        floatingActionButton = {
            Row(
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_large))
                    .fillMaxWidth(),
                verticalAlignment = Alignment.Bottom
            ) {
                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.padding_extra_large)))
                Button(
                    onClick = {
                        //viewModel.loadImageFromMediaStore(getImageLauncher)
                        navigateBack()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.small
                ) {
                    Text(stringResource(R.string.save_image))
                }
            }
        }
    ){
        /*HomeBody(
            //onItemClick = navigateToExifTagsEdit,
            modifier = modifier.fillMaxSize(),
            contentPadding = innerPadding,
        )*/
    }
}