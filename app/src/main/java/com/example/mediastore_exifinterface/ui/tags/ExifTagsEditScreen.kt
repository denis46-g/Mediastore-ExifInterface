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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
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

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MediastoreTopAppBar(
                title = stringResource(ExifTagsEditDestination.titleRes),
                canNavigateBack = true,
                navigateUp = onNavigateUp,
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            Row(
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_large))
                    .fillMaxWidth(),
                verticalAlignment = Alignment.Bottom
            ) {
                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.padding_extra_large)))

                Column(
                    modifier = modifier.padding(dimensionResource(id = R.dimen.padding_extra_small)),
                    verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
                ) {

                    OutlinedTextField(
                        value = "date",
                        onValueChange = {  },
                        label = { Text(stringResource(R.string.creation_date)) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                            unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                            disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        enabled = true,
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = "latitude",
                        onValueChange = {  },
                        label = { Text(stringResource(R.string.latitude)) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                            unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                            disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        enabled = true,
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = "longitude",
                        onValueChange = {  },
                        label = { Text(stringResource(R.string.longitude)) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                            unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                            disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        enabled = true,
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = "device",
                        onValueChange = {  },
                        label = { Text(stringResource(R.string.device)) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                            unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                            disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        enabled = true,
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = "device model",
                        onValueChange = {  },
                        label = { Text(stringResource(R.string.device_model)) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                            unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                            disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        enabled = true,
                        singleLine = true
                    )
                    Button(
                        onClick = {
                            viewModel.saveImage()
                            navigateBack()
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = MaterialTheme.shapes.small
                    ) {
                        Text(stringResource(R.string.save_image))
                    }
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